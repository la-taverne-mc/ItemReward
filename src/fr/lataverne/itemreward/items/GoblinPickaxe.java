package fr.lataverne.itemreward.items;

import fr.lataverne.itemreward.managers.CustomItem;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;
import java.util.Random;

import static fr.lataverne.itemreward.Helper.*;

public class GoblinPickaxe extends CustomItem {
	public GoblinPickaxe(int amount) {
		super(Material.GOLDEN_PICKAXE, amount);

		ItemMeta itemMeta = Objects.requireNonNull(this.getItemMeta(), "Item meta can't be null");

		if (configPathExists(this.getConfigPath() + ".displayName")) {
			itemMeta.setDisplayName(getStringInConfig(this.getConfigPath() + ".displayName", true));
		}

		if (configPathExists(this.getConfigPath() + ".lore")) {
			itemMeta.setLore(getStringListInConfig(this.getConfigPath() + ".lore", true));
		}

		itemMeta.setCustomModelData(1);

		this.setItemMeta(itemMeta);
	}

	public GoblinPickaxe(ItemStack itemStack) {
		super(itemStack);
	}

	@Override
	public ECustomItem getCustomItemType() {
		return ECustomItem.GoblinPickaxe;
	}

	@Override
	protected String getConfigPath() {
		return "item.goblinPickaxe";
	}

	@Override
	protected void onBlockBreak(BlockBreakEvent e) {
		if (e.getBlock().getLocation().getWorld() == null) {
			return;
		}

		if (e.getBlock().getType().equals(Material.STONE)) {
			e.setDropItems(false);

			int mineralRand = new Random().nextInt(65);
			if (mineralRand <= 14) {
				e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.IRON_NUGGET, getRandomNumberInRange(7, 11)));
			} else if (mineralRand <= 29) {
				e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.LAPIS_LAZULI, getRandomNumberInRange(6, 10)));
			} else if (mineralRand <= 39) {
				e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.REDSTONE, getRandomNumberInRange(6, 10)));
			} else if (mineralRand <= 49) {
				e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.GOLD_NUGGET, getRandomNumberInRange(6, 10)));
			} else if (mineralRand <= 57) {
				e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.DIAMOND, getRandomNumberInRange(1, 2)));
			} else {
				e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.EMERALD, getRandomNumberInRange(1, 2)));
			}
		} else if (e.getBlock().getType().equals(Material.NETHERRACK)) {
			e.setDropItems(false);
			e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.QUARTZ, getRandomNumberInRange(1, 5)));
		}
	}

	@Override
	protected void onInventoryClick(InventoryClickEvent e) {
		cantRepairableAndEnchanted(e);
		cantUseInCraft(e);
		cantCooked(e);
	}
}
