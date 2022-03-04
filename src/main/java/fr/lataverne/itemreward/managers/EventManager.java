package fr.lataverne.itemreward.managers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCookEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class EventManager implements Listener {
    @EventHandler (priority = EventPriority.LOW)
    private void onBlockBreak(BlockBreakEvent e) {
        if (!e.isCancelled()) {
            ItemStack itemInMainHand = e.getPlayer().getInventory().getItemInMainHand();

            CustomItem customItem = CustomItem.getCustomItem(itemInMainHand);
            if (customItem != null && CustomItem.useBlockBreakEvent(customItem)) {
                customItem.onBlockBreak(e);
            }
        }
    }

    @EventHandler
    private void onBlockCook(BlockCookEvent e) {
        if (!e.isCancelled()) {
            ItemStack item = e.getSource();

            CustomItem customItem = CustomItem.getCustomItem(item);
            if (customItem != null && CustomItem.useBlockCookEvent(customItem)) {
                customItem.onBlockCook(e);
            }
        }
    }

    @EventHandler
    private void onEntityDeath(EntityDeathEvent e) {
        Player killer = e.getEntity().getKiller();

        if (killer != null) {
            ItemStack itemInMainHand = killer.getInventory().getItemInMainHand();

            CustomItem customItem = CustomItem.getCustomItem(itemInMainHand);
            if (customItem != null && CustomItem.useEntityDeathEvent(customItem)) {
                customItem.onEntityDeath(e);
            }
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    private void onInventoryClick(InventoryClickEvent e) {
        if (!e.isCancelled()) {
            CustomItem customItem = CustomItem.getCustomItem(e.getCursor());
            if (customItem != null && CustomItem.useInventoryClickEvent(customItem)) {
                customItem.onInventoryClick(e);
            }

            customItem = CustomItem.getCustomItem(e.getCurrentItem());
            if (customItem != null && CustomItem.useInventoryClickEvent(customItem)) {
                customItem.onInventoryClick(e);
            }

        }
    }

    @EventHandler
    private void onPlayerItemConsume(PlayerItemConsumeEvent e) {
        if (!e.isCancelled()) {
            CustomItem customItem = CustomItem.getCustomItem(e.getItem());
            if (customItem != null) {
                if (CustomItem.usePlayerItemConsumeEvent(customItem)) {
                    customItem.onPlayerItemConsume(e);
                }
            } else if (e.getItem().getType() == Material.MILK_BUCKET) {
                Player player = e.getPlayer();
                if (CustomEffect.hasEffectInProgress(player.getUniqueId())) {
                    CustomEffect customEffect = CustomEffect.getCustomEffect(player.getUniqueId());
                    customEffect.stop();
                }
            }
        }
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent e) {
        CustomEffect.loadCustomEffect(e.getPlayer().getUniqueId());
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent e) {
        if (!e.isCancelled()) {
            ItemStack itemOnFeet = e.getPlayer().getInventory().getItem(EquipmentSlot.FEET);

            CustomItem customItem = CustomItem.getCustomItem(itemOnFeet);
            if (customItem != null && CustomItem.usePlayerMoveEvent(customItem)) {
                customItem.onPlayerMove(e);
            }
        }
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent e) {
        CustomEffect.saveCustomEffect(e.getPlayer().getUniqueId());
    }
}
