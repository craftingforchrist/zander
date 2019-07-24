package com.shadowolfyt.zander.events;

import com.shadowolfyt.zander.ZanderMain;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EndCrystalPercentDrop implements Listener {
    ZanderMain plugin;

    public EndCrystalPercentDrop(ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void EndCrystalPercentDrop(EntityDeathEvent event) {
        event.getDrops().clear();
        event.setDroppedExp(0);

        LivingEntity entity = event.getEntity();

        if (entity instanceof SkeletonHorse) {
            if (Math.random() < 0.2) {
                entity.getLocation().getWorld().dropItem(entity.getLocation(), new ItemStack(Material.END_CRYSTAL, 1));
                return;
            } else {
                entity.getLocation().getWorld().dropItem(entity.getLocation(), new ItemStack(Material.BONE, 1));
                return;
            }
        }
    }
}
