package fr.lataverne.itemreward.items.potions;

import fr.lataverne.itemreward.Helper;
import fr.lataverne.itemreward.effects.SwimmingEffect;
import fr.lataverne.itemreward.managers.CustomEffect;
import fr.lataverne.itemreward.managers.CustomPotion;
import fr.lataverne.itemreward.managers.ECustomItem;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SwimmingPotion extends CustomPotion {

    public SwimmingPotion(int amount) {
        super(1, amount);

        ItemMeta itemMeta = Objects.requireNonNull(this.getItemMeta());

        if (Helper.configPathExists(this.getConfigPath() + ".displayName")) {
            itemMeta.setDisplayName(Helper.getStringInConfig(this.getConfigPath() + ".displayName", true));
        }

        if (Helper.configPathExists(this.getConfigPath() + ".lore")) {
            itemMeta.setLore(Helper.getStringListInConfig(this.getConfigPath() + ".lore", true));
        }

        itemMeta.setCustomModelData(8);

        this.setItemMeta(itemMeta);
    }

    public SwimmingPotion(ItemStack itemStack) {
        super(itemStack);
    }

    @Override
    public ECustomItem getCustomItemType() {
        return ECustomItem.SwimmingPotion;
    }

    @Override
    protected String getConfigPath() {
        return "item.swimmingPotion";
    }

    @Override
    protected void onPlayerItemConsume(@NotNull PlayerItemConsumeEvent e) {
        Player player = e.getPlayer();

        if (CustomEffect.hasEffectInProgress(player.getUniqueId())) {
            Helper.sendMessage(player, Helper.getStringInConfig("message.user.otherCustomPotionAlreadyTaken", false));
            e.setCancelled(true);
            return;
        }

        SwimmingEffect swimmingEffect = new SwimmingEffect(player.getUniqueId());
        swimmingEffect.start();

        CustomPotion.customEmptyPotion(player, 8);
    }
}
