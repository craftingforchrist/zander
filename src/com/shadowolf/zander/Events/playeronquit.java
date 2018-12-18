package com.shadowolf.zander.Events;

import com.shadowolf.zander.zander;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class playeronquit implements Listener {

    zander plugin;

    public playeronquit(zander instance){
        plugin = instance;

    }

    @EventHandler
    public void onQuit(org.bukkit.event.player.PlayerQuitEvent event){
        Player player = event.getPlayer();

        event.setQuitMessage("");
        if(player.isOp()) {
            event.setQuitMessage(ChatColor.GOLD + "Server Operator " + player.getName() + " has left the server");
        } else {
            event.setQuitMessage(ChatColor.YELLOW + player.getName() + " has left the server");
        }

        String playername = player.getName();
        if (!player.hasPlayedBefore()){
            plugin.getConfig().set(playername + ".leaves", 0);
        }

        int leaves = plugin.getConfig().getInt(playername + ".leaves");
        plugin.getConfig().set(playername + ".leaves", leaves + 1);
        plugin.saveConfig();
    }
}
