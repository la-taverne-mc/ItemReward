package fr.lataverne.itemreward;

import fr.lataverne.itemreward.managers.CommandManager;
import fr.lataverne.itemreward.managers.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@SuppressWarnings("ClassNamePrefixedWithPackageName")
public class ItemReward extends JavaPlugin {

    private static ItemReward instance = null;

    public static ItemReward getInstance() {
        return ItemReward.instance;
    }

    public static void sendMessageToConsole(String message) {
        Helper.colorizeString(message);
        String str =
                ItemReward.instance.getConfig().getString("message.consoleSuffix") + " " + ChatColor.RESET + message;
        Bukkit.getConsoleSender().sendMessage(Helper.colorizeString(str));
    }

    @Override
    public void onDisable() {
        ItemReward.sendMessageToConsole(this.getConfig().getString("message.system.stopMessage"));
    }

    @Override
    public void onEnable() {
        //noinspection AssignmentToStaticFieldFromInstanceMethod
        ItemReward.instance = this;

        CommandExecutor commandManager = new CommandManager();

        this.getCommand("itemreward").setExecutor(commandManager);

        Bukkit.getPluginManager().registerEvents(new EventManager(), this);

        ItemReward.sendMessageToConsole(this.getConfig().getString("message.system.startMessage"));
    }

    /**
     * Method for reload the plugin's config. If the config don't exist then we save the default config.
     */
    @Override
    public void reloadConfig() {
        boolean configExist = true;
        if (!new File("plugins/ItemReward/config.yml").exists()) {
            this.saveDefaultConfig();
            configExist = false;
        }

        super.reloadConfig();

        ItemReward.sendMessageToConsole(configExist
                                        ? this.getConfig().getString("message.system.existingConfig")
                                        : this.getConfig().getString("message.system.nonExistingConfig"));

        ItemReward.sendMessageToConsole(this.getConfig().getString("message.system.reloadComplete"));
    }
}
