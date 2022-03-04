package fr.lataverne.itemreward.items.potions;

import fr.lataverne.itemreward.effects.FlyEffect;
import fr.lataverne.itemreward.managers.CustomEffect;
import fr.lataverne.itemreward.managers.CustomPotion;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

import static fr.lataverne.itemreward.Helper.*;

public class FlyPotion extends CustomPotion {
    public FlyPotion(ItemStack itemStack) {
        super(itemStack);
    }

    public FlyPotion(int amount, int level) {
        super(amount, level);

        ItemMeta itemMeta = Objects.requireNonNull(this.getItemMeta());

        if (configPathExists(this.getConfigPath() + ".displayName")) {
            itemMeta.setDisplayName(getStringInConfig(this.getConfigPath() + ".displayName", true));
        }

        if (configPathExists(this.getConfigPath() + ".lore")) {
            itemMeta.setLore(getStringListInConfig(this.getConfigPath() + ".lore", true));
        }

        itemMeta.setCustomModelData(getCustomModelDataValue(this.level));

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
            default:
            case 1:
                output += "1";
                break;
            case 2:
                output += "2";
                break;
            case 3:
                output += "3";
                break;
            case 4:
                output += "4";
                break;
        }

        return output;
    }

    @Override
    protected void onPlayerItemConsume(PlayerItemConsumeEvent e) {
        Player player = e.getPlayer();

        if (CustomEffect.hasEffectInProgress(player.getUniqueId())) {
            sendMessage(player, getStringInConfig("message.user.otherCustomPotionAlreadyTaken", false));
            e.setCancelled(true);
            return;
        }

        FlyEffect flyEffect = new FlyEffect(player.getUniqueId(), this.level);
        flyEffect.start();

        customEmptyPotion(player, getCustomModelDataValue(this.level));
    }

    private static int getCustomModelDataValue(int level) {
        switch (level) {
            default:
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
        }
    }
}
