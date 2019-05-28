package com.shadowolfyt.zander.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FallIntoEndVoidListener implements Listener {
    @EventHandler
    public void fall(PlayerMoveEvent e) {
        if ((e.getTo().getY() < -10) && // Is falling into the void?
                (e.getPlayer().getWorld().getBiome(e.getTo().getBlockX(), e.getFrom().getBlockZ()).toString().toUpperCase()
                        .contains("END".toUpperCase()) || e.getPlayer().getWorld().getName().toUpperCase().contains("END"))) { // is in an end biome? is in end?
            Location l = e.getPlayer().getBedSpawnLocation() == null ? e.getPlayer().getBedSpawnLocation() : Bukkit.getWorlds().get(0).getSpawnLocation();

            e.getPlayer().teleport(l);
            e.getPlayer().setFallDistance(50);
        }
    }
}
