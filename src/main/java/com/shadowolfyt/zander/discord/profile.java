package com.shadowolfyt.zander.discord;

import com.shadowolfyt.zander.ZanderMain;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class profile extends ListenerAdapter {
    private ZanderMain plugin;
    public profile(ZanderMain plugin) {
        this.plugin = plugin;
    }


    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase("!profile")) {
            if (args.length == 0) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Error");
                embed.setColor(Color.red);
                embed.setDescription("Incorrect Usage: !profile [PlayerName]");
                event.getChannel().sendMessage(embed.build()).queue();
            }

            //
            // Database Query
            // Get data from players profile.
            //
            try {
                PreparedStatement findstatement = plugin.getConnection().prepareStatement("SELECT * FROM playerdata WHERE username = ?");
                findstatement.setString(1, args[1]);
                ResultSet results = findstatement.executeQuery();
                if (!results.next()) {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("Error");
                    embed.setColor(Color.red);
                    embed.setDescription("The player " + args[1] + " does not exist on this server!");
                    event.getChannel().sendMessage(embed.build()).queue();
                } else {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle(results.getString("username") + "'s Profile", plugin.getConfig().getString("web.siteaddress") + results.getString("username"));
                    embed.setThumbnail("https://crafatar.com/avatars/" + results.getString("uuid") + "?overlay");
                    embed.addField("Joins", results.getString("joins"), true);
                    embed.addField("Deaths", results.getString("deaths"), true);
                    embed.addField("Status", results.getString("status"), true);
                    embed.addField("Last Seen", results.getString("lastseen"), true);
//                    embed.addField("Total Playtime", results.getString("totalplaytime"), true);
                    event.getChannel().sendMessage(embed.build()).queue();
                }
            // If there is a SQL exception.
            } catch (SQLException e) {
                e.printStackTrace();
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Error");
                embed.setColor(Color.red);
                embed.setDescription("There was an error, whoops...");
                event.getChannel().sendMessage(embed.build()).queue();
            }
        }
    }
}
