package com.shadowolfyt.zander.discord.commands;

import com.shadowolfyt.zander.ZanderMain;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class discord extends ListenerAdapter {
    private ZanderMain plugin;
    public discord(ZanderMain plugin) {
        this.plugin = plugin;
    }


    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase("!discord")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Discord Link", event.getChannel().createInvite().complete().getURL());
            embed.setDescription("Here is a link: " + event.getChannel().createInvite().complete().getURL());
            event.getChannel().sendMessage(embed.build()).queue();
        }
    }
}
