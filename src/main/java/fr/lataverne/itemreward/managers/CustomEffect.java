package fr.lataverne.itemreward.managers;

import com.google.gson.*;
import fr.lataverne.itemreward.Helper;
import fr.lataverne.itemreward.ItemReward;
import fr.lataverne.itemreward.effects.*;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class CustomEffect {

    private static final String customEffectPath = "plugins/ItemReward/customEffect/";

    private static final HashMap<UUID, CustomEffect> effectsInProgress = new HashMap<>();

    protected final int level;

    protected final UUID playerUUID;

    protected boolean isStarted = false;

    protected int remainingTime;

    protected int taskId = -1;

    protected CustomEffect(UUID playerUUID, int level) {
        this.playerUUID = playerUUID;
        this.level = level;
        this.remainingTime = Helper.getIntInConfig(this.getConfigPath() + ".duration");
    }

    public static void addEffectInProgress(UUID uuid, CustomEffect customEffect) {
        CustomEffect.effectsInProgress.put(uuid, customEffect);
    }

    public static CustomEffect createCustomEffect(ECustomEffect eCustomEffect, UUID playerUUID, int level) {
        return switch (eCustomEffect) {
            case Fly -> new FlyEffect(playerUUID, level);
            case Phantom -> new PhantomEffect(playerUUID);
            case Mining -> new MiningEffect(playerUUID);
            case Creeper -> new CreeperEffect(playerUUID);
            case Swimming -> new SwimmingEffect(playerUUID);
        };
    }

    public static CustomEffect getCustomEffect(UUID uuid) {
        return CustomEffect.effectsInProgress.getOrDefault(uuid, null);
    }

    public static boolean hasEffectInProgress(UUID uuid) {
        return CustomEffect.effectsInProgress.containsKey(uuid);
    }

    public static void loadCustomEffect(UUID playerUUID) {
        if (playerUUID == null) {
            return;
        }

        Path path = Paths.get(CustomEffect.customEffectPath + playerUUID);

        if (!Files.exists(path)) {
            return;
        }

        try {
            Reader reader = Files.newBufferedReader(Paths.get(CustomEffect.customEffectPath + playerUUID));

            JsonParser jsonParser = new JsonParser();

            JsonObject json = jsonParser.parse(reader).getAsJsonObject();

            LocalDateTime dateTime = LocalDateTime.parse(json.get("dateTime").getAsString());

            if (dateTime.isBefore(LocalDateTime.now().minusDays(15))) {
                Files.delete(path);
                return;
            }

            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                if ("dateTime".equals(entry.getKey())) {
                    continue;
                }

                try {
                    ECustomEffect eCustomEffect = ECustomEffect.valueOf(entry.getKey());

                    JsonObject jsonObject = entry.getValue().getAsJsonObject();

                    int level = jsonObject.has("level")
                                ? jsonObject.get("level").getAsInt()
                                : 1;

                    CustomEffect customEffect = CustomEffect.createCustomEffect(eCustomEffect, playerUUID, level);
                    customEffect.remainingTime = jsonObject.get("remainingTime").getAsInt();

                    customEffect.start();
                } catch (IllegalArgumentException e) {
                    ItemReward.sendMessageToConsole(
                            ChatColor.RED + "This custom potion does not exist (" + entry.getKey() + ")");
                } catch (NotImplementedException e) {
                    ItemReward.sendMessageToConsole(
                            ChatColor.RED + "This custom potion is not implemented (" + entry.getKey() + ")");
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
        CustomEffect.effectsInProgress.remove(uuid);
    }

    public static void saveCustomEffect(UUID playerUUID) {
        if (playerUUID == null) {
            return;
        }

        Path path = Paths.get(CustomEffect.customEffectPath + playerUUID);

        if (!CustomEffect.effectsInProgress.containsKey(playerUUID)) {
            if (Files.exists(path)) {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    ItemReward.sendMessageToConsole("Error: cannot delete the file (" + path + ")");
                }
            }
            return;
        }

        CustomEffect customEffect = CustomEffect.effectsInProgress.get(playerUUID);

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

            if (!Files.exists(Paths.get(CustomEffect.customEffectPath))) {
                Files.createDirectory(Paths.get(CustomEffect.customEffectPath));
            }

            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            Files.writeString(path, gson.toJson(json));
        } catch (IOException e) {
            ItemReward.sendMessageToConsole(ChatColor.RED + "Write file error");
            ItemReward.sendMessageToConsole(ChatColor.RED + e.getMessage());
            return;
        }

        Bukkit.getScheduler().cancelTask(customEffect.taskId);
        customEffect.taskId = -1;

        CustomEffect.effectsInProgress.remove(playerUUID);
    }

    public void start() {
        if (this.isStarted) {
            return;
        }

        Player player = Bukkit.getPlayer(this.playerUUID);

        if (player == null) {
            return;
        }

        this.taskId = Bukkit.getScheduler()
                            .scheduleSyncRepeatingTask(ItemReward.getInstance(), this::getRepeatingTask, 0, 20);

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

        Helper.sendBarMessage(player, Helper.getStringInConfig("message.user.customPotionEffectFinished", false));

        this.isStarted = false;
    }

    @Override
    public String toString() {
        return "CustomEffect{" + "level=" + this.level + ", playerUUID=" + this.playerUUID + ", isStarted=" +
               this.isStarted + ", remainingTime=" + this.remainingTime + ", taskId=" + this.taskId + "}";
    }

    protected abstract String getConfigPath();

    protected abstract ECustomEffect getCustomEffectType();

    protected abstract void getRepeatingTask();
}
