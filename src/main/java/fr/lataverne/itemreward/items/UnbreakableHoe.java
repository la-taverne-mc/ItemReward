package fr.lataverne.itemreward.items;

import fr.lataverne.itemreward.Helper;
import fr.lataverne.itemreward.managers.CustomItem;
import fr.lataverne.itemreward.managers.ECustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class UnbreakableHoe extends CustomItem {

    public UnbreakableHoe(int amount) {
        super(Material.STONE_HOE, amount);

        ItemMeta itemMeta = Objects.requireNonNull(this.getItemMeta(), "Item meta can't be null");

        itemMeta.addEnchant(Enchantment.DURABILITY, 20, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        if (Helper.configPathExists(this.getConfigPath() + ".displayName")) {
            itemMeta.setDisplayName(Helper.getStringInConfig(this.getConfigPath() + ".displayName", true));
        }

        if (Helper.configPathExists(this.getConfigPath() + ".lore")) {
            itemMeta.setLore(Helper.getStringListInConfig(this.getConfigPath() + ".lore", true));
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
        Helper.cantUseInCraft(e);
        Helper.cantRepairableAndEnchanted(e);

        InventoryType inventoryType = Helper.extractInventoryType(e);

        if (inventoryType == InventoryType.GRINDSTONE) {
            e.setCancelled(true);
        }
    }
}
