package com.shadowolf.zander.events;

import com.connorlinfoot.titleapi.TitleAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.meta.FireworkMeta;

public class EnderDragonDeath implements Listener {

    @EventHandler
    public void onEnderDragonDeath(EntityDeathEvent event){
        if(event.getEntity() instanceof EnderDragon) {
            Player player  = event.getEntity().getKiller();

            Firework firework = (Firework) player.getWorld().spawn(event.getEntity().getKiller().getLocation(), Firework.class);
            FireworkMeta fireworkmeta = firework.getFireworkMeta();
            fireworkmeta.addEffect(FireworkEffect.builder()
                .flicker(false)
                .trail(true)
                .with(FireworkEffect.Type.CREEPER)
                .withColor(Color.GREEN)
                .withFade(Color.BLUE)
                .build());
            fireworkmeta.setPower(3);
            firework.setFireworkMeta(fireworkmeta);

            // Broadcast Message to Server.
            TitleAPI.sendTitle(player,40,50,40,ChatColor.RED + "EnderDragon has been " + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "SLAIN",ChatColor.GOLD + player.getKiller().getDisplayName() + " has slain the EnderDragon!");
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + player.getDisplayName() + ChatColor.YELLOW + " has " + ChatColor.RED.toString() + ChatColor.BOLD + "SLAIN " + ChatColor.RESET + "the EnderDragon!");
        }

    }
}
