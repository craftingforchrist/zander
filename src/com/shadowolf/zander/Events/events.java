package com.shadowolf.zander.Events;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.shadowolf.zander.zander;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class events implements Listener {
    zander plugin = zander.getPlugin(zander.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        event.setJoinMessage("");
        player.sendMessage(ChatColor.GOLD + player.getName() + " has joined the server!");
        ActionBarAPI.sendActionBar(player, ChatColor.GOLD + player.getName() + " has joined the server!");
    }
}
