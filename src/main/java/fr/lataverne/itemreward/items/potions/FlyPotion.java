package fr.lataverne.itemreward.items.potions;

import fr.lataverne.itemreward.Helper;
import fr.lataverne.itemreward.effects.FlyEffect;
import fr.lataverne.itemreward.managers.CustomEffect;
import fr.lataverne.itemreward.managers.CustomPotion;
import fr.lataverne.itemreward.managers.ECustomItem;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FlyPotion extends CustomPotion {

    public FlyPotion(ItemStack itemStack) {
        super(itemStack);
    }

    public FlyPotion(int level, int amount) {
        super(level, amount);

        ItemMeta itemMeta = Objects.requireNonNull(this.getItemMeta());

        if (Helper.configPathExists(this.getConfigPath() + ".displayName")) {
            itemMeta.setDisplayName(Helper.getStringInConfig(this.getConfigPath() + ".displayName", true));
        }

        if (Helper.configPathExists(this.getConfigPath() + ".lore")) {
            itemMeta.setLore(Helper.getStringListInConfig(this.getConfigPath() + ".lore", true));
        }

        itemMeta.setCustomModelData(FlyPotion.getCustomModelDataValue(this.level));

        this.setItemMeta(itemMeta);
    }

    @Override
    public ECustomItem getCustomItemType() {
        return ECustomItem.FlyPotion;
    }

    @Override
    protected String getConfigPath() {
        String output = "item.flyPotion.level";

        switch (this.level) {
            case 2 -> output += "2";
            case 3 -> output += "3";
            case 4 -> output += "4";
            case 1, default -> output += "1";
        }

        return output;
    }

    @Override
    protected void onPlayerItemConsume(@NotNull PlayerItemConsumeEvent e) {
        Player player = e.getPlayer();

        if (CustomEffect.hasEffectInProgress(player.getUniqueId())) {
            Helper.sendMessage(player, Helper.getStringInConfig("message.user.otherCustomPotionAlreadyTaken", false));
            e.setCancelled(true);
            return;
        }

        FlyEffect flyEffect = new FlyEffect(player.getUniqueId(), this.level);
        flyEffect.start();

        CustomPotion.customEmptyPotion(player, FlyPotion.getCustomModelDataValue(this.level));
    }

    private static int getCustomModelDataValue(int level) {
        return level < 1 || level > 4
               ? 1
               : level;
    }
}
