package com.shadowolf.zander.events;

import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EnderDragonDeath implements Listener {


    @EventHandler
    public void onEnderDragonDeath(EntityDeathEvent event){
        if(event.getEntity() instanceof EnderDragon) {

        }

    }
}
