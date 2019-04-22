package com.shadowolfyt.zander.events;

import com.shadowolfyt.zander.ZanderMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerDeath implements Listener {
    ZanderMain plugin;

    public PlayerDeath (ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity killer = event.getEntity().getKiller();
        Entity deadEntity = event.getEntity();
        if (killer instanceof Player && deadEntity instanceof Player) {
            Player killer1 = event.getEntity().getKiller();
            Player deadplayer = (Player) event.getEntity();

            //
            // Database Query
            // Add +1 to deaths on death.
            //
            try {
                PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE " + plugin.getConfig().getString("database.playerdatatable") + " SET deaths = deaths+1 WHERE uuid=?");
                updatestatement.setString(1, killer1.getPlayer().getUniqueId().toString());
                updatestatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
