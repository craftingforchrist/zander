package com.shadowolfyt.zander.discord.moderation;

import com.shadowolfyt.zander.ZanderMain;
import com.shadowolfyt.zander.discord.DiscordMain;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import java.awt.*;

public class TNTLightAdminLog implements Listener {
    private ZanderMain plugin;
    public TNTLightAdminLog(ZanderMain plugin) {
        this.plugin = plugin;
        DiscordMain.getInstance();
    }

    @EventHandler
    public void onCreatureSpawn (ExplosionPrimeEvent event) {
        Entity entity = event.getEntity();

        if (event.getEntityType() == EntityType.PRIMED_TNT) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Admin Event Log");
            embed.setDescription("TNT has been primed.");
            embed.setColor(Color.orange);
//            TODO: Log the player that primed the TNT.
//            embed.addField("Player", event.getEntity().().getDisplayName(), true);
            embed.addField("Location", "**X: **" + entity.getLocation().getBlockX() + "** Y: **" + entity.getLocation().getBlockY() + "** Z: **" + entity.getLocation().getBlockZ(), true);

            TextChannel textChannel = DiscordMain.getInstance().getJda().getTextChannelsByName(plugin.getConfig().getString("discord.adminlogchannel"), true).get(0);
            textChannel.sendMessage(embed.build()).queue();
        }
    }
}
