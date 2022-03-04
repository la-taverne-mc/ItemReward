package fr.lataverne.itemreward.managers;

import fr.lataverne.itemreward.items.*;
import fr.lataverne.itemreward.items.potions.*;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCookEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

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
        return getCustomItem(customItemType, 1, 1);
    }

    public static CustomItem getCustomItem(ECustomItem customItemType, int amount, int level) {
        switch (customItemType) {
            case GoblinPickaxe:
                return new GoblinPickaxe(amount);
            case GiantBoots:
                return new GiantBoots(amount);
            case UnbreakableHoe:
                return new UnbreakableHoe(amount);
            case RawBear:
                return new RawBear(amount);
            case RawHorse:
                return new RawHorse(amount);
            case CookedBear:
                return new CookedBear(amount);
            case CookedHorse:
                return new CookedHorse(amount);
            case ULU:
                return new ULU(amount);
            case IndianSpear:
                return new IndianSpear(amount);
            case BaseballBat:
                return new BaseballBat(amount);
            case FlyPotion:
                return new FlyPotion(amount, level);
            case PhantomPotion:
                return new PhantomPotion(amount);
            case MiningPotion:
                return new MiningPotion(amount);
            case CreeperPotion:
                return new CreeperPotion(amount);
            case SwimmingPotion:
                return new SwimmingPotion(amount);
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
