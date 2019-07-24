package com.shadowolfyt.zander.events;

import com.shadowolfyt.zander.ZanderMain;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class WhitelistListener implements Listener {
    ZanderMain plugin;

    public WhitelistListener(ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        if (event.getResult() == PlayerLoginEvent.Result.KICK_WHITELIST) {
            event.setKickMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.pluginprefix")) + ChatColor.RESET.toString() + ChatColor.RED + " " + ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("server.whitelisttagline")) + "\n\n" + ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("server.whitelistcontact")));
            Bukkit.broadcastMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.pluginprefix")) + " " + ChatColor.RED + player.getDisplayName() + " attempted to login but is not whitelisted.");
        }
    }
}
