package com.shadowolf.zander.Events;

import com.shadowolf.zander.zander;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class WhitelistListener implements Listener {

    zander plugin;

    public WhitelistListener(zander instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        if (event.getResult() == PlayerLoginEvent.Result.KICK_WHITELIST) {
            event.setKickMessage(ChatColor.AQUA.toString() + ChatColor.AQUA + "[zander] " + ChatColor.RESET.toString() + ChatColor.RED + "You are not whitelisted on this server!\n\nIf this is incorrect please contact shadowolf#9212 on Discord.");
            Bukkit.broadcastMessage(ChatColor.RED + player.getDisplayName() + " attempted to login but is not whitelisted.");
        }
    }
}
