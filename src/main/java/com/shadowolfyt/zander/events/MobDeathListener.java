package com.shadowolfyt.zander.events;

import com.shadowolfyt.zander.ZanderMain;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MobDeathListener implements Listener {
    ZanderMain plugin;

    public MobDeathListener(ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;

        //
        // Database Query
        // Log a mob death.
        //
        try {
            PreparedStatement insertstatement = plugin.getConnection().prepareStatement("INSERT INTO mobdeath (killer_id, mobtype_id, location) VALUES ((select id from playerdata where uuid = ?), (select id from mobtypes where mobtype = ?), POINT(?, ?))");
            insertstatement.setString(1, event.getEntity().getKiller().getUniqueId().toString());
            insertstatement.setString(2, event.getEntityType().name());
            insertstatement.setFloat(3, event.getEntity().getLocation().getBlockX());
            insertstatement.setFloat(4, event.getEntity().getLocation().getBlockZ());
            insertstatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
