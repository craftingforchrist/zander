package com.shadowolfyt.zander.events;

import com.shadowolfyt.zander.ZanderMain;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPing implements Listener {
    ZanderMain plugin;

    public ServerListPing(ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        event.setMotd(ChatColor.translateAlternateColorCodes('&', ZanderMain.plugin.getConfig().getString("server.motdFirstLine") + "\n" + ZanderMain.plugin.getConfig().getString("server.motdSecondLine")));
    }
}
