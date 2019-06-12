package com.shadowolfyt.zander.events;

import com.shadowolfyt.zander.ZanderMain;
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

        if (event.getEntityType() == EntityType.PLAYER) {
            try {
                PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE playerdata SET mobdeath_player = mobdeath_player+1 WHERE uuid=?");
                updatestatement.setString(1, event.getEntity().getKiller().getUniqueId().toString());
                updatestatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (event.getEntityType() == EntityType.CREEPER) {
            try {
                PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE playerdata SET mobdeath_creeper = mobdeath_creeper+1 WHERE uuid=?");
                updatestatement.setString(1, event.getEntity().getKiller().getUniqueId().toString());
                updatestatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (event.getEntityType() == EntityType.ENDER_DRAGON) {
            try {
                PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE playerdata SET mobdeath_enderdragon = mobdeath_enderdragon+1 WHERE uuid=?");
                updatestatement.setString(1, event.getEntity().getKiller().getUniqueId().toString());
                updatestatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (event.getEntityType() == EntityType.WITHER) {
            try {
                PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE playerdata SET mobdeath_wither = mobdeath_wither+1 WHERE uuid=?");
                updatestatement.setString(1, event.getEntity().getKiller().getUniqueId().toString());
                updatestatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (event.getEntityType() == EntityType.ELDER_GUARDIAN) {
            try {
                PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE playerdata SET mobdeath_elderguardian = mobdeath_elderguardian+1 WHERE uuid=?");
                updatestatement.setString(1, event.getEntity().getKiller().getUniqueId().toString());
                updatestatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (event.getEntityType() == EntityType.WITHER_SKELETON) {
            try {
                PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE playerdata SET mobdeath_witherskeleton = mobdeath_witherskeleton+1 WHERE uuid=?");
                updatestatement.setString(1, event.getEntity().getKiller().getUniqueId().toString());
                updatestatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (event.getEntityType() == EntityType.SHULKER) {
            try {
                PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE playerdata SET mobdeath_shulker = mobdeath_shulker+1 WHERE uuid=?");
                updatestatement.setString(1, event.getEntity().getKiller().getUniqueId().toString());
                updatestatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (event.getEntityType() == EntityType.EVOKER) {
            try {
                PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE playerdata SET mobdeath_evoker = mobdeath_evoker+1 WHERE uuid=?");
                updatestatement.setString(1, event.getEntity().getKiller().getUniqueId().toString());
                updatestatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (event.getEntityType() == EntityType.BAT) {
            try {
                PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE playerdata SET mobdeath_bat = mobdeath_bat+1 WHERE uuid=?");
                updatestatement.setString(1, event.getEntity().getKiller().getUniqueId().toString());
                updatestatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else return;
    }
}
