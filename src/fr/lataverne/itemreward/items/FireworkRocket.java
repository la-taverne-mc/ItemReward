package fr.lataverne.itemreward.items;

import fr.lataverne.itemreward.managers.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Objects;

import static fr.lataverne.itemreward.Helper.*;

public class FireworkRocket extends CustomItem {
	public FireworkRocket() {
		super(Material.FIREWORK_ROCKET, 64);

		FireworkMeta itemMeta = (FireworkMeta) Objects.requireNonNull(this.getItemMeta(), "Item meta can't be null");

		itemMeta.setPower(3);

		if (configPathExists(this.getConfigPath() + ".displayName")) {
			itemMeta.setDisplayName(getStringInConfig(this.getConfigPath() + ".displayName", true));
		}

		if (configPathExists(this.getConfigPath() + ".lore")) {
			itemMeta.setLore(getStringListInConfig(this.getConfigPath() + ".lore", true));
		}

		this.setItemMeta(itemMeta);
	}

	public FireworkRocket(ItemStack itemStack) {
		super(itemStack);
	}

	@Override
	public ECustomItem getCustomItemType() {
		return ECustomItem.FireworkRocket;
	}

	@Override
	protected String getConfigPath() {
		return "item.fireworkRocket";
	}
}
