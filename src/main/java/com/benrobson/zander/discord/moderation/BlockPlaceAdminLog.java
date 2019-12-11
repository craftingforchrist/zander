package com.benrobson.zander.discord.moderation;

import com.benrobson.zander.discord.DiscordMain;
import com.benrobson.zander.ZanderMain;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BlockPlaceAdminLog implements Listener {
    private ZanderMain plugin;
    public BlockPlaceAdminLog(ZanderMain plugin) {
        this.plugin = plugin;
        DiscordMain.getInstance();
    }

    @EventHandler
    public void onBlockPlaceEvent (BlockPlaceEvent event) {
        Block block = event.getBlock();
        Material material = block.getType();
        Player player = event.getPlayer();
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);

        if (material.equals(Material.TNT)) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Admin Event Log");
            embed.setDescription("TNT has been placed.");
            embed.setColor(Color.red);
            embed.addField("Player", player.getDisplayName(), true);
            embed.addField("Location", "**X: **" + block.getX() + "** Y: **" + block.getY() + "** Z: **" + block.getZ(), true);
            embed.setFooter("Executed: " + strDate, null);

            TextChannel textChannel = DiscordMain.getInstance().getJda().getTextChannelsByName(plugin.getConfig().getString("discord.adminlogchannel"), true).get(0);
            textChannel.sendMessage(embed.build()).queue();
        }
    }
}
