package fr.lataverne.itemreward.managers;

import com.google.gson.*;
import fr.lataverne.itemreward.ItemReward;
import fr.lataverne.itemreward.effects.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.Player;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static fr.lataverne.itemreward.Helper.getStringInConfig;
import static fr.lataverne.itemreward.Helper.sendBarMessage;

public abstract class CustomEffect {
	public enum ECustomEffect {
		Fly,
		Phantom,
		Mining,
		Creeper,
		Swimming,
	}

	private static final String customEffectPath = "plugins/ItemReward/customEffect/";

	private static final HashMap<UUID, CustomEffect> effectsInProgress = new HashMap<>();

	protected final int level;

	protected final UUID playerUUID;

	protected int taskId = -1;

	protected boolean isStarted = false;

	protected int remainingTime = 0;

	protected CustomEffect(UUID playerUUID, int level) {
		this.playerUUID = playerUUID;
		this.level = level;
	}

	public static void addEffectInProgress(UUID uuid, CustomEffect customEffect) {
		effectsInProgress.put(uuid, customEffect);
	}

	public static CustomEffect createCustomEffect(ECustomEffect eCustomEffect, UUID playerUUID, int level) throws NotImplementedException {
		switch (eCustomEffect) {
			case Fly:
				return new FlyEffect(playerUUID, level);
			case Phantom:
				return new PhantomEffect(playerUUID);
			case Mining:
				return new MiningEffect(playerUUID);
			case Creeper:
				return new CreeperEffect(playerUUID);
			case Swimming:
				return new SwimmingEffect(playerUUID);
			default:
				throw new NotImplementedException("Not implemented: " + eCustomEffect);
		}
	}

	public static CustomEffect getCustomEffect(UUID uuid) {
		return effectsInProgress.getOrDefault(uuid, null);
	}

	public static boolean hasEffectInProgress(UUID uuid) {
		return effectsInProgress.containsKey(uuid);
	}

	public static void loadCustomEffect(UUID playerUUID) {
		if (playerUUID == null) {
			return;
		}

		Path path = Paths.get(customEffectPath + playerUUID);

		if (!Files.exists(path)) {
			return;
		}

		try {
			Reader reader = Files.newBufferedReader(Paths.get(customEffectPath + playerUUID));

			JsonParser jsonParser = new JsonParser();

			JsonObject json = jsonParser.parse(reader).getAsJsonObject();

			LocalDateTime dateTime = LocalDateTime.parse(json.get("dateTime").getAsString());

			if (dateTime.isBefore(LocalDateTime.now().minusDays(15))) {
				Files.delete(path);
				return;
			}

			for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
				if (entry.getKey().equals("dateTime")) {
					continue;
				}

				try {
					ECustomEffect eCustomEffect = ECustomEffect.valueOf(entry.getKey());

					JsonObject jsonObject = entry.getValue().getAsJsonObject();

					int level = 1;
					if (jsonObject.has("level")) {
						level = jsonObject.get("level").getAsInt();
					}

					CustomEffect customEffect = createCustomEffect(eCustomEffect, playerUUID, level);
					customEffect.remainingTime = jsonObject.get("remainingTime").getAsInt();

					customEffect.start();
				} catch (IllegalArgumentException e) {
					ItemReward.sendMessageToConsole(ChatColor.RED + "This custom potion does not exist (" + entry.getKey() + ")");
				} catch (NotImplementedException e) {
					ItemReward.sendMessageToConsole(ChatColor.RED + "This custom potion is not implemented (" + entry.getKey() + ")");
				}
			}
		} catch (IOException e) {
			ItemReward.sendMessageToConsole(ChatColor.RED + "Read file error");
			ItemReward.sendMessageToConsole(ChatColor.RED + e.getMessage());
		} catch (JsonIOException | JsonSyntaxException e) {
			ItemReward.sendMessageToConsole(ChatColor.RED + "Json parse error");
			ItemReward.sendMessageToConsole(ChatColor.RED + e.getMessage());
		}
	}

	public static void removeEffectInProgress(UUID uuid) {
		effectsInProgress.remove(uuid);
	}

	public static void saveCustomEffect(UUID playerUUID) {
		if (playerUUID == null) {
			return;
		}

		Path path = Paths.get(customEffectPath + playerUUID);

		if (!effectsInProgress.containsKey(playerUUID)) {
			if (Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					ItemReward.sendMessageToConsole("Error: cannot delete the file (" + path + ")");
				}
			}
			return;
		}

		CustomEffect customEffect = effectsInProgress.get(playerUUID);

		JsonObject json = new JsonObject();

		JsonObject effect = new JsonObject();
		effect.addProperty("remainingTime", customEffect.remainingTime);
		if (customEffect.level > 1) {
			effect.addProperty("level", customEffect.level);
		}

		json.add(customEffect.getCustomEffectType().toString(), effect);

		json.addProperty("dateTime", LocalDateTime.now().toString());

		try {
			Gson gson = new Gson();

			BufferedWriter writer = Files.newBufferedWriter(path);
			writer.write(gson.toJson(json));

			writer.close();
		} catch (IOException e) {
			ItemReward.sendMessageToConsole(ChatColor.RED + "Write file error");
			ItemReward.sendMessageToConsole(ChatColor.RED + e.getMessage());
			return;
		}

		Bukkit.getScheduler().cancelTask(customEffect.taskId);
		customEffect.taskId = -1;

		effectsInProgress.remove(playerUUID);
	}

	public void start() {
		if (this.isStarted) {
			return;
		}

		Player player = Bukkit.getPlayer(this.playerUUID);

		if (player == null) {
			return;
		}

		this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(ItemReward.getInstance(), this.getRepeatingTask(), 0, 20);

		if (this.taskId == -1) {
			this.stop();
		} else {
			CustomEffect.addEffectInProgress(player.getUniqueId(), this);
		}

		this.isStarted = true;
	}

	public void stop() {
		Player player = Bukkit.getPlayer(this.playerUUID);

		if (player == null) {
			return;
		}

		if (this.taskId != -1) {
			Bukkit.getScheduler().cancelTask(this.taskId);
			this.taskId = -1;
		}

		CustomEffect.removeEffectInProgress(player.getUniqueId());

		sendBarMessage(player, getStringInConfig("message.user.customPotionEffectFinished", false));

		this.isStarted = false;
	}

	@SuppressWarnings ("unused")
	protected abstract String getConfigPath();

	@SuppressWarnings ("unused")
	protected abstract ECustomEffect getCustomEffectType();

	protected abstract Runnable getRepeatingTask();
}
