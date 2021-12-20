package fr.lataverne.itemreward.items.potions;

import fr.lataverne.itemreward.effects.MiningEffect;
import fr.lataverne.itemreward.managers.CustomEffect;
import fr.lataverne.itemreward.managers.CustomPotion;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.Objects;

import static fr.lataverne.itemreward.Helper.*;

public class MiningPotion extends CustomPotion {
	public MiningPotion() {
		super(1);

		PotionMeta itemMeta = (PotionMeta) Objects.requireNonNull(this.getItemMeta());

		if (configPathExists(this.getConfigPath() + ".displayName")) {
			itemMeta.setDisplayName(getStringInConfig(this.getConfigPath() + ".displayName", true));
		}

		if (configPathExists(this.getConfigPath() + ".lore")) {
			itemMeta.setLore(getStringListInConfig(this.getConfigPath() + ".lore", true));
		}

		itemMeta.setCustomModelData(7);

		this.setItemMeta(itemMeta);
	}

	public MiningPotion(ItemStack itemStack) {
		super(itemStack);
	}

	@Override
	public ECustomItem getCustomItemType() {
		return ECustomItem.MiningPotion;
	}

	@Override
	protected String getConfigPath() {
		return "item.miningPotion";
	}

	@Override
	protected void onPlayerItemConsume(PlayerItemConsumeEvent e) throws NotImplementedException {
		Player player = e.getPlayer();

		if (CustomEffect.hasEffectInProgress(player.getUniqueId())) {
			sendMessage(player, getStringInConfig("message.user.otherCustomPotionAlreadyTaken", false));
			e.setCancelled(true);
			return;
		}

		MiningEffect miningEffect = new MiningEffect(player.getUniqueId());
		miningEffect.start();

		customEmptyPotion(player, 7);
	}
}
