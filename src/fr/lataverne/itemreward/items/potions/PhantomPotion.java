package fr.lataverne.itemreward.items.potions;

import fr.lataverne.itemreward.effects.PhantomEffect;
import fr.lataverne.itemreward.managers.CustomEffect;
import fr.lataverne.itemreward.managers.CustomPotion;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.Objects;

import static fr.lataverne.itemreward.Helper.*;

public class PhantomPotion extends CustomPotion {
	public PhantomPotion(int amount) {
		super(amount, 1);

		PotionMeta itemMeta = (PotionMeta) Objects.requireNonNull(this.getItemMeta());

		if (configPathExists(this.getConfigPath() + ".displayName")) {
			itemMeta.setDisplayName(getStringInConfig(this.getConfigPath() + ".displayName", true));
		}

		if (configPathExists(this.getConfigPath() + ".lore")) {
			itemMeta.setLore(getStringListInConfig(this.getConfigPath() + ".lore", true));
		}

		itemMeta.setCustomModelData(5);

		this.setItemMeta(itemMeta);

	}

	public PhantomPotion(ItemStack itemStack) {
		super(itemStack);
	}

	@Override
	public ECustomItem getCustomItemType() {
		return ECustomItem.PhantomPotion;
	}

	@Override
	protected String getConfigPath() {
		return "item.phantomPotion";
	}

	@Override
	protected void onPlayerItemConsume(PlayerItemConsumeEvent e) {
		Player player = e.getPlayer();

		if (CustomEffect.hasEffectInProgress(player.getUniqueId())) {
			sendMessage(player, getStringInConfig("message.user.otherCustomPotionAlreadyTaken", false));
			e.setCancelled(true);
			return;
		}

		PhantomEffect phantomEffect = new PhantomEffect(player.getUniqueId());
		phantomEffect.start();

		customEmptyPotion(player, 5);
	}
}
