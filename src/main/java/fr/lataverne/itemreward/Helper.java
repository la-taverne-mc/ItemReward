package fr.lataverne.itemreward;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public enum Helper {
    ;

    private static final Pattern REGEX_CHATCOLOR_AQUA = Pattern.compile("§b|&b");

    private static final Pattern REGEX_CHATCOLOR_BLACK = Pattern.compile("§0|&0");

    private static final Pattern REGEX_CHATCOLOR_BLUE = Pattern.compile("§9|&9");

    private static final Pattern REGEX_CHATCOLOR_BOLD = Pattern.compile("§l|&l");

    private static final Pattern REGEX_CHATCOLOR_DARK_AQUA = Pattern.compile("§3|&3");

    private static final Pattern REGEX_CHATCOLOR_DARK_BLUE = Pattern.compile("§1|&1");

    private static final Pattern REGEX_CHATCOLOR_DARK_GRAY = Pattern.compile("§8|&8");

    private static final Pattern REGEX_CHATCOLOR_DARK_GREEN = Pattern.compile("§2|&2");

    private static final Pattern REGEX_CHATCOLOR_DARK_PURPLE = Pattern.compile("§5|&5");

    private static final Pattern REGEX_CHATCOLOR_DARK_RED = Pattern.compile("§4|&4");

    private static final Pattern REGEX_CHATCOLOR_GOLD = Pattern.compile("§6|&6");

    private static final Pattern REGEX_CHATCOLOR_GRAY = Pattern.compile("§7|&7");

    private static final Pattern REGEX_CHATCOLOR_GREEN = Pattern.compile("§a|&a");

    private static final Pattern REGEX_CHATCOLOR_ITALIC = Pattern.compile("§o|&o");

    private static final Pattern REGEX_CHATCOLOR_LIGHT_PURPLE = Pattern.compile("§d|&d");

    private static final Pattern REGEX_CHATCOLOR_MAGIC = Pattern.compile("§k|&k");

    private static final Pattern REGEX_CHATCOLOR_RED = Pattern.compile("§c|&c");

    private static final Pattern REGEX_CHATCOLOR_RESET = Pattern.compile("§r|&r");

    private static final Pattern REGEX_CHATCOLOR_STRIKETHROUGH = Pattern.compile("§m|&m");

    private static final Pattern REGEX_CHATCOLOR_UNDERLINE = Pattern.compile("§n|&n");

    private static final Pattern REGEX_CHATCOLOR_WHITE = Pattern.compile("§f|&f");

    private static final Pattern REGEX_CHATCOLOR_YELLOW = Pattern.compile("§e|&e");

    public static void addNBT(@NotNull ItemStack item, String key, String value) {
        Objects.requireNonNull(ItemReward.getInstance(), "ItemReward hasn't been initialized.");
        Objects.requireNonNull(item, "item must not be null");

        ItemMeta meta = item.hasItemMeta()
                        ? item.getItemMeta()
                        : Bukkit.getItemFactory().getItemMeta(item.getType());

        Objects.requireNonNull(meta, "Item meta must not be null");

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        NamespacedKey namespacedKey = new NamespacedKey(ItemReward.getInstance(), key);
        pdc.set(namespacedKey, PersistentDataType.STRING, value);

        item.setItemMeta(meta);
    }

    public static void cantCooked(InventoryClickEvent e) {
        InventoryType inventoryType = Helper.extractInventoryType(e);

        if (inventoryType != null) {
            switch (inventoryType) {
                case SMOKER, FURNACE, BLAST_FURNACE -> e.setCancelled(true);
            }
        }
    }
    
    public static void cantRepairableAndEnchanted(InventoryClickEvent e) {
        InventoryType inventoryType = Helper.extractInventoryType(e);

        if (inventoryType != null) {
            switch (inventoryType) {
                case ANVIL, GRINDSTONE, ENCHANTING -> e.setCancelled(true);
            }
        }
    }

    public static void cantUseInCraft(InventoryClickEvent e) {
        InventoryType inventoryType = Helper.extractInventoryType(e);

        int rawSlot = e.getRawSlot();

        if (inventoryType != null) {
            switch (inventoryType) {
                case WORKBENCH:
                    e.setCancelled(true);
                    break;
                case CRAFTING:
                    if (rawSlot < 5) {
                        e.setCancelled(true);
                    }
                    break;
            }
        }
    }

    public static @Nullable String colorizeString(String str) {
        if (str == null) {
            return null;
        }

        String output = str;

        output = Helper.REGEX_CHATCOLOR_BLACK.matcher(output).replaceAll(ChatColor.BLACK + "");
        output = Helper.REGEX_CHATCOLOR_DARK_BLUE.matcher(output).replaceAll(ChatColor.DARK_BLUE + "");
        output = Helper.REGEX_CHATCOLOR_DARK_GREEN.matcher(output).replaceAll(ChatColor.DARK_GREEN + "");
        output = Helper.REGEX_CHATCOLOR_DARK_AQUA.matcher(output).replaceAll(ChatColor.DARK_AQUA + "");
        output = Helper.REGEX_CHATCOLOR_DARK_RED.matcher(output).replaceAll(ChatColor.DARK_RED + "");
        output = Helper.REGEX_CHATCOLOR_DARK_PURPLE.matcher(output).replaceAll(ChatColor.DARK_PURPLE + "");
        output = Helper.REGEX_CHATCOLOR_GOLD.matcher(output).replaceAll(ChatColor.GOLD + "");
        output = Helper.REGEX_CHATCOLOR_GRAY.matcher(output).replaceAll(ChatColor.GRAY + "");
        output = Helper.REGEX_CHATCOLOR_DARK_GRAY.matcher(output).replaceAll(ChatColor.DARK_GRAY + "");
        output = Helper.REGEX_CHATCOLOR_BLUE.matcher(output).replaceAll(ChatColor.BLUE + "");
        output = Helper.REGEX_CHATCOLOR_GREEN.matcher(output).replaceAll(ChatColor.GREEN + "");
        output = Helper.REGEX_CHATCOLOR_AQUA.matcher(output).replaceAll(ChatColor.AQUA + "");
        output = Helper.REGEX_CHATCOLOR_RED.matcher(output).replaceAll(ChatColor.RED + "");
        output = Helper.REGEX_CHATCOLOR_LIGHT_PURPLE.matcher(output).replaceAll(ChatColor.LIGHT_PURPLE + "");
        output = Helper.REGEX_CHATCOLOR_YELLOW.matcher(output).replaceAll(ChatColor.YELLOW + "");
        output = Helper.REGEX_CHATCOLOR_WHITE.matcher(output).replaceAll(ChatColor.WHITE + "");
        output = Helper.REGEX_CHATCOLOR_MAGIC.matcher(output).replaceAll(ChatColor.MAGIC + "");
        output = Helper.REGEX_CHATCOLOR_BOLD.matcher(output).replaceAll(ChatColor.BOLD + "");
        output = Helper.REGEX_CHATCOLOR_STRIKETHROUGH.matcher(output).replaceAll(ChatColor.STRIKETHROUGH + "");
        output = Helper.REGEX_CHATCOLOR_UNDERLINE.matcher(output).replaceAll(ChatColor.UNDERLINE + "");
        output = Helper.REGEX_CHATCOLOR_ITALIC.matcher(output).replaceAll(ChatColor.ITALIC + "");
        output = Helper.REGEX_CHATCOLOR_RESET.matcher(output).replaceAll(ChatColor.RESET + "");

        return output;
    }

    public static boolean configPathExists(String path) {
        return ItemReward.getInstance().getConfig().contains(path);
    }

    public static String convertTime(int time) {
        int t = time;
        String sTime = "";

        if (t >= 3600) {
            sTime += t / 3600 + "h";
            t = t % 3600;
        }

        if (t >= 60) {
            sTime += t / 60 + "min";
            t = t % 60;
        }

        if (t >= 1) {
            sTime += t + "s";
        }

        return sTime;
    }

    public static @Nullable InventoryType extractInventoryType(@NotNull InventoryClickEvent e) {
        InventoryType inventoryType;

        if (e.getClick().isShiftClick()) {
            inventoryType = e.getInventory().getType();
        } else {
            if (e.getClickedInventory() == null) {
                return null;
            } else {
                inventoryType = e.getClickedInventory().getType();
            }
        }

        return inventoryType;
    }

    public static int getIntInConfig(String path) {
        return ItemReward.getInstance().getConfig().getInt(path);
    }

    public static @Nullable String getNBT(@NotNull ItemStack item, String key) {
        Objects.requireNonNull(ItemReward.getInstance(), "ItemReward hasn't been initialized.");
        Objects.requireNonNull(item, "item must not be null");

        ItemMeta meta = Objects.requireNonNull(item.getItemMeta(), "Item meta must not be null");

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        NamespacedKey namespacedKey = new NamespacedKey(ItemReward.getInstance(), key);

        return pdc.has(namespacedKey, PersistentDataType.STRING)
               ? pdc.get(namespacedKey, PersistentDataType.STRING)
               : null;
    }

    public static int getRandomNumberInRange(int min, int max) {
        return new SecureRandom().nextInt((max - min) + 1) + min;
    }

    public static String getStringInConfig(String path, boolean colorization) {
        String str = ItemReward.getInstance().getConfig().getString(path);

        if (colorization) {
            str = Helper.colorizeString(str);
        }

        return str;
    }

    public static @NotNull List<String> getStringListInConfig(String path, boolean colorization) {
        List<String> stringList = ItemReward.getInstance().getConfig().getStringList(path);

        if (colorization) {
            int size = stringList.size();
            for (int i = 0; i < size; i++) {
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

    public static String replaceValueInString(String str, String @NotNull ... args) {
        String output = str;
        int length = args.length;
        for (int i = 0; i < length; i++) {
            output = output.replace("{" + (i + 1) + "}", args[i]);
        }

        return output;
    }

    public static void sendBarMessage(@NotNull Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Helper.colorizeString(message)));
    }

    public static void sendMessage(@NotNull CommandSender sender, String message) {
        String suffixMessage = Helper.getStringInConfig("message.messageSuffix", false);
        sender.sendMessage(Helper.colorizeString(suffixMessage + " &r" + message));
    }
}

