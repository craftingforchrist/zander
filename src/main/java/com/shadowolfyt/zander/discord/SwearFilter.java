package com.shadowolfyt.zander.discord;

import com.shadowolfyt.zander.ZanderMain;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class SwearFilter extends ListenerAdapter {
    private ZanderMain plugin;
    public SwearFilter(ZanderMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] FilteredWords = {plugin.configurationManager.getfilter().getString("filteredwords")};
        String[] message = event.getMessage().getContentRaw().split(" ");

        for(int i = 0;i < message.length;i++){
            for(int b = 0; b < FilteredWords.length;b++){
                if (message[i].equalsIgnoreCase(FilteredWords[b])) {
                    event.getMessage().delete().queue();

                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("Foul Language");
                    embed.setColor(Color.red);
                    embed.setDescription(event.getMember().getUser().getName() + " you cannot use that word!");
                    event.getChannel().sendMessage(embed.build()).queue();
                }
            }
        }
    }
}


