package net.craftingforchrist.zander.proxy.events;

import net.craftingforchrist.zander.proxy.ZanderProxyMain;
import net.craftingforchrist.zander.proxy.discord.DiscordMain;
import net.dv8tion.jda.api.entities.TextChannel;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class PlayerChatEvent implements Listener {
    private ZanderProxyMain plugin = ZanderProxyMain.getInstance();

    @EventHandler
    public void PlayerOnJoin(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        String Username = player.getDisplayName();
        String Server = player.getServer().getInfo().getName();
        String ChatMessage = event.getMessage();

        String[] words = ChatMessage.split(" ");

        //
        // Discord Chat Logs
        //

        TextChannel textChannel = DiscordMain.jda.getTextChannelsByName(plugin.configurationManager.getConfig().getString("discord.chatlogchannel"), true).get(0);
        if (!ChatMessage.startsWith("/")) textChannel.sendMessage("**" + Server + "** | " + Username + " :: " + ChatMessage).queue();

        //
        // Swear Filter
        //
        List<String> bannedWords = plugin.configurationManager.getFilter().getStringList("bannedWords");

        boolean bannedshouldBreak = false;
        for (String word : words) {
            if (bannedshouldBreak) break;
            for (String badWord : bannedWords) {
                if (word.toLowerCase().equals(badWord.toLowerCase())) {
                    event.setCancelled(true);
                    player.sendMessage(new TextComponent(ChatColor.RED + "You are not allowed to swear on this Server."));
                    System.out.println(badWord + " is a banned word.");
                    bannedshouldBreak = true;
                    break;
                }
            }
        }

        //
        // Link Filter
        //
        List<String> filteredLinks = plugin.configurationManager.getFilter().getStringList("filteredLinks");

        boolean linksshouldBreak = false;
        for (String word : words) {
            if (linksshouldBreak) break;

            for (String filterLink : filteredLinks) {
                if (word.toLowerCase().contains(filterLink.toLowerCase())) {
                    if (word.toLowerCase().contains(plugin.configurationManager.getConfig().getString("web.siteaddress"))) return;
                    event.setCancelled(true);
                    player.sendMessage(new TextComponent(ChatColor.RED + "You are not allowed to advertise on this Server."));
                    System.out.println(filterLink + " is a part of or is an advertising link.");
                    linksshouldBreak = true;
                    break;
                }
            }
        }
    }
}