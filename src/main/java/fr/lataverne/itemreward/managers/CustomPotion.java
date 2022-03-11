package fr.lataverne.itemreward.managers;

import fr.lataverne.itemreward.Helper;
import fr.lataverne.itemreward.ItemReward;
import fr.lataverne.itemreward.api.objects.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class CustomPotion extends CustomItem {

    private static final String NBTTagCustomPotionType = "CustomPotionType";

    private static final String NBTTagLevel = "CustomPotionLevel";

    protected int level = 1;

    protected CustomPotion(ItemStack itemStack) {
        super(itemStack);

        if (Helper.hasNBT(itemStack, CustomPotion.NBTTagLevel)) {
            this.level = Integer.parseInt(Objects.requireNonNull(Helper.getNBT(itemStack, CustomPotion.NBTTagLevel)));
        }
    }

    protected CustomPotion(int level, int amount) {
        super(Material.POTION, amount);

        PotionMeta itemMeta = (PotionMeta) Objects.requireNonNull(this.getItemMeta());

        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.setColor(this.getPotionColor());

        this.setItemMeta(itemMeta);

        this.level = level;

        Helper.addNBT(this, CustomPotion.NBTTagCustomPotionType, this.getCustomItemType().toString());
        Helper.addNBT(this, CustomPotion.NBTTagLevel, Integer.toString(this.level));
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    public Color getPotionColor() {
        int red = Helper.getIntInConfig(this.getConfigPath() + ".color.red");
        int green = Helper.getIntInConfig(this.getConfigPath() + ".color.green");
        int blue = Helper.getIntInConfig(this.getConfigPath() + ".color.blue");

        return Color.fromRGB(red, green, blue);
    }

    @Override
    public String toString() {
        return "CustomPotion{" + "level=" + this.level + "}";
    }

    protected static void customEmptyPotion(Player player, int customModelData) {
        Bukkit.getScheduler()
              .runTaskLater(ItemReward.getInstance(), () -> CustomPotion.customEmptyPotionProc(player, customModelData), 1);
    }

    private static void customEmptyPotionProc(@NotNull Player player, int customModelData) {
        ItemStack glassBottle;
        if (player.getInventory().getItemInMainHand().getType() == Material.GLASS_BOTTLE) {
            glassBottle = player.getInventory().getItemInMainHand();
        } else if (player.getInventory().getItemInOffHand().getType() == Material.GLASS_BOTTLE) {
            glassBottle = player.getInventory().getItemInOffHand();
        } else {
            return;
        }

        ItemMeta itemMeta = glassBottle.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setCustomModelData(customModelData);
            glassBottle.setItemMeta(itemMeta);
        }
    }
}
