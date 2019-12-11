package com.benrobson.zander.events;

import com.benrobson.zander.ZanderMain;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerOnQuit implements Listener {
    ZanderMain plugin;
    public PlayerOnQuit(ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage("");
        if (player.isOp()) {
            event.setQuitMessage(ChatColor.GOLD + "Server Operator " + player.getName() + " has left the server");
        } else {
            event.setQuitMessage(ChatColor.YELLOW + player.getName() + " has left the server");
        }

        //
        // Database Query
        // End the players session.
        //
        try {
            PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE gamesessions SET sessionend = NOW() where player_id = (select id from playerdata where uuid = ?) AND sessionend is null");
            updatestatement.setString(1, player.getUniqueId().toString());
            updatestatement.executeUpdate();
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.developmentprefix")) + " " + player.getDisplayName() + "'s session has ended, logging in the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
