package fr.lataverne.itemreward.items;

import fr.lataverne.itemreward.managers.CustomItem;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockCookEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

import static fr.lataverne.itemreward.Helper.*;

public class RawHorse extends CustomItem {
	public RawHorse() {
		super(Material.BEEF);

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

	public RawHorse(ItemStack itemStack) {
		super(itemStack);
	}

	@Override
	public ECustomItem getCustomItemType() {
		return ECustomItem.RawHorse;
	}

	@Override
	protected String getConfigPath() {
		return "item.rawHorse";
	}

	@Override
	protected void onBlockCook(BlockCookEvent e) {
		if (e.getBlock().getType() == Material.CAMPFIRE) {
			e.setResult(new CookedHorse());
		} else {
			e.setCancelled(true);
		}
	}

	@Override
	protected void onInventoryClick(InventoryClickEvent e) {
		cantCooked(e);
	}

	@Override
	protected void onPlayerItemConsume(PlayerItemConsumeEvent e) throws NotImplementedException {
		Player player = e.getPlayer();

		player.setFoodLevel(Math.min(player.getFoodLevel() + 3, 20));
		player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 3));
	}
}
