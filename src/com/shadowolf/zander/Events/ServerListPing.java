package com.shadowolf.zander.Events;

import com.shadowolf.zander.zander;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPing implements Listener {

    zander plugin;

    public ServerListPing(zander instance) {
        plugin = instance;
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        event.setMotd(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("motd.line1") + "\n" + plugin.getConfig().getString("motd.line2")));
    }
}
