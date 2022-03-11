package fr.lataverne.itemreward.items;

import fr.lataverne.itemreward.Helper;
import fr.lataverne.itemreward.api.objects.CustomItem;
import fr.lataverne.itemreward.managers.ECustomItem;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;
import java.util.Objects;

public class GoblinPickaxe extends CustomItem {

    public GoblinPickaxe(int amount) {
        super(Material.GOLDEN_PICKAXE, amount);

        ItemMeta itemMeta = Objects.requireNonNull(this.getItemMeta(), "Item meta can't be null");

        if (Helper.configPathExists(this.getConfigPath() + ".displayName")) {
            itemMeta.setDisplayName(Helper.getStringInConfig(this.getConfigPath() + ".displayName", true));
        }

        if (Helper.configPathExists(this.getConfigPath() + ".lore")) {
            itemMeta.setLore(Helper.getStringListInConfig(this.getConfigPath() + ".lore", true));
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
    public void onBlockBreak(@NotNull BlockBreakEvent e) {
        if (e.getBlock().getLocation().getWorld() == null) {
            return;
        }

        if (e.getBlock().getType() == Material.STONE) {
            e.setDropItems(false);

            int mineralRand = new SecureRandom().nextInt(65);
            if (mineralRand <= 14) {
                e.getBlock()
                 .getLocation()
                 .getWorld()
                 .dropItemNaturally(e.getBlock()
                                     .getLocation(), new ItemStack(Material.IRON_NUGGET, Helper.getRandomNumberInRange(7, 11)));
            } else if (mineralRand <= 29) {
                e.getBlock()
                 .getLocation()
                 .getWorld()
                 .dropItemNaturally(e.getBlock()
                                     .getLocation(), new ItemStack(Material.LAPIS_LAZULI, Helper.getRandomNumberInRange(6, 10)));
            } else if (mineralRand <= 39) {
                e.getBlock()
                 .getLocation()
                 .getWorld()
                 .dropItemNaturally(e.getBlock()
                                     .getLocation(), new ItemStack(Material.REDSTONE, Helper.getRandomNumberInRange(6, 10)));
            } else if (mineralRand <= 49) {
                e.getBlock()
                 .getLocation()
                 .getWorld()
                 .dropItemNaturally(e.getBlock()
                                     .getLocation(), new ItemStack(Material.GOLD_NUGGET, Helper.getRandomNumberInRange(6, 10)));
            } else if (mineralRand <= 57) {
                e.getBlock()
                 .getLocation()
                 .getWorld()
                 .dropItemNaturally(e.getBlock()
                                     .getLocation(), new ItemStack(Material.DIAMOND, Helper.getRandomNumberInRange(1, 2)));
            } else {
                e.getBlock()
                 .getLocation()
                 .getWorld()
                 .dropItemNaturally(e.getBlock()
                                     .getLocation(), new ItemStack(Material.EMERALD, Helper.getRandomNumberInRange(1, 2)));
            }
        } else if (e.getBlock().getType() == Material.NETHERRACK) {
            e.setDropItems(false);
            e.getBlock()
             .getLocation()
             .getWorld()
             .dropItemNaturally(e.getBlock()
                                 .getLocation(), new ItemStack(Material.QUARTZ, Helper.getRandomNumberInRange(1, 5)));
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        Helper.cantRepairableAndEnchanted(e);
        Helper.cantUseInCraft(e);
        Helper.cantCooked(e);
    }

    @Override
    protected String getConfigPath() {
        return "item.goblinPickaxe";
    }
}
