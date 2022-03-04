package fr.lataverne.itemreward.items;

import fr.lataverne.itemreward.managers.CustomItem;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

import static fr.lataverne.itemreward.Helper.*;

public class IndianSpear extends CustomItem {
    public IndianSpear(int amount) {
        super(Material.GOLDEN_SWORD, amount);

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

    public IndianSpear(ItemStack itemStack) {
        super(itemStack);
    }

    @Override
    public ECustomItem getCustomItemType() {
        return ECustomItem.IndianSpear;
    }

    @Override
    protected String getConfigPath() {
        return "item.indianSpear";
    }

    @Override
    protected void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().getType().equals(EntityType.HORSE) && e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();

            World world = Objects.requireNonNull(killer.getLocation().getWorld());
            world.dropItemNaturally(e.getEntity().getLocation(), new RawHorse(1));
        }
    }

    @Override
    protected void onInventoryClick(InventoryClickEvent e) {
        cantRepairableAndEnchanted(e);
        cantUseInCraft(e);
        cantCooked(e);
    }
}
