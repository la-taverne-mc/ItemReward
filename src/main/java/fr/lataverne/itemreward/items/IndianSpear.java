package fr.lataverne.itemreward.items;

import fr.lataverne.itemreward.Helper;
import fr.lataverne.itemreward.managers.CustomItem;
import fr.lataverne.itemreward.managers.ECustomItem;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class IndianSpear extends CustomItem {

    public IndianSpear(int amount) {
        super(Material.GOLDEN_SWORD, amount);

        ItemMeta itemMeta = Objects.requireNonNull(this.getItemMeta(), "Item meta can't be null");

        itemMeta.setCustomModelData(1);

        if (Helper.configPathExists(this.getConfigPath() + ".displayName")) {
            itemMeta.setDisplayName(Helper.getStringInConfig(this.getConfigPath() + ".displayName", true));
        }

        if (Helper.configPathExists(this.getConfigPath() + ".lore")) {
            itemMeta.setLore(Helper.getStringListInConfig(this.getConfigPath() + ".lore", true));
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
    protected void onEntityDeath(@NotNull EntityDeathEvent e) {
        if (e.getEntity().getType() == EntityType.HORSE && e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();

            World world = Objects.requireNonNull(killer.getLocation().getWorld());
            world.dropItemNaturally(e.getEntity().getLocation(), new RawHorse(1));
        }
    }

    @Override
    protected void onInventoryClick(InventoryClickEvent e) {
        Helper.cantRepairableAndEnchanted(e);
        Helper.cantUseInCraft(e);
        Helper.cantCooked(e);
    }
}
