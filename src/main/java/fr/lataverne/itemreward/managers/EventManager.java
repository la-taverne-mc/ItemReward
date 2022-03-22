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
import org.jetbrains.annotations.NotNull;

public class EventManager implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    private static void onBlockBreak(@NotNull BlockBreakEvent e) {
        if (!e.isCancelled()) {
            ItemStack itemInMainHand = e.getPlayer().getInventory().getItemInMainHand();

            CustomItem customItem = CustomItem.getCustomItem(itemInMainHand);
            if (customItem != null && CustomItem.useBlockBreakEvent(customItem)) {
                customItem.onBlockBreak(e);
            }
        }
    }

    @EventHandler
    private static void onBlockCook(@NotNull BlockCookEvent e) {
        if (!e.isCancelled()) {
            ItemStack item = e.getSource();

            CustomItem customItem = CustomItem.getCustomItem(item);
            if (customItem != null && CustomItem.useBlockCookEvent(customItem)) {
                customItem.onBlockCook(e);
            }
        }
    }

    @EventHandler
    private static void onEntityDeath(@NotNull EntityDeathEvent e) {
        Player killer = e.getEntity().getKiller();

        if (killer != null) {
            ItemStack itemInMainHand = killer.getInventory().getItemInMainHand();

            CustomItem customItem = CustomItem.getCustomItem(itemInMainHand);
            if (customItem != null && CustomItem.useEntityDeathEvent(customItem)) {
                customItem.onEntityDeath(e);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private static void onInventoryClick(@NotNull InventoryClickEvent e) {
        if (!e.isCancelled()) {
            CustomItem customItem = CustomItem.getCustomItem(e.getCursor());
            if (customItem != null && CustomItem.useInventoryClickEvent(customItem)) {
                customItem.onInventoryClick(e);
            }

            CustomItem item = CustomItem.getCustomItem(e.getCurrentItem());
            if (item != null && CustomItem.useInventoryClickEvent(item)) {
                item.onInventoryClick(e);
            }
        }
    }

    @EventHandler
    private static void onPlayerItemConsume(@NotNull PlayerItemConsumeEvent e) {
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
    private static void onPlayerJoin(@NotNull PlayerJoinEvent e) {
        CustomEffect.loadCustomEffect(e.getPlayer().getUniqueId());
    }

    @EventHandler
    private static void onPlayerMove(@NotNull PlayerMoveEvent e) {
        if (!e.isCancelled()) {
            ItemStack itemOnFeet = e.getPlayer().getInventory().getItem(EquipmentSlot.FEET);

            CustomItem customItem = CustomItem.getCustomItem(itemOnFeet);
            if (customItem != null && CustomItem.usePlayerMoveEvent(customItem)) {
                customItem.onPlayerMove(e);
            }
        }
    }

    @EventHandler
    private static void onPlayerQuit(@NotNull PlayerQuitEvent e) {
        CustomEffect.saveCustomEffect(e.getPlayer().getUniqueId());
    }
}
