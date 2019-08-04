package com.shadowolfyt.zander.discord.commands;

import com.shadowolfyt.zander.ZanderMain;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class rules extends ListenerAdapter {
    private ZanderMain plugin;
    public rules(ZanderMain plugin) {
        this.plugin = plugin;
    }


    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase("!rules")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Server Rules");
            embed.setDescription("You can see the server rules here: " + plugin.getConfig().getString("web.siteaddress") + "rules");
            event.getChannel().sendMessage(embed.build()).queue();
        }
    }
}
