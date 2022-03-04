package fr.lataverne.itemreward.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static fr.lataverne.itemreward.Helper.getStringInConfig;
import static fr.lataverne.itemreward.Helper.sendMessage;

public class CommandManager implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length < 1) {
                sendMessage(player, getStringInConfig("message.user.misuseCommand", false));
                return true;
            }

            if (args[0].equalsIgnoreCase("help")) {
                this.sendHelpMenu(player);
                return true;
            } // ir help

            if (args[0].equalsIgnoreCase("give")) {
                if (!player.hasPermission("ir.give")) {
                    sendMessage(player, getStringInConfig("message.user.notPermission", false));
                    return true;
                }

                this.giveCustomsItem(player, Arrays.copyOfRange(args, 1, args.length));
                return true;
            } // ir give ...

            if (args[0].equalsIgnoreCase("list")) {
                if (!player.hasPermission("ir.list")) {
                    sendMessage(player, getStringInConfig("message.user.notPermission", false));
                    return true;
                }

                for (CustomItem.ECustomItem eCustomItem : CustomItem.ECustomItem.values()) {
                    player.sendMessage(ChatColor.GOLD + eCustomItem.toString());
                }
                return true;
            } // ir list

            sendMessage(player, getStringInConfig("message.user.unknownCommand", false));
            return true;
        }

        if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;

            if (args.length < 1 || args[0].equalsIgnoreCase("help")) {
                this.sendHelpMenu(console);
                return true;
            }

            if (args[0].equalsIgnoreCase("give")) {
                this.giveCustomsItem(console, Arrays.copyOfRange(args, 1, args.length));
                return true;
            } // ir give ...

            if (args[0].equalsIgnoreCase("list")) {
                for (CustomItem.ECustomItem eCustomItem : CustomItem.ECustomItem.values()) {
                    console.sendMessage(ChatColor.GOLD + eCustomItem.toString());
                }

                return true;
            } // ir list

            sendMessage(console, getStringInConfig("message.user.unknownCommand", false));
            return true;
        }

        return false;
    }

    private void giveCustomsItem(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sendMessage(sender, getStringInConfig("message.user.misuseCommand", false));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sendMessage(sender, getStringInConfig("message.user.userNotFound", false));
            return;
        }

        if (args.length < 2) {
            sendMessage(sender, getStringInConfig("message.user.misuseCommand", false));
            return;
        }

        if (args[1].equalsIgnoreCase("all")) {
            for (CustomItem.ECustomItem customItemType : CustomItem.ECustomItem.values()) {
                target.getInventory().addItem(CustomItem.getCustomItem(customItemType));
            }
        } else {
            int amount = 1;
            CustomItem.ECustomItem customItemType;
            int level = 1;

            try {
                customItemType = CustomItem.ECustomItem.valueOf(args[1]);
            } catch (IllegalArgumentException ex) {
                sendMessage(sender, getStringInConfig("message.user.customItemNotFound", false));
                return;
            }

            if (args.length >= 3) {
                try {
                    amount = Integer.parseInt(args[2]);
                } catch (NumberFormatException ignored) {
                }
            }

            if (args.length >= 4) {
                try {
                    level = Integer.parseInt(args[3]);
                } catch (NumberFormatException ignored) {
                }
            }

            CustomItem customItem = CustomItem.getCustomItem(customItemType, amount, level);
            if (customItem != null) {
                target.getInventory().addItem(customItem);
            }
        }
    }

    private void sendHelpMenu(CommandSender sender) {
        sender.sendMessage(ChatColor.AQUA + "================== ItemReward ==================");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/ir give " + ChatColor.GOLD + "<player> <item> " + ChatColor.GRAY + " [<count>] [<level>]" + ChatColor.WHITE + " : Give custom items to a player.");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/ir give " + ChatColor.GOLD + "<player> " + ChatColor.LIGHT_PURPLE + "all" + ChatColor.WHITE + " : Give all custom items to a player.");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/ir list" + ChatColor.WHITE + " : Display all custom items.");
        sender.sendMessage(ChatColor.AQUA + "================== ItemReward ==================");
    }
}