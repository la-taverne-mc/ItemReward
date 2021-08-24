package fr.lataverne.itemreward.items;

import fr.lataverne.itemreward.managers.CustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Objects;

import static fr.lataverne.itemreward.Helper.*;

public class MendingBook extends CustomItem {
	public MendingBook() {
		super(Material.ENCHANTED_BOOK);

		EnchantmentStorageMeta itemMeta = (EnchantmentStorageMeta) Objects.requireNonNull(this.getItemMeta(), "Item meta can't be null");

		itemMeta.addEnchant(Enchantment.MENDING, 1, false);

		if (configPathExists(this.getConfigPath() + ".displayName")) {
			itemMeta.setDisplayName(getStringInConfig(this.getConfigPath() + ".displayName", true));
		}

		if (configPathExists(this.getConfigPath() + ".lore")) {
			itemMeta.setLore(getStringListInConfig(this.getConfigPath() + ".lore", true));
		}

		this.setItemMeta(itemMeta);
	}

	public MendingBook(ItemStack itemStack) {
		super(itemStack);
	}

	@Override
	public ECustomItem getCustomItemType() {
		return ECustomItem.MendingBook;
	}

	@Override
	protected String getConfigPath() {
		return "item.mendingBook";
	}

	@Override
	protected void onInventoryClick(InventoryClickEvent e) {
		InventoryType inventoryType = e.getInventory().getType();
		int rawSlot = e.getRawSlot();

		if (inventoryType == InventoryType.GRINDSTONE && rawSlot < 3) {
			e.setCancelled(true);
		}
	}
}
