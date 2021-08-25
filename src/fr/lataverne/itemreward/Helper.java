package fr.lataverne.itemreward;

import com.sun.istack.internal.NotNull;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@SuppressWarnings ("unused")
public abstract class Helper {

	public static void addNBT(@NotNull ItemStack item, String key, String value) {
		Objects.requireNonNull(ItemReward.getInstance(), "ItemReward hasn't been initialized.");
		Objects.requireNonNull(item, "item must not be null");

		ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());

		Objects.requireNonNull(meta, "Item meta must not be null");

		PersistentDataContainer pdc = meta.getPersistentDataContainer();

		NamespacedKey namespacedKey = new NamespacedKey(ItemReward.getInstance(), key);
		pdc.set(namespacedKey, PersistentDataType.STRING, value);

		item.setItemMeta(meta);
	}

	public static void cantCooked(InventoryClickEvent e) {
		if (e.getClickedInventory() == null) {
			return;
		}

		InventoryType inventoryType = e.getClickedInventory().getType();

		if (inventoryType == InventoryType.SMOKER) {
			e.setCancelled(true);
		} else if (inventoryType == InventoryType.FURNACE) {
			e.setCancelled(true);
		} else if (inventoryType == InventoryType.BLAST_FURNACE) {
			e.setCancelled(true);
		}
	}

	public static void cantRepairableAndEnchanted(InventoryClickEvent e) {
		if (e.getClickedInventory() == null) {
			return;
		}

		InventoryType inventoryType = e.getClickedInventory().getType();

		if (inventoryType == InventoryType.ANVIL) {
			e.setCancelled(true);
		} else if (inventoryType == InventoryType.ENCHANTING) {
			e.setCancelled(true);
		}
	}

	public static void cantUseInCraft(InventoryClickEvent e) {
		if (e.getClickedInventory() == null) {
			return;
		}

		InventoryType inventoryType = e.getClickedInventory().getType();

		if (inventoryType == InventoryType.WORKBENCH) {
			e.setCancelled(true);
		} else if (inventoryType == InventoryType.CRAFTING) {
			e.setCancelled(true);
		}
	}

	public static String colorizeString(String str) {
		if (str == null) {
			return null;
		}

		return str.replaceAll("§0|&0", ChatColor.BLACK + "").replaceAll("§1|&1", ChatColor.DARK_BLUE + "").replaceAll("§2|&2", ChatColor.DARK_GREEN + "").replaceAll("§3|&3", ChatColor.DARK_AQUA + "").replaceAll("§4|&4", ChatColor.DARK_RED + "").replaceAll("§5|&5", ChatColor.DARK_PURPLE + "").replaceAll("§6|&6", ChatColor.GOLD + "").replaceAll("§7|&7", ChatColor.GRAY + "").replaceAll("§8|&8", ChatColor.DARK_GRAY + "").replaceAll("§9|&9", ChatColor.BLUE + "").replaceAll("§a|&a", ChatColor.GREEN + "").replaceAll("§b|&b", ChatColor.AQUA + "").replaceAll("§c|&c", ChatColor.RED + "").replaceAll("§d|&d", ChatColor.LIGHT_PURPLE + "").replaceAll("§e|&e", ChatColor.YELLOW + "").replaceAll("§f|&f", ChatColor.WHITE + "").replaceAll("§k|&k", ChatColor.MAGIC + "").replaceAll("§l|&l", ChatColor.BOLD + "").replaceAll("§m|&m", ChatColor.STRIKETHROUGH + "").replaceAll("§n|&n", ChatColor.UNDERLINE + "").replaceAll("§o|&o", ChatColor.ITALIC + "").replaceAll("§r|&r", ChatColor.RESET + "");
	}

	public static boolean configPathExists(String path) {
		return ItemReward.getInstance().getConfig().contains(path);
	}

	public static String convertTime(int Time) {
		String sTime = "";

		if (Time >= 3600) {
			sTime += String.valueOf(Time / 3600).concat("h");
			Time = Time % 3600;
		}

		if (Time >= 60) {
			sTime += String.valueOf(Time / 60).concat("min");
			Time = Time % 60;
		}

		if (Time >= 1) {
			sTime += String.valueOf(Time).concat("s");
		}

		return sTime;
	}

	public static HashMap<String, String> getAllValues(@NotNull ItemStack item) {
		Objects.requireNonNull(ItemReward.getInstance(), "ItemReward hasn't been initialized.");
		Objects.requireNonNull(item, "item must not be null");

		HashMap<String, String> map = new HashMap<>();

		if (!item.hasItemMeta()) {
			return map;
		}

		ItemMeta meta = Objects.requireNonNull(item.getItemMeta(), "Item meta must not be null");
		PersistentDataContainer pdc = meta.getPersistentDataContainer();

		for (NamespacedKey key : pdc.getKeys()) {
			map.put(key.toString(), pdc.get(key, PersistentDataType.STRING));
		}
		return map;
	}

	public static int getIntInConfig(String path) {
		return ItemReward.getInstance().getConfig().getInt(path);
	}

	public static String getNBT(@NotNull ItemStack item, String key) {
		Objects.requireNonNull(ItemReward.getInstance(), "ItemReward hasn't been initialized.");
		Objects.requireNonNull(item, "item must not be null");

		ItemMeta meta = Objects.requireNonNull(item.getItemMeta(), "Item meta must not be null");

		PersistentDataContainer pdc = meta.getPersistentDataContainer();

		NamespacedKey namespacedKey = new NamespacedKey(ItemReward.getInstance(), key);

		if (pdc.has(namespacedKey, PersistentDataType.STRING)) {
			return pdc.get(namespacedKey, PersistentDataType.STRING);
		}

		return null;
	}

	public static int getRandomNumberInRange(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}

	public static String getStringInConfig(String path, boolean colorization) {
		String str = ItemReward.getInstance().getConfig().getString(path);

		if (colorization) {
			str = Helper.colorizeString(str);
		}

		return str;
	}

	public static List<String> getStringListInConfig(String path, boolean colorization) {
		List<String> stringList = ItemReward.getInstance().getConfig().getStringList(path);

		if (colorization) {
			for (int i = 0; i < stringList.size(); i++) {
				stringList.set(i, Helper.colorizeString(stringList.get(i)));
			}
		}

		return stringList;
	}

	public static boolean hasNBT(@NotNull ItemStack item, String key) {
		Objects.requireNonNull(ItemReward.getInstance(), "ItemReward hasn't been initialized.");
		Objects.requireNonNull(item, "item must not be null");

		if (!item.hasItemMeta()) {
			return false;
		}

		ItemMeta meta = Objects.requireNonNull(item.getItemMeta(), "Item meta must not be null");
		PersistentDataContainer pdc = meta.getPersistentDataContainer();

		return pdc.has(new NamespacedKey(ItemReward.getInstance(), key), PersistentDataType.STRING);
	}

	public static void removeNBT(@NotNull ItemStack item, String key) {
		Objects.requireNonNull(ItemReward.getInstance(), "ItemReward hasn't been initialized.");
		Objects.requireNonNull(item, "item must not be null");

		if (!item.hasItemMeta()) {
			return;
		}

		ItemMeta meta = Objects.requireNonNull(item.getItemMeta(), "Item meta must not be null");
		PersistentDataContainer pdc = meta.getPersistentDataContainer();

		pdc.remove(new NamespacedKey(ItemReward.getInstance(), key));
		item.setItemMeta(meta);
	}

	public static String replaceValueInString(String str, String... args) {
		for (int i = 0; i < args.length; i++) {
			str = str.replace("{" + (i + 1) + "}", args[i]);
		}

		return str;
	}

	public static void sendBarMessage(Player player, String message) {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(colorizeString(message)));
	}

	public static void sendMessageToPlayer(Player player, String message) {
		String suffixMessage = getStringInConfig("message.messageSuffix", false);
		player.sendMessage(colorizeString(suffixMessage + " &r" + message));
	}
}

