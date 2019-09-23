package com.shadowolfyt.zander.commands;

import com.shadowolfyt.zander.ZanderMain;
import com.shadowolfyt.zander.discord.DiscordMain;
import lombok.Getter;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.awt.*;

public class announce implements CommandExecutor {
    ZanderMain plugin;
    @Getter public static DiscordMain instance;
    @Getter public JDA jda;

    public announce(ZanderMain instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("zander.announce")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("error.specifyannouncecontent")));
                return true;
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < args.length; i++){
                    sb.append(args[i]).append(" ");
                }

                String str = sb.toString().trim();

                //
                // Server Broadcast
                //
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.broadcastprefix")) + " " + str);

                //
                // Discord Broadcast
                //
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Platform Broadcast");
                eb.setDescription(str);
                eb.setColor(Color.orange);

                TextChannel textChannel = DiscordMain.getInstance().getJda().getTextChannelsByName(plugin.getConfig().getString("discord.broadcastchannel"), true).get(0);
                textChannel.sendMessage(eb.build()).queue();
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
        return true;
    }
}
