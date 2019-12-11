package com.benrobson.zander;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AnnouncerManager extends BukkitRunnable {
    private ZanderMain plugin;
    public AnnouncerManager(ZanderMain plugin) {
        this.plugin = plugin;
    }

    int i = 0;

    public void run() {
        String prefix = ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.pluginprefix"));
        List<String> announcements = this.plugin.getConfig().getStringList("announcements");
        if (this.i == announcements.size()) {
            this.i = 0;
        } else {
            if (!((String) announcements.get(this.i)).equals("") && announcements.get(this.i) != null) {
                Bukkit.getServer().broadcastMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', (String) announcements.get(this.i)));
            } else {
                Bukkit.getServer().broadcastMessage(prefix + " " + ChatColor.RED + "Each announcement is not empty and is surrounded by quotes.");
            }

            ++this.i;
        }
    }
}
