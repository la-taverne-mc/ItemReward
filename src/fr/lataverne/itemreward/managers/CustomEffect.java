package fr.lataverne.itemreward.managers;

import fr.lataverne.itemreward.ItemReward;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

import static fr.lataverne.itemreward.Helper.getStringInConfig;
import static fr.lataverne.itemreward.Helper.sendBarMessage;

public abstract class CustomEffect {
	public enum ECustomEffect {
		Fly,
		Phantom,
		Mining,
		Creeper,
	}

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

	public static CustomEffect getCustomEffect(UUID uuid) {
		return effectsInProgress.getOrDefault(uuid, null);
	}

	public static boolean hasEffectInProgress(UUID uuid) {
		return effectsInProgress.containsKey(uuid);
	}

	public static void removeEffectInProgress(UUID uuid) {
		effectsInProgress.remove(uuid);
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
