package com.benrobson.zander.discord.moderation;

import com.benrobson.zander.ZanderMain;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SwearFilter extends ListenerAdapter {
    private ZanderMain plugin;
    public SwearFilter(ZanderMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        List<String> FilteredWords = plugin.configurationManager.getfilter().getStringList("filteredwords");
        String message = event.getMessage().getContentRaw();

        for (String word: FilteredWords){
            if (message.toLowerCase().matches("^" + word + "$")){
                event.getMessage().delete().queue();
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Foul Language");
                embed.setColor(Color.red);
                embed.setDescription(event.getAuthor().getName() + " you cannot use that word!");
                event.getChannel().sendMessage(embed.build()).queue();
                event.getMessage().delete().queueAfter(5, TimeUnit.SECONDS);
            }
        }
    }
}