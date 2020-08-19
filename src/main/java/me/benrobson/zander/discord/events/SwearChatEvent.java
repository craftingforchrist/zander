package me.benrobson.zander.discord.events;

import me.benrobson.zander.SwearFilterManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SwearChatEvent extends ListenerAdapter {
    private static SwearFilterManager SwearFilterManager = new SwearFilterManager();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
//        String[] message = event.getMessage().getContentRaw().split("\\s+");

//        boolean filteredMessage = SwearFilterManager.filterText(event.getMessage().getContentRaw());

//        System.out.println(SwearFilterManager.filterText(event.getMessage().getContentRaw()));

        if (SwearFilterManager.foundSwear == true) {
            event.getChannel().sendMessage("This is a swear!");
        }
    }
}
