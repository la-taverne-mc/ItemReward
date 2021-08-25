package fr.lataverne.itemreward.effects;

import fr.lataverne.itemreward.managers.CustomEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

import static fr.lataverne.itemreward.Helper.*;

public class FlyEffect extends CustomEffect {

	public FlyEffect(UUID playerUUID, int level) {
		super(playerUUID, level);

		this.remainingTime = getIntInConfig(this.getConfigPath() + ".duration");
	}

	@Override
	public void stop() {
		Player player = Objects.requireNonNull(Bukkit.getPlayer(this.playerUUID));
		player.setAllowFlight(false);

		super.stop();
	}

	@Override
	protected String getConfigPath() {
		return "effect.flyEffect.level" + this.level;
	}

	@Override
	protected ECustomEffect getCustomEffectType() {
		return ECustomEffect.Fly;
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
					player.setAllowFlight(true);

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
