package com.shadowolfyt.zander.events;

import com.shadowolfyt.zander.ZanderMain;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class BanListener implements Listener {
    ZanderMain plugin;

    public BanListener(ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
        if (event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.KICK_BANNED) {
            event.disallow(event.getLoginResult(), "prefix" + "\n" + "You have been banned" + "\n\n&r"+ event.getKickMessage() + "\n\n" + "Contact someone to be unbanned.");
            Bukkit.broadcastMessage(color(plugin.configurationManager.getlang().getString("main.punishmentprefix")) + " " + ChatColor.RED + event.getName() + " attempted to login but is banned.");
        }
    }

    private String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}