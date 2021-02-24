package net.craftingforchrist.zander.proxy.events;

import net.craftingforchrist.zander.proxy.Variables;
import net.craftingforchrist.zander.proxy.ZanderProxyMain;
import net.craftingforchrist.zander.proxy.discord.DiscordMain;
import net.dv8tion.jda.api.entities.TextChannel;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerOnDisconnect implements Listener {
    private ZanderProxyMain plugin = ZanderProxyMain.getInstance();

    @EventHandler
    public void PlayerOnDisconnect(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        String UserUUID = player.getUniqueId().toString();
        String Username = player.getDisplayName();

        if (player.isConnected()) return;

        //
        // Database Query
        // End the players session.
        //
        try {
            PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE gamesessions SET sessionend = NOW() where playerid = (select id from playerdata where uuid = ?) AND sessionend is null");
            updatestatement.setString(1, UserUUID);
            updatestatement.executeUpdate();
            plugin.getLogger().info(ChatColor.translateAlternateColorCodes('&', Variables.developmentprefix + " " + Username + " has left the server. Session has ended, logging in the database."));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //
        // Discord Chat Logs
        //
        TextChannel textChannel = DiscordMain.jda.getTextChannelsByName(plugin.configurationManager.getConfig().getString("discord.chatlogchannel"), true).get(0);
        textChannel.sendMessage("** :negative_squared_cross_mark: ** | " + Username + " has left the Network.").queue();
    }
}
