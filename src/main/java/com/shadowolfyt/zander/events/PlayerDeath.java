package com.shadowolfyt.zander.events;

import com.shadowolfyt.zander.ZanderMain;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class PlayerDeath implements Listener {
    ZanderMain plugin;

    public PlayerDeath (ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void EntityDeathEvent (EntityDeathEvent event) {

        LivingEntity entity = event.getEntity();

        if (entity.getType() == EntityType.PLAYER){
            int deaths = plugin.getConfig().getInt("players" + "." + entity.getName() + ".deaths");
            plugin.getConfig().set("players" + "." + entity.getName() + ".deaths", deaths + 1);
            plugin.saveConfig();
        } else {
            return;
        }
    }
}
