package fr.lataverne.itemreward.items;

import fr.lataverne.itemreward.managers.CustomItem;
import org.bukkit.Material;
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

public class RawBear extends CustomItem {
	public RawBear(int amount) {
		super(Material.BEEF, amount);

		ItemMeta itemMeta = Objects.requireNonNull(this.getItemMeta(), "Item meta can't be null");

		itemMeta.setCustomModelData(2);

		if (configPathExists(this.getConfigPath() + ".displayName")) {
			itemMeta.setDisplayName(getStringInConfig(this.getConfigPath() + ".displayName", true));
		}

		if (configPathExists(this.getConfigPath() + ".lore")) {
			itemMeta.setLore(getStringListInConfig(this.getConfigPath() + ".lore", true));
		}

		this.setItemMeta(itemMeta);
	}

	public RawBear(ItemStack itemStack) {
		super(itemStack);
	}

	@Override
	public ECustomItem getCustomItemType() {
		return ECustomItem.RawBear;
	}

	@Override
	protected String getConfigPath() {
		return "item.rawBear";
	}

	@Override
	protected void onBlockCook(BlockCookEvent e) {
		switch (e.getBlock().getType()) {
			case CAMPFIRE:
			case SOUL_CAMPFIRE:
				e.setResult(new CookedBear(1));
				break;
			default:
				e.setCancelled(true);
				break;
		}
	}

	@Override
	protected void onInventoryClick(InventoryClickEvent e) {
		cantCooked(e);
	}

	@Override
	protected void onPlayerItemConsume(PlayerItemConsumeEvent e) {
		Player player = e.getPlayer();

		player.setFoodLevel(Math.min(player.getFoodLevel() + 3, 20));
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 3));
	}
}
