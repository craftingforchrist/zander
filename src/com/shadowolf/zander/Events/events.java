package com.shadowolf.zander.Events;

import com.shadowolf.zander.zander;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class events implements Listener {
    zander plugin = zander.getPlugin(zander.class);

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event){
        Player player = event.getPlayer();

        event.setJoinMessage("");
        if(player.isOp()) {
            event.setJoinMessage(ChatColor.GOLD + "Server Operator " + player.getName() + " has joined the server!");
        } else {
            event.setJoinMessage(ChatColor.YELLOW + player.getName() + " has joined the server!");
        }
    }

    public void onJoin(org.bukkit.event.player.PlayerQuitEvent event){
        Player player = event.getPlayer();

        event.setQuitMessage("");
        if(player.isOp()) {
            event.setQuitMessage(ChatColor.GOLD + "Server Operator " + player.getName() + " has left the server!");
        } else {
            event.setQuitMessage(ChatColor.YELLOW + player.getName() + " has left the server!");
        }
    }
}
