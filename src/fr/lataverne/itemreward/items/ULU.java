package fr.lataverne.itemreward.items;

import fr.lataverne.itemreward.managers.CustomItem;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;
import java.util.UUID;

import static fr.lataverne.itemreward.Helper.*;

public class ULU extends CustomItem {
	public ULU(int amount) {
		super(Material.STONE_AXE, amount);

		ItemMeta itemMeta = Objects.requireNonNull(this.getItemMeta(), "Item meta can't be null");

		itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("1de4ca9e-fe66-4222-966d-73f226e8fecd"), "generic.attackDamage", 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
		itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.fromString("c26bf64f-1a1a-4425-bc4e-77dc56845f8b"), "generic.attackSpeed", -0.65, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlot.HAND));
		itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("10cea9ed-2627-4a6e-b904-52228bbff57b"), "generic.attackDamage", 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.OFF_HAND));
		itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.fromString("48c680ff-6d3a-4bff-b62c-2badd65cf045"), "generic.attackSpeed", -0.65, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlot.OFF_HAND));

		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

		itemMeta.setCustomModelData(1);

		if (configPathExists(this.getConfigPath() + ".displayName")) {
			itemMeta.setDisplayName(getStringInConfig(this.getConfigPath() + ".displayName", true));
		}

		if (configPathExists(this.getConfigPath() + ".lore")) {
			itemMeta.setLore(getStringListInConfig(this.getConfigPath() + ".lore", true));
		}

		this.setItemMeta(itemMeta);
	}

	public ULU(ItemStack itemStack) {
		super(itemStack);
	}

	@Override
	public ECustomItem getCustomItemType() {
		return ECustomItem.ULU;
	}

	@Override
	protected String getConfigPath() {
		return "item.ulu";
	}

	@Override
	protected void onEntityDeath(EntityDeathEvent e) {
		if (e.getEntity().getType().equals(EntityType.POLAR_BEAR) && e.getEntity().getKiller() != null) {
			Player killer = e.getEntity().getKiller();

			World world = Objects.requireNonNull(killer.getLocation().getWorld());
			world.dropItemNaturally(e.getEntity().getLocation(), new RawBear(1));
		}
	}

	@Override
	protected void onInventoryClick(InventoryClickEvent e) {
		cantUseInCraft(e);
		cantRepairableAndEnchanted(e);
	}
}
