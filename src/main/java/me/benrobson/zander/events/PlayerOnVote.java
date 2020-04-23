package me.benrobson.zander.events;

import com.vexsoftware.votifier.bungee.events.VotifierEvent;
import me.benrobson.zander.ConfigurationManager;
import me.benrobson.zander.ZanderBungeeMain;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerOnVote implements Listener {
    private ZanderBungeeMain plugin = ZanderBungeeMain.getInstance();

    @EventHandler
    public void PlayerOnVote(VotifierEvent event) {
        String player = event.getVote().getUsername();
        String service = event.getVote().getServiceName();

        // May comment out if getting spammy
        ProxyServer.getInstance().broadcast(new TextComponent(player + " has voted from " + service + ". You can too at:" + ConfigurationManager.getConfig().getString("web.siteaddress") + "vote")); // Broadcast to all Servers

        try {
            PreparedStatement insertstatement = plugin.getConnection().prepareStatement("INSERT INTO votes (username, service) VALUES (?, ?)");

            insertstatement.setString(1, player);
            insertstatement.setString(2, service);

            insertstatement.executeUpdate();
            plugin.getLogger().info(player + " has voted on " + service + ".");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}