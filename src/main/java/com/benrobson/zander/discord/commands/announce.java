package com.benrobson.zander.discord.commands;

import com.benrobson.zander.discord.DiscordMain;
import com.benrobson.zander.ZanderMain;
import lombok.Getter;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.awt.*;

public class announce extends ListenerAdapter {
    @Getter public static DiscordMain instance;
    @Getter public JDA jda;
    @Getter public ZanderMain plugin;

    public announce(ZanderMain plugin) {
        this.plugin = plugin;
        DiscordMain.getInstance();
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().startsWith(plugin.getConfig().getString("discord.prefix") + "announce")) {
            String[] args = event.getMessage().getContentRaw().split(" ");

            //
            // Server Broadcast
            //
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.broadcastprefix")) + " " + args);

            //
            // Discord Broadcast
            //
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Platform Broadcast");
            eb.setDescription(event.getMessage().toString());
            eb.setColor(Color.orange);

            TextChannel textChannel = DiscordMain.getInstance().getJda().getTextChannelsByName(plugin.getConfig().getString("discord.broadcastchannel"), true).get(0);
            textChannel.sendMessage(eb.build()).queue();
        }
    }
}