package fr.lataverne.itemreward.items;

import fr.lataverne.itemreward.managers.CustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

import static fr.lataverne.itemreward.Helper.*;

public class UnbreakableHoe extends CustomItem {
    public UnbreakableHoe(int amount) {
        super(Material.STONE_HOE, amount);

        ItemMeta itemMeta = Objects.requireNonNull(this.getItemMeta(), "Item meta can't be null");

        itemMeta.addEnchant(Enchantment.DURABILITY, 20, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        if (configPathExists(this.getConfigPath() + ".displayName")) {
            itemMeta.setDisplayName(getStringInConfig(this.getConfigPath() + ".displayName", true));
        }

        if (configPathExists(this.getConfigPath() + ".lore")) {
            itemMeta.setLore(getStringListInConfig(this.getConfigPath() + ".lore", true));
        }

        this.setItemMeta(itemMeta);
    }

    public UnbreakableHoe(ItemStack itemStack) {
        super(itemStack);
    }

    @Override
    public ECustomItem getCustomItemType() {
        return ECustomItem.UnbreakableHoe;
    }

    @Override
    protected String getConfigPath() {
        return "item.unbreakableHoe";
    }

    @Override
    protected void onInventoryClick(InventoryClickEvent e) {
        cantUseInCraft(e);
        cantRepairableAndEnchanted(e);

        InventoryType inventoryType = extractInventoryType(e);

        if (inventoryType == InventoryType.GRINDSTONE) {
            e.setCancelled(true);
        }
    }
}