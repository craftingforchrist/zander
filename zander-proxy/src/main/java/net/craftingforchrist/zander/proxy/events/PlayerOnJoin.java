package net.craftingforchrist.zander.proxy.events;

import net.craftingforchrist.zander.proxy.Variables;
import net.craftingforchrist.zander.proxy.ZanderProxyMain;
import net.dv8tion.jda.api.entities.TextChannel;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static net.craftingforchrist.zander.proxy.discord.DiscordMain.jda;

public class PlayerOnJoin implements Listener {
    private ZanderProxyMain plugin = ZanderProxyMain.getInstance();

    @EventHandler
    public void PlayerOnJoin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        String UserUUID = player.getUniqueId().toString();
        String Username = player.getDisplayName();
        String UserAddress = player.getAddress().getAddress().getHostAddress();
        String Server = player.getServer().getInfo().getName();

        plugin.getLogger().info(player.getDisplayName() + " has joined the server");

        //
        // Database Query
        // Create a new player profile in Database.
        //
        try {
            PreparedStatement findstatement = plugin.getConnection().prepareStatement("SELECT * FROM playerdata WHERE uuid=?");
            findstatement.setString(1, player.getUniqueId().toString());
            ResultSet results = findstatement.executeQuery();
            if (!results.next()) {
                plugin.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', Variables.developmentprefix + " " + Username + " is a new player, creating a player profile.")));
                PreparedStatement insertstatement = plugin.getConnection().prepareStatement("INSERT INTO playerdata (uuid, username) VALUES (?, ?)");

                insertstatement.setString(1, UserUUID);
                insertstatement.setString(2, Username);

                insertstatement.executeUpdate();
                plugin.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', Variables.developmentprefix + " Inserted information into " + Username + "'s profile")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //
        // Database Query
        // Check if player has updated their username
        //
        try {
            PreparedStatement findstatement = plugin.getConnection().prepareStatement("SELECT username FROM playerdata WHERE uuid=?;");
            findstatement.setString(1, UserUUID);
            ResultSet results = findstatement.executeQuery();
            if (results.next()) {
                PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE playerdata SET username=? WHERE uuid=?;");
                updatestatement.setString(1, Username);
                updatestatement.setString(2, UserUUID);
                updatestatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //
        // Database Query
        // Start the players game session.
        //
        try {
            PreparedStatement insertstatement = plugin.getConnection().prepareStatement("INSERT INTO gamesessions (playerid, ipaddress, server) VALUES ((select id from playerdata where uuid=?), ?, ?)");
            insertstatement.setString(1, UserUUID);
            insertstatement.setString(2, UserAddress);
            insertstatement.setString(3, Server);
            insertstatement.executeUpdate();
            plugin.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', Variables.developmentprefix + " " + Username + " has logged in, beginning their session.")));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //
        // Discord Chat Logs
        //
        TextChannel textChannel = jda.getTextChannelsByName(plugin.configurationManager.getConfig().getString("discord.chatlogchannel"), true).get(0);
        textChannel.sendMessage("** :ballot_box_with_check: ** | " + Username + " has joined the Network.").queue();
    }
}