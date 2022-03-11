package fr.lataverne.itemreward.managers;

import fr.lataverne.itemreward.Helper;
import fr.lataverne.itemreward.api.objects.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class CommandManager implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
        if (sender instanceof Player player) {

            if (args.length < 1) {
                Helper.sendMessage(player, Helper.getStringInConfig("message.user.misuseCommand", false));
                return true;
            }

            if ("help".equalsIgnoreCase(args[0])) {
                CommandManager.sendHelpMenu(player);
                return true;
            } // ir help

            if ("give".equalsIgnoreCase(args[0])) {
                if (!player.hasPermission("ir.give")) {
                    Helper.sendMessage(player, Helper.getStringInConfig("message.user.notPermission", false));
                    return true;
                }

                CommandManager.giveCustomsItem(player, Arrays.copyOfRange(args, 1, args.length));
                return true;
            } // ir give ...

            if ("list".equalsIgnoreCase(args[0])) {
                if (!player.hasPermission("ir.list")) {
                    Helper.sendMessage(player, Helper.getStringInConfig("message.user.notPermission", false));
                    return true;
                }

                for (ECustomItem eCustomItem : ECustomItem.values()) {
                    player.sendMessage(ChatColor.GOLD + eCustomItem.toString());
                }
                return true;
            } // ir list

            Helper.sendMessage(player, Helper.getStringInConfig("message.user.unknownCommand", false));
            return true;
        }

        if (sender instanceof ConsoleCommandSender console) {

            if (args.length < 1 || "help".equalsIgnoreCase(args[0])) {
                CommandManager.sendHelpMenu(console);
                return true;
            }

            if ("give".equalsIgnoreCase(args[0])) {
                CommandManager.giveCustomsItem(console, Arrays.copyOfRange(args, 1, args.length));
                return true;
            } // ir give ...

            if ("list".equalsIgnoreCase(args[0])) {
                for (ECustomItem eCustomItem : ECustomItem.values()) {
                    console.sendMessage(ChatColor.GOLD + eCustomItem.toString());
                }

                return true;
            } // ir list

            Helper.sendMessage(console, Helper.getStringInConfig("message.user.unknownCommand", false));
            return true;
        }

        return false;
    }

    private static void giveCustomsItem(CommandSender sender, String @NotNull [] args) {
        if (args.length < 1) {
            Helper.sendMessage(sender, Helper.getStringInConfig("message.user.misuseCommand", false));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            Helper.sendMessage(sender, Helper.getStringInConfig("message.user.userNotFound", false));
            return;
        }

        if (args.length < 2) {
            Helper.sendMessage(sender, Helper.getStringInConfig("message.user.misuseCommand", false));
            return;
        }

        if ("all".equalsIgnoreCase(args[1])) {
            for (ECustomItem customItemType : ECustomItem.values()) {
                target.getInventory().addItem(CustomItem.getCustomItem(customItemType));
            }
        } else {
            int amount = 1;
            ECustomItem customItemType;
            int level = 1;

            try {
                customItemType = ECustomItem.valueOf(args[1]);
            } catch (IllegalArgumentException ex) {
                Helper.sendMessage(sender, Helper.getStringInConfig("message.user.customItemNotFound", false));
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

    private static void sendHelpMenu(@NotNull CommandSender sender) {
        sender.sendMessage(ChatColor.AQUA + "================== ItemReward ==================");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/ir give " + ChatColor.GOLD + "<player> <item> " + ChatColor.GRAY +
                           " [<count>] [<level>]" + ChatColor.WHITE + " : Give custom items to a player.");
        sender.sendMessage(
                ChatColor.LIGHT_PURPLE + "/ir give " + ChatColor.GOLD + "<player> " + ChatColor.LIGHT_PURPLE + "all" +
                ChatColor.WHITE + " : Give all custom items to a player.");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/ir list" + ChatColor.WHITE + " : Display all custom items.");
        sender.sendMessage(ChatColor.AQUA + "================== ItemReward ==================");
    }
}
