package com.shadowolfyt.zander.events;

import com.shadowolfyt.zander.ZanderMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Iterator;
import java.util.Random;

public class PigmanQuartzAgro implements Listener {
    ZanderMain plugin;

    public PigmanQuartzAgro(ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void PigmanQuartzAgro(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.NETHER_QUARTZ_ORE) {
            Player player = event.getPlayer();

            Random random = new Random();
            int randomNumber = random.nextInt(50);
            if (randomNumber == 1) {
                Iterator getEntities = player.getNearbyEntities(32, 32, 32).iterator();
                while (getEntities.hasNext()) {
                    Entity entity = (Entity)getEntities.next();
                    if (entity instanceof PigZombie) {
                        PigZombie pigZombie = (PigZombie)entity;
                        pigZombie.setAnger(120);
                        pigZombie.setTarget(event.getPlayer());
                        pigZombie.getWorld().playSound(pigZombie.getLocation(), Sound.ENTITY_ZOMBIE_PIGMAN_ANGRY, 1.0F, 1.0F);
                    }
                }
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("miscellaneous.pigmanangermessage")));
            }
        }
    }
}
