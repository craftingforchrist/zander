package com.shadowolfyt.zander.events;

import com.shadowolfyt.zander.ZanderMain;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class BanListener implements Listener {
    ZanderMain plugin;

    public BanListener(ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (event.getResult() == PlayerLoginEvent.Result.KICK_BANNED) {
            event.setKickMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.title")) + "\n\n" + event.getKickMessage() + "\n\n" + "Contact " + this.plugin.getConfig().getString("web.email") + " to appeal.");

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("zander.punishnotify")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.punishmentprefix")) + " " + ChatColor.RED + event.getPlayer().getDisplayName() + " attempted to login but is banned.");
                }
            }
        }
    }
}