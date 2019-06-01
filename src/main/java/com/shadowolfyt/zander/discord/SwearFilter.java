package com.shadowolfyt.zander.discord;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class SwearFilter extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] LIST_OF_BAD_WORDS = {"anal", "anus", "arse", "ass", "motherfucker", "balls", "bastard", "bitch", "blowjob", "blow job", "buttplug","cock","coon","cunt","dildo","fag","dyke","fuck","fucking","nigger","Goddamn","jizz","nigga","pussy","shit","whore"};
        String[] message = event.getMessage().getContentRaw().split(" ");

        for(int i = 0;i < message.length;i++){
            for(int b = 0; b < LIST_OF_BAD_WORDS.length;b++){
                if (message[i].equalsIgnoreCase(LIST_OF_BAD_WORDS[b])) {
                    event.getMessage().delete().queue();

                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("Foul Language");
                    embed.setColor(Color.red);
                    embed.setDescription(event.getMember().getUser().getName() + " watch the language!");
                    event.getChannel().sendMessage(embed.build()).queue();
                }
            }
        }
    }
}


