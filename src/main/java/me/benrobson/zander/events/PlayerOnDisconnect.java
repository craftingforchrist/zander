package me.benrobson.zander.events;

import me.benrobson.zander.ZanderBungeeMain;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerOnDisconnect implements Listener {
    private ZanderBungeeMain plugin = ZanderBungeeMain.getInstance();

    @EventHandler
    public void PlayerOnDisconnect(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        plugin.getLogger().info(player.getDisplayName() + " has left the server");
    }
}
