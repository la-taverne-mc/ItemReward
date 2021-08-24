package fr.lataverne.itemreward.managers;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.Objects;

import static fr.lataverne.itemreward.Helper.*;

public abstract class CustomPotion extends CustomItem {
	private static final String NBTTagCustomPotionType = "CustomPotionType";

	private static final String NBTTagLevel = "CustomPotionLevel";

	protected int level = 1;

	public CustomPotion(int level) {
		super(Material.POTION);

		PotionMeta itemMeta = (PotionMeta) Objects.requireNonNull(this.getItemMeta());

		itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		itemMeta.setColor(this.getPotionColor());

		this.setItemMeta(itemMeta);

		this.level = level;

		this.init();
	}

	public CustomPotion(ItemStack itemStack) {
		super(itemStack);

		if (hasNBT(itemStack, NBTTagLevel)) {
			this.level = Integer.parseInt(Objects.requireNonNull(getNBT(itemStack, NBTTagLevel)));
		}
	}

	public Color getPotionColor() {
		int red = getIntInConfig(this.getConfigPath() + ".color.red");
		int green = getIntInConfig(this.getConfigPath() + ".color.green");
		int blue = getIntInConfig(this.getConfigPath() + ".color.blue");

		return Color.fromRGB(red, green, blue);
	}

	private void init() {
		addNBT(this, NBTTagCustomPotionType, this.getCustomItemType().toString());
		addNBT(this, NBTTagLevel, Integer.toString(this.level));
	}
}
