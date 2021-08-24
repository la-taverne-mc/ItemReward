package fr.lataverne.itemreward.managers;

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
				return true;
			}

			if (args[0].equalsIgnoreCase("all")) {
				for (CustomItem.ECustomItem customItemType : CustomItem.ECustomItem.values()) {
					player.getInventory().addItem(CustomItem.getCustomItem(customItemType));
				}
			} else {
				CustomItem.ECustomItem customItemType;
				int level = 1;

				try {
					customItemType = CustomItem.ECustomItem.valueOf(args[0]);
				} catch (IllegalArgumentException ex) {
					sendMessageToPlayer(player, getStringInConfig("message.user.customItemNotFound", false));
					return true;
				}

				if (args.length > 1) {
					try {
						level = Integer.parseInt(args[1]);
					} catch (NumberFormatException ignored) {

					}
				}

				CustomItem customItem = CustomItem.getCustomItem(customItemType, level);

				if (customItem != null) {
					player.getInventory().addItem(customItem);
				}
			}

			return true;
		}

		return false;
	}
}
