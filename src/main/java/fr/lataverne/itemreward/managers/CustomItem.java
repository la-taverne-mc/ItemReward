package fr.lataverne.itemreward.managers;

import fr.lataverne.itemreward.Helper;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class CustomItem extends ItemStack {

    private static final String NBTTag = "CustomItemType";

    protected CustomItem(Material material, int amount) {
        super(material, amount);

        this.init();
    }

    protected CustomItem(ItemStack itemStack) {
        super(itemStack);
    }

    public static CustomItem getCustomItem(ECustomItem customItemType) {
        return CustomItem.getCustomItem(customItemType, 1, 1);
    }

    @SuppressWarnings("DuplicatedCode")
    public static CustomItem getCustomItem(ECustomItem customItemType, int amount, int level) {
        return switch (customItemType) {
            case GoblinPickaxe -> new GoblinPickaxe(amount);
            case GiantBoots -> new GiantBoots(amount);
            case UnbreakableHoe -> new UnbreakableHoe(amount);
            case RawBear -> new RawBear(amount);
            case RawHorse -> new RawHorse(amount);
            case CookedBear -> new CookedBear(amount);
            case CookedHorse -> new CookedHorse(amount);
            case ULU -> new ULU(amount);
            case IndianSpear -> new IndianSpear(amount);
            case BaseballBat -> new BaseballBat(amount);
            case FlyPotion -> new FlyPotion(level, amount);
            case PhantomPotion -> new PhantomPotion(amount);
            case MiningPotion -> new MiningPotion(amount);
            case CreeperPotion -> new CreeperPotion(amount);
            case SwimmingPotion -> new SwimmingPotion(amount);
        };
    }

    @SuppressWarnings("DuplicatedCode")
    public static @Nullable CustomItem getCustomItem(ItemStack itemStack) {
        if (itemStack == null || itemStack.getItemMeta() == null || !Helper.hasNBT(itemStack, CustomItem.NBTTag)) {
            return null;
        }

        try {
            ECustomItem customItemType = ECustomItem.valueOf(Helper.getNBT(itemStack, CustomItem.NBTTag));

            return switch (customItemType) {
                case GoblinPickaxe -> new GoblinPickaxe(itemStack);
                case GiantBoots -> new GiantBoots(itemStack);
                case UnbreakableHoe -> new UnbreakableHoe(itemStack);
                case RawBear -> new RawBear(itemStack);
                case RawHorse -> new RawHorse(itemStack);
                case CookedBear -> new CookedBear(itemStack);
                case CookedHorse -> new CookedHorse(itemStack);
                case ULU -> new ULU(itemStack);
                case IndianSpear -> new IndianSpear(itemStack);
                case BaseballBat -> new BaseballBat(itemStack);
                case FlyPotion -> new FlyPotion(itemStack);
                case PhantomPotion -> new PhantomPotion(itemStack);
                case MiningPotion -> new MiningPotion(itemStack);
                case CreeperPotion -> new CreeperPotion(itemStack);
                case SwimmingPotion -> new SwimmingPotion(itemStack);
            };
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Bad NBT tag for the custom item type. Value: " + Helper.getNBT(itemStack, CustomItem.NBTTag));
        }
    }

    public static boolean useBlockBreakEvent(@NotNull CustomItem customItem) {
        return customItem.getCustomItemType() == ECustomItem.GoblinPickaxe;
    }

    public static boolean useBlockCookEvent(@NotNull CustomItem customItem) {
        return switch (customItem.getCustomItemType()) {
            case RawBear, RawHorse -> true;
            default -> false;
        };
    }

    public static boolean useEntityDeathEvent(@NotNull CustomItem customItem) {
        return switch (customItem.getCustomItemType()) {
            case ULU, IndianSpear -> true;
            default -> false;
        };
    }

    public static boolean useInventoryClickEvent(@NotNull CustomItem customItem) {
        return switch (customItem.getCustomItemType()) {
            case GiantBoots, GoblinPickaxe, IndianSpear, RawBear, RawHorse, ULU, UnbreakableHoe, BaseballBat -> true;
            default -> false;
        };
    }

    public static boolean usePlayerItemConsumeEvent(@NotNull CustomItem customItem) {
        return switch (customItem.getCustomItemType()) {
            case RawBear, RawHorse, CookedBear, CookedHorse, FlyPotion, PhantomPotion, MiningPotion, CreeperPotion, SwimmingPotion -> true;
            default -> false;
        };
    }

    public static boolean usePlayerMoveEvent(@NotNull CustomItem customItem) {
        return customItem.getCustomItemType() == ECustomItem.GiantBoots;
    }

    public abstract ECustomItem getCustomItemType();

    protected abstract String getConfigPath();

    protected void onBlockBreak(BlockBreakEvent e) {
        throw new NotImplementedException("Not implemented");
    }

    protected void onBlockCook(BlockCookEvent e) {
        throw new NotImplementedException("Not implemented");
    }

    protected void onEntityDeath(EntityDeathEvent e) {
        throw new NotImplementedException("Not implemented");
    }

    protected void onInventoryClick(InventoryClickEvent e) {
        throw new NotImplementedException("Not implemented");
    }

    protected void onPlayerItemConsume(PlayerItemConsumeEvent e) {
        throw new NotImplementedException("Not implemented");
    }

    protected void onPlayerMove(PlayerMoveEvent e) {
        throw new NotImplementedException("Not implemented");
    }

    private void init() {
        Helper.addNBT(this, CustomItem.NBTTag, this.getCustomItemType().toString());
    }
}
