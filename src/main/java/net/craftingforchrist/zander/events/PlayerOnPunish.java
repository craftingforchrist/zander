//package net.craftingforchrist.zander.events;
//
//import me.leoko.advancedban.bungee.event.PunishmentEvent;
//import me.leoko.advancedban.bungee.event.RevokePunishmentEvent;
//import net.craftingforchrist.zander.ZanderBungeeMain;
//import net.dv8tion.jda.api.EmbedBuilder;
//import net.dv8tion.jda.api.entities.TextChannel;
//import net.md_5.bungee.api.plugin.Listener;
//import net.md_5.bungee.event.EventHandler;
//
//import java.awt.*;
//
//import static net.craftingforchrist.zander.discord.DiscordMain.jda;
//
//public class PlayerOnPunish implements Listener {
//    private ZanderBungeeMain plugin = ZanderBungeeMain.getInstance();
//
//    @EventHandler
//    public void PlayerOnPunish(PunishmentEvent event) {
//        String punisheduser = event.getPunishment().getName();
//        String punisher = event.getPunishment().getOperator();
//        String reason = event.getPunishment().getReason();
//        String type = event.getPunishment().getType().getName();
//        int id = event.getPunishment().getId();
//
//        EmbedBuilder embed = new EmbedBuilder();
//        embed.setTitle("Punishment Logged [#" + id + "]");
//        embed.setColor(Color.ORANGE);
//        embed.addField("Punished User", punisheduser, true);
//        embed.addField("Punished By", punisher, true);
//        embed.addField("Type", type, true);
//        embed.addField("Reason", reason, true);
//        embed.setFooter("zander // AdvancedBan Hook");
//
//        TextChannel textChannel = jda.getTextChannelsByName(plugin.configurationManager.getConfig().getString("discord.punishmentlogchannel"), true).get(0);
//        textChannel.sendMessage(embed.build()).queue();
//    }
//
//    @EventHandler
//    public void PlayerOnPunishRevoke(RevokePunishmentEvent event) {
//        String punisheduser = event.getPunishment().getName();
//        String punisher = event.getPunishment().getOperator();
//        String reason = event.getPunishment().getReason();
//        int id = event.getPunishment().getId();
//
//        EmbedBuilder embed = new EmbedBuilder();
//        embed.setTitle("Punishment Revoked [" + id + "]");
//        embed.setColor(Color.YELLOW);
//        embed.addField("Revoked User", punisheduser, true);
//        embed.addField("Revoked By", punisher, true);
//        embed.addField("Reason", reason, true);
//        embed.setFooter("zander // AdvancedBan Hook");
//
//        TextChannel textChannel = jda.getTextChannelsByName(plugin.configurationManager.getConfig().getString("discord.punishmentlog"), true).get(0);
//        textChannel.sendMessage(embed.build()).queue();
//    }
//}
