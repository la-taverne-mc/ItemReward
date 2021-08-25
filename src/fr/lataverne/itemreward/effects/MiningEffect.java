package fr.lataverne.itemreward.effects;

import fr.lataverne.itemreward.managers.CustomEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;
import java.util.UUID;

import static fr.lataverne.itemreward.Helper.*;

public class MiningEffect extends CustomEffect {
	public MiningEffect(UUID playerUUID) {
		super(playerUUID, 1);

		this.remainingTime = getIntInConfig(this.getConfigPath() + ".duration");
	}

	@Override
	public void stop() {
		Player player = Objects.requireNonNull(Bukkit.getPlayer(this.playerUUID));
		player.removePotionEffect(PotionEffectType.FAST_DIGGING);

		super.stop();
	}

	@Override
	protected String getConfigPath() {
		return "effect.miningEffect";
	}

	@Override
	protected ECustomEffect getCustomEffectType() {
		return ECustomEffect.Mining;
	}

	@Override
	protected Runnable getRepeatingTask() {
		return () -> {
			Player player = Bukkit.getPlayer(this.playerUUID);

			if (player == null) {
				return;
			}

			if (Bukkit.getOnlinePlayers().contains(player)) {
				if (this.remainingTime > 0) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 40, 1));

					String message = getStringInConfig("message.user.remainingTimeCustomPotion", false);
					message = replaceValueInString(message, convertTime(this.remainingTime), this.getCustomEffectType().toString());
					sendBarMessage(player, message);
				} else {
					this.stop();
				}

				this.remainingTime--;
			}
		};
	}
}
