package com.shadowolfyt.zander.events;

import com.shadowolfyt.zander.ZanderMain;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class BanListener implements Listener {
    ZanderMain plugin;

    public BanListener(ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        if (event.getResult() == PlayerLoginEvent.Result.KICK_BANNED) {
            event.setKickMessage(ChatColor.RED + plugin.configurationManager.getlang().getString("punish.bantagline") + "\n\n" + ChatColor.YELLOW + event.getKickMessage() + "\n\n" + ChatColor.YELLOW + plugin.configurationManager.getlang().getString("punish.appealcontact"));
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.pluginprefix")) + " " + ChatColor.RED + player.getDisplayName() + " attempted to login but is banned.");
        }
    }
}
