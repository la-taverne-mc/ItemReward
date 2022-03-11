package fr.lataverne.itemreward.api.objects;

import fr.lataverne.itemreward.managers.ECustomItem;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a custom item of ItemReward.
 */
public interface ICustomItem {

    /**
     * Gets the amount of custom items.
     *
     * @return Amount of items.
     */
    int getAmount();

    /**
     * Gets the type of the custom type.
     *
     * @return Type of the custom item.
     */
    ECustomItem getCustomItemType();

    /**
     * Gets the custom item.
     *
     * @return Represented custom item.
     */
    ItemStack getItemStack();

    /**
     * Gets the level of the custom item.
     *
     * @return Level of the custom item.
     */
    int getLevel();
}
