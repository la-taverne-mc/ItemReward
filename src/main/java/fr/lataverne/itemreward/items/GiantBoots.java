package fr.lataverne.itemreward.items;

import fr.lataverne.itemreward.Helper;
import fr.lataverne.itemreward.api.objects.CustomItem;
import fr.lataverne.itemreward.managers.ECustomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class GiantBoots extends CustomItem {

    private static final String NBTTagCounter = "counter";

    public GiantBoots(int amount) {
        super(Material.LEATHER_BOOTS, amount);

        ItemMeta itemMeta = Objects.requireNonNull(this.getItemMeta(), "Item meta can't be null");

        itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.fromString("ec3474c3-b57c-4fbf-ba1f-a7c5ac9292c5"), "generic.armor", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
        itemMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("dcc66b53-a22c-48cc-afbd-274a02967392"), "generic.movementSpeed", 0.75, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlot.FEET));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        if (Helper.configPathExists(this.getConfigPath() + ".displayName")) {
            itemMeta.setDisplayName(Helper.getStringInConfig(this.getConfigPath() + ".displayName", true));
        }

        if (Helper.configPathExists(this.getConfigPath() + ".lore")) {
            itemMeta.setLore(Helper.getStringListInConfig(this.getConfigPath() + ".lore", true));
        }

        itemMeta.setCustomModelData(1);

        Helper.addNBT(this, "counter", "0");

        this.setItemMeta(itemMeta);
    }

    public GiantBoots(ItemStack itemStack) {
        super(itemStack);
    }

    @Override
    public ECustomItem getCustomItemType() {
        return ECustomItem.GiantBoots;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        Helper.cantRepairableAndEnchanted(e);
        Helper.cantUseInCraft(e);
    }

    @Override
    public void onPlayerMove(@NotNull PlayerMoveEvent e) {
        Location from = Objects.requireNonNull(e.getFrom());
        Location to = Objects.requireNonNull(e.getTo());

        ItemStack boots = Objects.requireNonNull(e.getPlayer().getInventory().getBoots());

        if (from.distanceSquared(to) > 0.14) {
            Location loc = e.getPlayer().getLocation();
            loc.setY(loc.getY() - 2);

            Block block = Objects.requireNonNull(loc.getWorld()).getBlockAt(loc);
            if (block.getType().isAir()) {
                return;
            }

            int counter = 0;

            try {
                String strCounter = Helper.getNBT(boots, GiantBoots.NBTTagCounter);
                if (strCounter != null) {
                    counter = Integer.parseInt(strCounter);
                }
            } catch (NumberFormatException ignored) {

            }

            counter++;

            if (counter >= Helper.getIntInConfig(this.getConfigPath() + ".counter")) {
                counter = 0;
                Damageable itemMeta = (Damageable) Objects.requireNonNull(boots.getItemMeta());

                itemMeta.setDamage(itemMeta.getDamage() + 1);

                boots.setItemMeta(itemMeta);

                if (itemMeta.getDamage() > Material.LEATHER_BOOTS.getMaxDurability()) {
                    e.getPlayer().getInventory().setBoots(null);
                }
            }

            Helper.addNBT(boots, GiantBoots.NBTTagCounter, Integer.toString(counter));
        }
    }

    @Override
    protected String getConfigPath() {
        return "item.giantBoots";
    }
}
