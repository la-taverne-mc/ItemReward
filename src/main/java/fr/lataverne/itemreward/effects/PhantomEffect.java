package fr.lataverne.itemreward.effects;

import fr.lataverne.itemreward.managers.CustomEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.UUID;

import static fr.lataverne.itemreward.Helper.*;

public class PhantomEffect extends CustomEffect {
    public PhantomEffect(UUID playerUUID) {
        super(playerUUID, 1);

        this.remainingTime = getIntInConfig(this.getConfigPath() + ".duration");
    }

    @Override
    protected String getConfigPath() {
        return "effect.phantomEffect";
    }

    @Override
    protected ECustomEffect getCustomEffectType() {
        return ECustomEffect.Phantom;
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

                    player.getNearbyEntities(16.0, 16.0, 16.0).stream().filter(entity -> entity.getType().equals(EntityType.PHANTOM)).forEach(Entity::remove);

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
