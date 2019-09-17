package com.shadowolfyt.zander.commands;

import com.connorlinfoot.titleapi.TitleAPI;
import com.shadowolfyt.zander.ZanderMain;
import com.shadowolfyt.zander.discord.DiscordMain;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class report implements CommandExecutor {
    ZanderMain plugin;

    public report(ZanderMain instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("punish.specifyplayerandreasoning")));
            return true;
        } else if (args.length == 1) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("punish.specifyreason")));
        } else {
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("punish.playernotonline")));
                return true;
            }

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.reportprefix")) + " "  + ChatColor.GREEN + "Your report have been sent to the Server Staff.");

            StringBuilder str = new StringBuilder();

            for (int i = 1; i < args.length; i++) {
                str.append(args[i] + " ");
            }

            for (Player p : Bukkit.getOnlinePlayers()){
                if (p.hasPermission("zander.reportnotify")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.reportprefix")) + " " + sender.getName() + " has reported " + target.getDisplayName() + " for " + str.toString().trim());
                }
            }

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Incoming In-Game Report");
            embed.setDescription(sender.getName() + " has reported " + target.getDisplayName() + " for " + str.toString().trim());
            embed.setColor(Color.ORANGE);

            TextChannel textChannel = DiscordMain.getInstance().getJda().getTextChannelsByName(plugin.getConfig().getString("discord.reportchannel"), true).get(0);
            textChannel.sendMessage(embed.build()).queue();
        }
        return true;
    }
}
