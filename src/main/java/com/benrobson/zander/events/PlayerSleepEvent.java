package com.benrobson.zander.events;

import com.benrobson.zander.ZanderMain;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerSleepEvent implements Listener {
    ZanderMain plugin;
    public PlayerSleepEvent(ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void PlayerSleepEvent(PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();

        //
        // Database Query
        // Set the players bed location.
        //
        try {
            PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE playerdata SET bedlocation = POINT(?, ?) WHERE uuid = ?");
            updatestatement.setFloat(1, event.getBed().getX());
            updatestatement.setFloat(2, event.getBed().getZ());
            updatestatement.setString(3, player.getUniqueId().toString());
            updatestatement.executeUpdate();
            event.setSpawnLocation(true);
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.developmentprefix")) + " " + player.getDisplayName() + "'s bed location has been logged and set.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
