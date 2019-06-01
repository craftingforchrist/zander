package com.shadowolfyt.zander.events;

import com.shadowolfyt.zander.ZanderMain;
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
        if (event.getEntity() instanceof Monster) {
            event.getEntity().getKiller().sendMessage("You killed " + event.getEntity().getName());
            event.getEntity().getKiller().sendMessage("Your uuid is: " + event.getEntity().getKiller().getUniqueId().toString());

            if (event.getEntity().getName() == "Creeper") {
                try {
                    PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE playerdata SET mobdeath_creeper = mobdeath_creeper+1 WHERE uuid=?");
                    updatestatement.setString(1, event.getEntity().getKiller().getUniqueId().toString());
                    updatestatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
