package com.benrobson.zander.discord.moderation;

import com.benrobson.zander.discord.DiscordMain;
import com.benrobson.zander.ZanderMain;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TNTLightAdminLog implements Listener {
    private ZanderMain plugin;
    public TNTLightAdminLog(ZanderMain plugin) {
        this.plugin = plugin;
        DiscordMain.getInstance();
    }

    @EventHandler
    public void onCreatureSpawn (ExplosionPrimeEvent event) {
        Entity entity = event.getEntity();
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);

        if (event.getEntityType() == EntityType.PRIMED_TNT) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Admin Event Log");
            embed.setDescription("TNT has been primed.");
            embed.setColor(Color.orange);
            embed.setFooter("Executed: " + strDate, null);
//            TODO: Log the player that primed the TNT.
//            embed.addField("Player", event.getEntity().().getDisplayName(), true);
            embed.addField("Location", "**X: **" + entity.getLocation().getBlockX() + "** Y: **" + entity.getLocation().getBlockY() + "** Z: **" + entity.getLocation().getBlockZ(), true);

            TextChannel textChannel = DiscordMain.getInstance().getJda().getTextChannelsByName(plugin.getConfig().getString("discord.adminlogchannel"), true).get(0);
            textChannel.sendMessage(embed.build()).queue();
        }
    }
}
