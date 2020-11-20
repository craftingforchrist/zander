package me.benrobson.zander.events;

import me.benrobson.zander.ZanderBungeeMain;
import me.leoko.advancedban.bungee.event.PunishmentEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import static me.benrobson.zander.discord.DiscordMain.jda;

public class PlayerOnPunish extends ListenerAdapter implements Listener {
    private ZanderBungeeMain plugin = ZanderBungeeMain.getInstance();

    @EventHandler
    public void PlayerOnPunish(PunishmentEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Punishment");
        embed.setDescription("Type: " + event.getPunishment().getType() + "\nPunished: " + event.getPunishment().getName() + "\nPunished by: " + event.getPunishment().getOperator() + "\nReason: " + event.getPunishment().getReason());
//        embed.setColor(Color.ORANGE);
//        embed.setDescription(event.getPunishment().getName() + " has been punished by " + event.getPunishment().getOperator() + " for " + event.getPunishment().getReason());

        TextChannel textChannel =  jda.getTextChannelsByName(plugin.configurationManager.getConfig().getString("discord.punishementlogchannel"), true).get(0);
        textChannel.sendMessage(embed.build()).queue();
    }

//    @EventHandler
//    public void PlayerOnPunishRevoke(RevokePunishmentEvent event) {
//        EmbedBuilder embed = new EmbedBuilder();
//        embed.setTitle("Punishment");
//        embed.setColor(Color.ORANGE);
//        embed.setDescription(event.getPunishment().getName() + " has been punished by " + event.getPunishment().getOperator() + " for " + event.getPunishment().getReason());
//
//        TextChannel textChannel =  jda.getTextChannelsByName(plugin.configurationManager.getConfig().getString("discord.punishementlogchannel"), true).get(0);
//        textChannel.sendMessage(embed.build()).queue();
//    }
}