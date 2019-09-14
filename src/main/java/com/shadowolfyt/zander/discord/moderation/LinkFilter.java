package com.shadowolfyt.zander.discord.moderation;

import com.shadowolfyt.zander.ZanderMain;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;

public class LinkFilter extends ListenerAdapter {
    private ZanderMain plugin;
    public LinkFilter(ZanderMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        List<String> FilteredLinks = plugin.configurationManager.getlinksfilter().getStringList("filteredlinks");
        String message = event.getMessage().getContentRaw();

        for (String link: FilteredLinks){
            if (message.toLowerCase().matches("^" + link + "$")){
                if (event.getAuthor().isBot() && event.getMessage().getContentRaw().contains(link)){
                    event.getMessage().delete().queue();
                } else {
                    event.getMessage().delete().queue();
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("Link Detected");
                    embed.setColor(Color.red);
                    embed.setDescription(event.getAuthor().getName() + " you cannot advertise in this Discord!");
                    event.getChannel().sendMessage(embed.build()).queue();
                }
            }
        }
    }
}