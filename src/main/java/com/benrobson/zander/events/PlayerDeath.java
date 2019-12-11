package com.benrobson.zander.events;

import com.benrobson.zander.ZanderMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerDeath implements Listener {
    ZanderMain plugin;

    public PlayerDeath(ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();

        //
        // Database Query
        // Add +1 to deaths on death.
        //
        try {
            PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE playerdata SET deaths = deaths+1 WHERE uuid=?");
            updatestatement.setString(1, player.getUniqueId().toString());
            updatestatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
