package com.shadowolfyt.zander.events;

import com.shadowolfyt.zander.ZanderMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class PlayerGamemodeChange implements Listener {
    @EventHandler
    public void playerGamemodeChange(PlayerGameModeChangeEvent event) {
        ZanderMain.plugin.getLogger().info(event.getPlayer().getName() + " switched to gamemode " + event.getNewGameMode().name().toLowerCase());
    }
}
