package me.benrobson.zander.commands;

import me.benrobson.zander.DiscordMain;
import me.benrobson.zander.ZanderBungeeMain;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.awt.*;

public class report extends Command {
    public report() {
        super("report");
    }
    private static ZanderBungeeMain plugin;

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;

            if (strings.length == 0) {
                player.sendMessage(new TextComponent(ChatColor.RED + "Please specify a player to report and the reason for it."));
                return;
            } else if (strings.length == 1) {
                player.sendMessage(new TextComponent(ChatColor.RED + "Please specify a reason for this report."));
                return;
            } else {
                ProxiedPlayer target = ProxyServer.getInstance().getPlayer(strings[0]);

                player.sendMessage(new TextComponent(ChatColor.GREEN + "Your report has been sent to the Server Staff."));

                StringBuilder str = new StringBuilder();
                for (int i = 1; i < strings.length; i++) {
                    str.append(strings[i] + " ");
                }

                for (ProxiedPlayer pspp : ProxyServer.getInstance().getPlayers()) {
                    if (pspp.hasPermission("zander.reportnotify")) {
                        pspp.sendMessage(new TextComponent(player.getName() + " has reported " + target.getDisplayName() + " for " + ChatColor.GRAY + str.toString().trim()));
                    }
                }

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Incoming In-Game Report");
                embed.setColor(Color.ORANGE);
                embed.addField("Reported Player", target.getDisplayName(), true);
                embed.addField("Reported By", player.getName(), true);
                embed.addField("Reported Reason", str.toString().trim(), false);

                TextChannel textChannel = DiscordMain.getInstance().getJda().getTextChannelsByName(plugin.configurationManager.getConfig().getString("discord.reportchannel"), true).get(0);
                textChannel.sendMessage(embed.build()).queue();
            }
            return;
        }
    }
}
