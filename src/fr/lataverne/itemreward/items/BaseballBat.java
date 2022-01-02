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

public class BaseballBat extends CustomItem {
	public BaseballBat(int amount) {
		super(Material.WOODEN_SWORD, amount);

		ItemMeta itemMeta = Objects.requireNonNull(this.getItemMeta(), "Item meta can't be null");

		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemMeta.addEnchant(Enchantment.KNOCKBACK, 20, true);

		if (configPathExists(this.getConfigPath() + ".displayName")) {
			itemMeta.setDisplayName(getStringInConfig(this.getConfigPath() + ".displayName", true));
		}

		if (configPathExists(this.getConfigPath() + ".lore")) {
			itemMeta.setLore(getStringListInConfig(this.getConfigPath() + ".lore", true));
		}

		itemMeta.setCustomModelData(1);

		this.setItemMeta(itemMeta);

		ItemMeta meta = this.getItemMeta();
		org.bukkit.inventory.meta.Damageable dMeta = (org.bukkit.inventory.meta.Damageable) meta ;
		int damage = this.getType().getMaxDurability();
		dMeta.setDamage(damage - 1 );
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
	protected String getConfigPath() {
		return "item.baseballBat";
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
