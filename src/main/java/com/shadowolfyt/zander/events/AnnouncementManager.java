package com.shadowolfyt.zander.events;

import com.shadowolfyt.zander.ZanderMain;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Random;

import static org.bukkit.Bukkit.getServer;

public class AnnouncementManager implements Listener {
    ZanderMain plugin;

    public AnnouncementManager(ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void AnnouncementManager(PlayerJoinEvent event){
        if (plugin.getConfig().getString("announcementsenable") == "true") {
            BukkitScheduler scheduler = getServer().getScheduler();
            scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
                public void run() {
                    Random random = new Random();
                    int number = random.nextInt(4) + 1;

                    if (number == 1) {
                        Bukkit.broadcastMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "[zander] " + ChatColor.RESET + plugin.getConfig().getString("announcements.announce1"));
                    } else if (number == 2) {
                        Bukkit.broadcastMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "[zander] " + ChatColor.RESET + plugin.getConfig().getString("announcements.announce2"));
                    } else if (number == 3) {
                        Bukkit.broadcastMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "[zander] " + ChatColor.RESET + plugin.getConfig().getString("announcements.announce3"));
                    }
                }
            }, 300, 25000);
        } else {
            getServer().getConsoleSender().sendMessage("Announcements have been disabled in the config.yml, not using the announcement function.");
        }
    }
}
