package me.benrobson.zander.discord.events;

import me.benrobson.zander.ZanderBungeeMain;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;

public class ChatEvent extends ListenerAdapter {
    private ZanderBungeeMain plugin = ZanderBungeeMain.getInstance();

    @Override
    public void onGuildMessageReceived (GuildMessageReceivedEvent event) {
        String GuildMessage = event.getMessage().getContentRaw();
        String[] words = GuildMessage.split(" ");


        System.out.println(event.getMessage().getContentRaw());


        //
        // Swear Filter
        //
        List<String> bannedWords = plugin.configurationManager.getConfig().getStringList("bannedWords");

        for (String word : words) {
            for (String BadWord : bannedWords) {
                boolean shouldBreak = false;

                if (shouldBreak == false) {
                    if (event.getMessage().getAuthor().isBot()) return;
                    if (word.toLowerCase().contains(BadWord.toLowerCase())) {
                        event.getMessage().delete().queue();

                        EmbedBuilder embed = new EmbedBuilder();
                        embed.setTitle("No Swearing!", null);
                        embed.setColor(Color.red);
                        embed.setDescription(event.getAuthor().getName() + " swearing was detected in your message, swearing is not allowed on this Server.\nContinuing to do so will result in punishment.");
                        event.getChannel().sendMessage(embed.build()).complete();
                        shouldBreak = true;
                    }
                } else return;
            }
        }

        //
        // Link Filter
        //
        List<String> filteredLinks = plugin.configurationManager.getConfig().getStringList("filteredLinks");

        for (String word : words) {
            for (String FilteredLinks : filteredLinks) {
                if (event.getMessage().getAuthor().isBot()) return;
                if (word.toLowerCase().contains(FilteredLinks.toLowerCase())) {
                    event.getMessage().delete().queue();

                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("No Advertising!", null);
                    embed.setColor(Color.red);
                    embed.setDescription(event.getAuthor().getName() + " advertising was detected in your message, advertising is not allowed on this Server.\nContinuing to do so will result in punishment.");
                    event.getChannel().sendMessage(embed.build()).complete();
                    return;
                }
            }
        }
    }
}
