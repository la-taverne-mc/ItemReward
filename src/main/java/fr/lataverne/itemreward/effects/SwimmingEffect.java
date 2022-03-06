package fr.lataverne.itemreward.effects;

import fr.lataverne.itemreward.Helper;
import fr.lataverne.itemreward.managers.CustomEffect;
import fr.lataverne.itemreward.managers.ECustomEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;
import java.util.UUID;

public class SwimmingEffect extends CustomEffect {

    public SwimmingEffect(UUID playerUUID) {
        super(playerUUID, 1);
    }

    @Override
    public void stop() {
        Player player = Objects.requireNonNull(Bukkit.getPlayer(this.playerUUID));
        player.removePotionEffect(PotionEffectType.CONDUIT_POWER);

        super.stop();
    }

    @Override
    protected String getConfigPath() {
        return "effect.swimmingEffect";
    }

    @Override
    protected ECustomEffect getCustomEffectType() {
        return ECustomEffect.Swimming;
    }

    @Override
    protected void getRepeatingTask() {
        Player player = Bukkit.getPlayer(this.playerUUID);

        if (player == null) {
            return;
        }

        if (Bukkit.getOnlinePlayers().contains(player)) {
            if (this.remainingTime > 0) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 40, 1));

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
