package fr.lataverne.itemreward;

import fr.lataverne.itemreward.managers.CommandManager;
import fr.lataverne.itemreward.managers.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public class ItemReward extends JavaPlugin {

    private static ItemReward instance;

    public static ItemReward getInstance() {
        return instance;
    }

    public static void sendMessageToConsole(String message) {
        Helper.colorizeString(message);
        String str = instance.getConfig().getString("message.consoleSuffix") + " " + ChatColor.RESET + message;
        Bukkit.getConsoleSender().sendMessage(Helper.colorizeString(str));
    }

    @Override
    public void onDisable() {
        sendMessageToConsole(this.getConfig().getString("message.system.stopMessage"));
    }

    @Override
    public void onEnable() {
        ItemReward.instance = this;

        CommandManager commandManager = new CommandManager();
        try {
            Objects.requireNonNull(this.getCommand("itemreward")).setExecutor(commandManager);
        } catch (NullPointerException ex) {
            sendMessageToConsole(this.getConfig().getString("message.system.failedStart"));
            this.setEnabled(false);
        }

        Bukkit.getPluginManager().registerEvents(new EventManager(), this);

        sendMessageToConsole(this.getConfig().getString("message.system.startMessage"));
    }

    /**
     * Method for reload the plugin's config.
     * If the config don't exist then we save the default config.
     */
    @Override
    public void reloadConfig() {
        boolean configExist = true;
        if (!new File("plugins/ItemReward/config.yml").exists()) {
            this.saveDefaultConfig();
            configExist = false;
        }

        super.reloadConfig();

        if (configExist) {
            sendMessageToConsole(this.getConfig().getString("message.system.existingConfig"));
        } else {
            sendMessageToConsole(this.getConfig().getString("message.system.nonExistingConfig"));
        }

        sendMessageToConsole(this.getConfig().getString("message.system.reloadComplete"));
    }
}
