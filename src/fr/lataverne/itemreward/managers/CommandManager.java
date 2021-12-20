package fr.lataverne.itemreward.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static fr.lataverne.itemreward.Helper.getStringInConfig;
import static fr.lataverne.itemreward.Helper.sendMessageToPlayer;

public class CommandManager implements CommandExecutor {
	@SuppressWarnings ("NullableProblems")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (args.length < 1) {
				sendMessageToPlayer(player, getStringInConfig("message.user.misuseCommand", false));
				sendMessageToPlayer(player, "Tape /ir help pour en savoir plus");
				return true;
			}

			if (args[0].equalsIgnoreCase("help")){
				sender.sendMessage(
						ChatColor.AQUA+"~~~~~~~~~~~~ iTEmRewArD ~~~~~~~~~~~~~~~~\n" +
						   ChatColor.GOLD+" /ir [command] [arg1] [arg2]\n" +
							   			  " give all                         - get all one item type (lvl 1)\n" +
										  " give [item] [levelItem]          - get this item\n" +
										  " give [item]                      - get this item\n" +
							   			  " give [item] [levelItem] [Player] - give this item to player\n" +
										  " give [item] [Player]             - give this item to player\n" +
							   			  " [list/listItems]                 - list all items\n" +
						   ChatColor.AQUA+"~~~~~~~~~~~~  page 1/1  ~~~~~~~~~~~~~~~~\n"
							);
				return true;
			}
			if (args[0].equalsIgnoreCase("give")) {
				if (!player.hasPermission("ir.get")) {
					sendMessageToPlayer(player, getStringInConfig("message.user.notPermission", false));
					return true;
				}

				if (args.length < 2) {
					sendMessageToPlayer(player, getStringInConfig("message.user.misuseCommand", false));
					sendMessageToPlayer(player, "fait /ir help");
					sendMessageToPlayer(player, "faire /r list pour avoir la liste des items");
				}

				if (args[1].equalsIgnoreCase("all")) {
					for (CustomItem.ECustomItem customItemType : CustomItem.ECustomItem.values()) {
						player.getInventory().addItem(CustomItem.getCustomItem(customItemType));
					}
				}
				else {
					Player target = null;
					CustomItem.ECustomItem customItemType;
					int level = 1;

					try {
						System.out.println(args[1]);
						System.out.println(args[1].toUpperCase());
						System.out.println(CustomItem.getEnumItem(args[1].toUpperCase()));
						customItemType = CustomItem.ECustomItem.valueOf(args[1]);
					} catch (IllegalArgumentException ex) {
						sendMessageToPlayer(player, getStringInConfig("message.user.customItemNotFound", false));
						return true;
					}

					try {
						if (args.length > 2) {
							target = Bukkit.getPlayer(args[2]);
							if(target==null)
								level = Integer.parseUnsignedInt(args[2]);
						}
					} catch (NumberFormatException ignored) {
					}
					if(target==null && args.length==4)
						target = Bukkit.getPlayer(args[3]);

					CustomItem customItem = CustomItem.getCustomItem(customItemType, level);
					if (customItem != null) {
						if(target!=null)
							target.getInventory().addItem(customItem);
						else
							player.getInventory().addItem(customItem);
					}
				}

				return true;
			} // ir get ...

			/**
			 * List item
			 */
			if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("listItems")) {
				for (CustomItem.ECustomItem eCustomItem : CustomItem.ECustomItem.values()) {
						player.sendMessage(ChatColor.GOLD + eCustomItem.toString());
					}
				return true;
			} // ir view ...

			sendMessageToPlayer(player, getStringInConfig("message.user.unknownCommand", false));
			return true;
		}

		return false;
	}
}
