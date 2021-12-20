package fr.lataverne.itemreward.managers;

import fr.lataverne.itemreward.items.*;
import fr.lataverne.itemreward.items.potions.*;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCookEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.apache.commons.lang3.NotImplementedException;

import static fr.lataverne.itemreward.Helper.*;

public abstract class CustomItem extends ItemStack {

	public enum ECustomItem {
		GoblinPickaxe,
		GiantBoots,
		UnbreakableHoe,
		RawBear,
		RawHorse,
		CookedBear,
		CookedHorse,
		ULU,
		IndianSpear,
		BaseballBat,
		FlyPotion,
		PhantomPotion,
		MiningPotion,
		CreeperPotion,
		SwimmingPotion,
	}

	private static final String NBTTag = "CustomItemType";

	public CustomItem(Material material) {
		this(material, 1);
	}

	public CustomItem(Material material, int amount) {
		super(material, amount);

		this.init();
	}

	public CustomItem(ItemStack itemStack) {
		super(itemStack);
	}

	public static CustomItem getCustomItem(ECustomItem customItemType) {
		return getCustomItem(customItemType, 1);
	}

	public static CustomItem getCustomItem(ECustomItem customItemType, int level) {
		switch (customItemType) {
			case GoblinPickaxe:
				return new GoblinPickaxe();
			case GiantBoots:
				return new GiantBoots();
			case UnbreakableHoe:
				return new UnbreakableHoe();
			case RawBear:
				return new RawBear();
			case RawHorse:
				return new RawHorse();
			case CookedBear:
				return new CookedBear();
			case CookedHorse:
				return new CookedHorse();
			case ULU:
				return new ULU();
			case IndianSpear:
				return new IndianSpear();
			case BaseballBat:
				return new BaseballBat();
			case FlyPotion:
				return new FlyPotion(level);
			case PhantomPotion:
				return new PhantomPotion();
			case MiningPotion:
				return new MiningPotion();
			case CreeperPotion:
				return new CreeperPotion();
			case SwimmingPotion:
				return new SwimmingPotion();
			default:
				return null;
		}
	}

	public static String getEnumItem(String customItemType) {
		switch (customItemType) {
			case "GOBLINPICKAXE":
				return  "GoblinPickaxe";
			case "GIANTBOOTS":
				return  "GiantBoots";
			case "UNBREAKABLEHOE":
				return  "UnbreakableHoe";
			case "RAWBEAR":
				return  "RawBear";
			case "RAWHORSE":
				return  "RawHorse";
			case "COOKEDBEAR":
				return "CookedBear";
			case "COOKEDHORSE":
				return "CookedHorse";
			case "ULU":
				return "ULU";
			case "INDIANSPEAR":
				return "IndianSpear";
			case "BASEBALLBAT":
				return "BaseballBat";
			case "FLYPOTION":
				return "FlyPotion";
			case "PHANTOMPOTION":
				return "PhantomPotion";
			case "MININGPOTION":
				return "MiningPotion";
			case "CREEPERPOTION":
				return "CreeperPotion";
			case "SWIMMINGPOTION":
				return "SwimmingPotion";
			default:
				return null;
		}
	}

	public static CustomItem getCustomItem(ItemStack itemStack) {
		if (itemStack == null || itemStack.getItemMeta() == null || !hasNBT(itemStack, NBTTag)) {
			return null;
		}

		try {
			ECustomItem customItemType = ECustomItem.valueOf(getNBT(itemStack, NBTTag));

			switch (customItemType) {
				case GoblinPickaxe:
					return new GoblinPickaxe(itemStack);
				case GiantBoots:
					return new GiantBoots(itemStack);
				case UnbreakableHoe:
					return new UnbreakableHoe(itemStack);
				case RawBear:
					return new RawBear(itemStack);
				case RawHorse:
					return new RawHorse(itemStack);
				case CookedBear:
					return new CookedBear(itemStack);
				case CookedHorse:
					return new CookedHorse(itemStack);
				case ULU:
					return new ULU(itemStack);
				case IndianSpear:
					return new IndianSpear(itemStack);
				case BaseballBat:
					return new BaseballBat(itemStack);
				case FlyPotion:
					return new FlyPotion(itemStack);
				case PhantomPotion:
					return new PhantomPotion(itemStack);
				case MiningPotion:
					return new MiningPotion(itemStack);
				case CreeperPotion:
					return new CreeperPotion(itemStack);
				case SwimmingPotion:
					return new SwimmingPotion(itemStack);
				default:
					throw new NotImplementedException("Custom item type not implemented");
			}
		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException("Bad NBT tag for the custom item type. Value: " + getNBT(itemStack, NBTTag));
		}
	}

	public static boolean useBlockBreakEvent(CustomItem customItem) {
		//noinspection SwitchStatementWithTooFewBranches
		switch (customItem.getCustomItemType()) {
			case GoblinPickaxe:
				return true;
			default:
				return false;
		}
	}

	public static boolean useBlockCookEvent(CustomItem customItem) {
		switch (customItem.getCustomItemType()) {
			case RawBear:
			case RawHorse:
				return true;
			default:
				return false;
		}
	}

	public static boolean useEntityDeathEvent(CustomItem customItem) {
		switch (customItem.getCustomItemType()) {
			case ULU:
			case IndianSpear:
				return true;
			default:
				return false;
		}
	}

	public static boolean useInventoryClickEvent(CustomItem customItem) {
		switch (customItem.getCustomItemType()) {
			case GiantBoots:
			case GoblinPickaxe:
			case IndianSpear:
			case RawBear:
			case RawHorse:
			case ULU:
			case UnbreakableHoe:
			case BaseballBat:
				return true;
			default:
				return false;
		}
	}

	public static boolean usePlayerItemConsumeEvent(CustomItem customItem) {
		switch (customItem.getCustomItemType()) {
			case RawBear:
			case RawHorse:
			case CookedBear:
			case CookedHorse:
			case FlyPotion:
			case PhantomPotion:
			case MiningPotion:
			case CreeperPotion:
			case SwimmingPotion:
				return true;
			default:
				return false;
		}
	}

	public static boolean usePlayerMoveEvent(CustomItem customItem) {
		//noinspection SwitchStatementWithTooFewBranches
		switch (customItem.getCustomItemType()) {
			case GiantBoots:
				return true;
			default:
				return false;
		}
	}

	public abstract ECustomItem getCustomItemType();

	protected abstract String getConfigPath();

	protected void onBlockBreak(BlockBreakEvent e) throws NotImplementedException {
		throw new NotImplementedException("Not implemented");
	}

	protected void onBlockCook(BlockCookEvent e) throws NotImplementedException {
		throw new NotImplementedException("Not implemented");
	}

	protected void onEntityDeath(EntityDeathEvent e) throws NotImplementedException {
		throw new NotImplementedException("Not implemented");
	}

	protected void onInventoryClick(InventoryClickEvent e) throws NotImplementedException {
		throw new NotImplementedException("Not implemented");
	}

	protected void onPlayerItemConsume(PlayerItemConsumeEvent e) throws NotImplementedException {
		throw new NotImplementedException("Not implemented");
	}

	protected void onPlayerMove(PlayerMoveEvent e) throws NotImplementedException {
		throw new NotImplementedException("Not implemented");
	}

	private void init() {
		addNBT(this, NBTTag, this.getCustomItemType().toString());
	}
}
