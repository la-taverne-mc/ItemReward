package fr.lataverne.itemreward.api.items;

import fr.lataverne.itemreward.managers.CustomItem;
import fr.lataverne.itemreward.managers.ECustomItem;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public enum CustomItems {
    ;

    /**
     * Initializes and returns the custom item by its name.
     *
     * @param customItemName Name of custom item.
     * @param amount Quantity of custom items.
     * @param level Level of the custom items.
     *
     * @return The created custom item if it exists. Else null.
     */
    public static @Nullable CustomItem getCustomItems(String customItemName, int amount, int level) {
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
     * @param amount Quantity of the custom items.
     * @param level Level of the custom items.
     *
     * @return The created custom item.
     */
    public static CustomItem getCustomItems(ECustomItem customItem, int amount, int level) {
        return CustomItem.getCustomItem(customItem, amount, level);
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
