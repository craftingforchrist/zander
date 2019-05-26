package com.shadowolfyt.zander.discord;

import com.shadowolfyt.zander.ZanderMain;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class profile extends ListenerAdapter {
    private ZanderMain plugin;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(plugin.prefix + "profile")) {
            //
            // Database Query
            // Create a new player profile in Database.
            //
            try {
                PreparedStatement findstatement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.getConfig().getString("database.playerdatatable") + " WHERE username=?");
                findstatement.setString(1, args[0]);
                ResultSet results = findstatement.executeQuery();
                if (!results.next()) {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle(results.getString("username") + "Player's Profile");
                    embed.addField("Joins", String.valueOf(results.getInt("joins")), true);
                    embed.addField("Deaths", String.valueOf(results.getInt("deaths")), true);
                    embed.addField("Status", results.getString("status"), false);
                    embed.addField("Last Seen", results.getString("lastseen"), true);
                    event.getChannel().sendMessage(embed.build()).queue();
                }
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }
}
