package fr.lataverne.itemreward.api;

import fr.lataverne.itemreward.api.objects.ICustomItem;
import fr.lataverne.itemreward.managers.CustomItem;
import fr.lataverne.itemreward.managers.ECustomItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

/**
 * Allows to get a custom item.
 */
public enum CustomItems {
    ;

    /**
     * Initializes and returns the custom item by its name.
     *
     * @param customItemName Name of custom item.
     * @param amount Amount of custom items.
     *
     * @return The created custom item if it exists. Else null.
     */
    public static @Nullable ICustomItem getCustomItems(String customItemName, int amount) {
        try {
            ECustomItem customItem = ECustomItem.valueOf(CustomItems.correctCase(customItemName));
            return CustomItems.getCustomItems(customItem, amount);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    /**
     * Initializes and returns the custom item by its name.
     *
     * @param customItemName Name of custom item.
     * @param amount Amount of custom items.
     * @param level Level of the custom item.
     *
     * @return The created custom item if it exists. Else null.
     */
    public static @Nullable ICustomItem getCustomItems(String customItemName, int amount, int level) {
        try {
            ECustomItem customItem = ECustomItem.valueOf(CustomItems.correctCase(customItemName));
            return CustomItems.getCustomItems(customItem, amount, level);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    /**
     * Initializes and returns the custom item.
     *
     * @param customItem Enum that corresponds to the custom item.
     * @param amount Amount of the custom items.
     *
     * @return The created custom item.
     */
    public static ICustomItem getCustomItems(ECustomItem customItem, int amount) {
        return CustomItem.getCustomItem(customItem, amount);
    }

    /**
     * Initializes and returns the custom item.
     *
     * @param customItem Enum that corresponds to the custom item.
     * @param amount Amount of custom items.
     * @param level Level of the custom item.
     *
     * @return The created custom item.
     */
    public static ICustomItem getCustomItems(ECustomItem customItem, int amount, int level) {
        return CustomItem.getCustomItem(customItem, amount, level);
    }

    /**
     * Gets a custom item with an existed item.
     *
     * @param item The custom item represented by an item stack.
     *
     * @return The custom item.
     *
     * @throws IllegalArgumentException throws if the item isn't a custom item.
     */
    public static @NotNull ICustomItem getCustomItems(ItemStack item) {
        ICustomItem customItem = CustomItem.getCustomItem(item);

        if (customItem == null) {
            throw new IllegalArgumentException("The item isn't a custom item");
        } else {
            return customItem;
        }
    }

    /**
     * Checks if an item is a custom item.
     *
     * @param item Item to check.
     *
     * @return True if is a custom item else false.
     */
    public static boolean isCustomItem(ItemStack item) {
        return CustomItem.getCustomItem(item) != null;
    }

    private static String correctCase(String customItemName) {
        for (ECustomItem customItem : ECustomItem.values()) {
            if (customItem.toString().toLowerCase(Locale.ENGLISH).equals(customItemName.toLowerCase(Locale.ENGLISH))) {
                return customItem.toString();
            }
        }

        return customItemName;
    }
}
