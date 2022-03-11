package fr.lataverne.itemreward.items;

import fr.lataverne.itemreward.Helper;
import fr.lataverne.itemreward.api.objects.CustomItem;
import fr.lataverne.itemreward.managers.ECustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class BaseballBat extends CustomItem {

    public BaseballBat(int amount) {
        super(Material.WOODEN_SWORD, amount);

        ItemMeta itemMeta = Objects.requireNonNull(this.getItemMeta(), "Item meta can't be null");

        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addEnchant(Enchantment.KNOCKBACK, 20, true);

        if (Helper.configPathExists(this.getConfigPath() + ".displayName")) {
            itemMeta.setDisplayName(Helper.getStringInConfig(this.getConfigPath() + ".displayName", true));
        }

        if (Helper.configPathExists(this.getConfigPath() + ".lore")) {
            itemMeta.setLore(Helper.getStringListInConfig(this.getConfigPath() + ".lore", true));
        }

        itemMeta.setCustomModelData(1);

        this.setItemMeta(itemMeta);

        ItemMeta meta = this.getItemMeta();
        org.bukkit.inventory.meta.Damageable dMeta = (org.bukkit.inventory.meta.Damageable) meta;
        int damage = this.getType().getMaxDurability();
        dMeta.setDamage(damage - 1);
        this.setItemMeta(dMeta);
    }

    public BaseballBat(ItemStack itemStack) {
        super(itemStack);
    }

    @Override
    public ECustomItem getCustomItemType() {
        return ECustomItem.BaseballBat;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        Helper.cantUseInCraft(e);
        Helper.cantRepairableAndEnchanted(e);

        InventoryType inventoryType = Helper.extractInventoryType(e);

        if (inventoryType == InventoryType.GRINDSTONE) {
            e.setCancelled(true);
        }
    }

    @Override
    protected String getConfigPath() {
        return "item.baseballBat";
    }
}
