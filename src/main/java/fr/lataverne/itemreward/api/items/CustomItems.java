package fr.lataverne.itemreward.api.items;

import fr.lataverne.itemreward.managers.CustomItem;
import fr.lataverne.itemreward.managers.ECustomItem;
import org.jetbrains.annotations.Nullable;

public enum CustomItems {
    ;

    public static @Nullable CustomItem getCustomItems(String customItemName, int amount, int level) {
        try {
            ECustomItem customItem = ECustomItem.valueOf(customItemName);
            return CustomItems.getCustomItems(customItem, amount, level);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    public static CustomItem getCustomItems(ECustomItem customItem, int amount, int level) {
        return CustomItem.getCustomItem(customItem, amount, level);
    }
}
