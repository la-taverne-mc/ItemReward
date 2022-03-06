package fr.lataverne.itemreward.effects;

import fr.lataverne.itemreward.Helper;
import fr.lataverne.itemreward.managers.CustomEffect;
import fr.lataverne.itemreward.managers.ECustomEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CreeperEffect extends CustomEffect {

    public CreeperEffect(UUID playerUUID) {
        super(playerUUID, 1);
    }

    @Override
    protected String getConfigPath() {
        return "effect.creeperEffect";
    }

    @Override
    protected ECustomEffect getCustomEffectType() {
        return ECustomEffect.Creeper;
    }

    @Override
    protected void getRepeatingTask() {
        Player player = Bukkit.getPlayer(this.playerUUID);

        if (player == null) {
            return;
        }

        if (Bukkit.getOnlinePlayers().contains(player)) {
            if (this.remainingTime > 0) {
                player.getNearbyEntities(16.0, 16.0, 16.0)
                      .stream()
                      .filter(entity -> entity.getType() == EntityType.CREEPER)
                      .forEach(Entity::remove);

                String message = Helper.getStringInConfig("message.user.remainingTimeCustomPotion", false);
                message = Helper.replaceValueInString(message, Helper.convertTime(this.remainingTime), this.getCustomEffectType()
                                                                                                           .toString());
                Helper.sendBarMessage(player, message);
            } else {
                this.stop();
            }

            this.remainingTime--;
        }
    }
}
