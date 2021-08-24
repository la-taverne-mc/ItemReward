package fr.lataverne.itemreward.items;

import fr.lataverne.itemreward.managers.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

import static fr.lataverne.itemreward.Helper.*;

public class CookedHorse extends CustomItem {
	public CookedHorse() {
		super(Material.COOKED_BEEF);

		ItemMeta itemMeta = Objects.requireNonNull(this.getItemMeta(), "Item meta can't be null");

		itemMeta.setCustomModelData(1);

		if (configPathExists(this.getConfigPath() + ".displayName")) {
			itemMeta.setDisplayName(getStringInConfig(this.getConfigPath() + ".displayName", true));
		}

		if (configPathExists(this.getConfigPath() + ".lore")) {
			itemMeta.setLore(getStringListInConfig(this.getConfigPath() + ".lore", true));
		}

		this.setItemMeta(itemMeta);
	}

	public CookedHorse(ItemStack itemStack) {
		super(itemStack);
	}

	@Override
	public ECustomItem getCustomItemType() {
		return ECustomItem.CookedHorse;
	}

	@Override
	protected String getConfigPath() {
		return "item.cookedHorse";
	}

	@Override
	protected void onPlayerItemConsume(PlayerItemConsumeEvent e) {
		Player player = e.getPlayer();

		player.setFoodLevel(Math.min(player.getFoodLevel() + 5, 20));
	}
}
