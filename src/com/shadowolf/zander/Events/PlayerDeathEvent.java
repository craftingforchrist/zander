package com.shadowolf.zander.Events;

import com.shadowolf.zander.zander;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class PlayerDeathEvent implements Listener {

    zander plugin;

    public PlayerDeathEvent(zander instance) {
        plugin = instance;
    }

    @EventHandler
    public void EntityDeathEvent(EntityDeathEvent event) {

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
