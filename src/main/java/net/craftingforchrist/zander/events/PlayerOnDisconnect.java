package net.craftingforchrist.zander.events;

import net.craftingforchrist.zander.Variables;
import net.craftingforchrist.zander.ZanderBungeeMain;
import net.dv8tion.jda.api.entities.TextChannel;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static net.craftingforchrist.zander.discord.DiscordMain.jda;

public class PlayerOnDisconnect implements Listener {
    private ZanderBungeeMain plugin = ZanderBungeeMain.getInstance();

    @EventHandler
    public void PlayerOnDisconnect(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (player.isConnected()) return;

        //
        // Database Query
        // End the players session.
        //
        try {
            PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE gamesessions SET sessionend = NOW() where playerid = (select id from playerdata where uuid = ?) AND sessionend is null");
            updatestatement.setString(1, player.getUniqueId().toString());
            updatestatement.executeUpdate();
            plugin.getLogger().info(ChatColor.translateAlternateColorCodes('&', Variables.developmentprefix + " " + player.getDisplayName() + " has left the server. Session has ended, logging in the database."));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //
        // Discord Chat Logs
        //
        TextChannel textChannel = jda.getTextChannelsByName(plugin.configurationManager.getConfig().getString("discord.chatlogchannel"), true).get(0);
        textChannel.sendMessage("** :negative_squared_cross_mark: ** | " + player.getDisplayName() + " has left the Network.").queue();
    }
}
