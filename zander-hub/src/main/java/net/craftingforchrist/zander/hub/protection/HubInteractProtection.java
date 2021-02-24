package net.craftingforchrist.zander.hub.protection;

import net.craftingforchrist.zander.hub.ZanderHubMain;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class HubInteractProtection implements Listener {
    ZanderHubMain plugin;
    public HubInteractProtection(ZanderHubMain plugin) {
        this.plugin = plugin;
    }

    // Block user from interacting or using any items
    @EventHandler(priority = EventPriority.HIGH)
    public void interactEntity(PlayerInteractEvent event) {
        Block clicked = event.getClickedBlock();

        // Used if the Hub needs to be edited by Senior Staff.
        if (event.getPlayer().hasPermission("zanderhub.build")) {
            event.setCancelled(false);
            return;
        }

        // Allows access for Doors and Buttons to be used.
        if (clicked.getType() == Material.DARK_OAK_DOOR || clicked.getType() == Material.DARK_OAK_BUTTON || clicked.getType() == Material.STONE_BUTTON) {
            event.setCancelled(false);
        } else {
            event.setCancelled(true);
            event.setUseInteractedBlock(Event.Result.DENY);
            event.setUseItemInHand(Event.Result.DENY);
        }

        // Allows access for Pressure Plates to be used.
        if (event.getAction().equals(Action.PHYSICAL)) {
            if (event.getClickedBlock().getType() == Material.HEAVY_WEIGHTED_PRESSURE_PLATE || event.getClickedBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE || event.getClickedBlock().getType() == Material.STONE_PRESSURE_PLATE || event.getClickedBlock().getType() == Material.BIRCH_PRESSURE_PLATE || event.getClickedBlock().getType() == Material.SPRUCE_PRESSURE_PLATE) {
                event.setCancelled(false);
            }
        }
    }
}
