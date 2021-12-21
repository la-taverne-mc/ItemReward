package fr.lataverne.itemreward.items.potions;

import fr.lataverne.itemreward.effects.CreeperEffect;
import fr.lataverne.itemreward.managers.CustomEffect;
import fr.lataverne.itemreward.managers.CustomPotion;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.Objects;

import static fr.lataverne.itemreward.Helper.*;

public class CreeperPotion extends CustomPotion {
	public CreeperPotion(int amount) {
		super(amount, 1);

		PotionMeta itemMeta = (PotionMeta) Objects.requireNonNull(this.getItemMeta());

		if (configPathExists(this.getConfigPath() + ".displayName")) {
			itemMeta.setDisplayName(getStringInConfig(this.getConfigPath() + ".displayName", true));
		}

		if (configPathExists(this.getConfigPath() + ".lore")) {
			itemMeta.setLore(getStringListInConfig(this.getConfigPath() + ".lore", true));
		}

		itemMeta.setCustomModelData(6);

		this.setItemMeta(itemMeta);
	}

	public CreeperPotion(ItemStack itemStack) {
		super(itemStack);
	}

	@Override
	public ECustomItem getCustomItemType() {
		return ECustomItem.CreeperPotion;
	}

	@Override
	protected String getConfigPath() {
		return "item.creeperPotion";
	}

	@Override
	protected void onPlayerItemConsume(PlayerItemConsumeEvent e) {
		Player player = e.getPlayer();

		if (CustomEffect.hasEffectInProgress(player.getUniqueId())) {
			sendMessage(player, getStringInConfig("message.user.otherCustomPotionAlreadyTaken", false));
			e.setCancelled(true);
			return;
		}

		CreeperEffect creeperEffect = new CreeperEffect(player.getUniqueId());
		creeperEffect.start();

		customEmptyPotion(player, 6);
	}
}
