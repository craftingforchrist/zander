package com.benrobson.zander.events;

import com.benrobson.zander.ZanderMain;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class PlayerGamemodeChange implements Listener {
    ZanderMain plugin;
    public PlayerGamemodeChange(ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void playerGamemodeChange(PlayerGameModeChangeEvent event) {
        plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.developmentprefix")) + " " + event.getPlayer().getName() + " switched to gamemode " + event.getNewGameMode().name().toUpperCase());
    }
}
