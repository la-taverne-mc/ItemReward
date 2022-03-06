package fr.lataverne.itemreward.effects;

import fr.lataverne.itemreward.Helper;
import fr.lataverne.itemreward.managers.CustomEffect;
import fr.lataverne.itemreward.managers.ECustomEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class FlyEffect extends CustomEffect {

    public FlyEffect(UUID playerUUID, int level) {
        super(playerUUID, level);

        this.remainingTime = Helper.getIntInConfig(this.getConfigPath() + ".duration");
    }

    @Override
    public void stop() {
        Player player = Objects.requireNonNull(Bukkit.getPlayer(this.playerUUID));
        player.setAllowFlight(false);

        super.stop();
    }

    @Override
    protected ECustomEffect getCustomEffectType() {
        return ECustomEffect.Fly;
    }

    @Override
    protected void getRepeatingTask() {
        Player player = Bukkit.getPlayer(this.playerUUID);

        if (player == null) {
            return;
        }

        if (Bukkit.getOnlinePlayers().contains(player)) {
            if (this.remainingTime > 0) {
                player.setAllowFlight(true);

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

    @Contract(pure = true)
    private @NotNull String getConfigPath() {
        return "effect.flyEffect.level" + this.level;
    }
}
