package com.benrobson.zander.events;

import com.connorlinfoot.titleapi.TitleAPI;
import com.benrobson.zander.ZanderMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EnderDragonDeathListener implements Listener {
    ZanderMain plugin;

    public EnderDragonDeathListener(ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();

        if (event.getEntity().getName().equals("Ender Dragon")) {
            TitleAPI.sendTitle(player, 40, 50, 40, ChatColor.LIGHT_PURPLE + "Ender Dragon has been slain!", "Slain by " + event.getEntity().getKiller().getName());
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "The Ender Dragon has been slain by " + ChatColor.BOLD.toString() + event.getEntity().getKiller().getName() + "!");
            return;
        }
        return;
    }
}
