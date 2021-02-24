package net.craftingforchrist.zander.events;

import net.craftingforchrist.zander.Variables;
import net.craftingforchrist.zander.ZanderProxyMain;
import net.dv8tion.jda.api.entities.TextChannel;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static net.craftingforchrist.zander.discord.DiscordMain.jda;

public class PlayerOnServerConnect implements Listener {
    private ZanderProxyMain plugin = ZanderProxyMain.getInstance();

    @EventHandler
    public void PlayerServerSwitchEvent(ServerSwitchEvent event) {
        ProxiedPlayer player = event.getPlayer();
        String Server = event.getFrom().getName();
        String Username = player.getDisplayName();
        String UserUUID = player.getUniqueId().toString();

        //
        // Database Query
        // Start the players game session.
        //
        try {
            PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE gamesessions SET server = ? WHERE (select id from playerdata where uuid = ?) AND sessionend IS NULL");
            updatestatement.setString(1, Server);
            updatestatement.setString(2, UserUUID);
            updatestatement.executeUpdate();
            plugin.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', Variables.developmentprefix + " " + Username + " switched to " + Server + ".")));

            //
            // Discord Chat Logs
            //
            TextChannel textChannel = jda.getTextChannelsByName(plugin.configurationManager.getConfig().getString("discord.chatlogchannel"), true).get(0);
            textChannel.sendMessage("** :twisted_rightwards_arrows: ** | " + Username + " switched to " + Server + ".").queue();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}