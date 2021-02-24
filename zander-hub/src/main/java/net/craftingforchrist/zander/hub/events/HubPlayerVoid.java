package net.craftingforchrist.zander.hub.events;

import net.craftingforchrist.zander.hub.ConfigurationManager;
import net.craftingforchrist.zander.hub.ZanderHubMain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class HubPlayerVoid implements Listener {
    ZanderHubMain plugin;
    public HubPlayerVoid(ZanderHubMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void HubPlayerVoid(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();

        World defaultworld = Bukkit.getServer().getWorlds().get(0);
        World hubworld = Bukkit.getWorld(plugin.getConfig().getString("hub.world", defaultworld.getName()));
        if (hubworld == null) {
            Bukkit.getLogger().warning("No world by the name of " + hubworld.getName() + " was found! Assuming default world.");
            hubworld = Bukkit.getServer().getWorlds().get(0);
        }

        if (location.getBlockY() <= 0) {
            player.teleport(ConfigurationManager.getHubLocation());
        }
    }
}
