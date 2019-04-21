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
            event.setKickMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + "[zander] " + ChatColor.RESET.toString() + ChatColor.RED + "You are not whitelisted on this server!\n\nIf this is incorrect please contact shadowolf#9212 on Discord.");
            Bukkit.broadcastMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + "[zander] " + ChatColor.RED + player.getDisplayName() + " attempted to login but is not whitelisted.");
        }
    }
}
