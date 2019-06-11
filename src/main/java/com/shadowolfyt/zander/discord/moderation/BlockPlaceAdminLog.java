package com.shadowolfyt.zander.discord.moderation;

import com.shadowolfyt.zander.ZanderMain;
import com.shadowolfyt.zander.discord.DiscordMain;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.awt.*;

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

        if (material.equals(Material.TNT)) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Admin Event Log");
            embed.setDescription("TNT has been placed.");
            embed.setColor(Color.red);
            embed.addField("Player", player.getDisplayName(), true);
            embed.addField("Location", "**X: **" + block.getX() + "** Y: **" + block.getY() + "** Z: **" + block.getZ(), true);

            TextChannel textChannel = DiscordMain.getInstance().getJda().getTextChannelsByName(plugin.getConfig().getString("discord.adminlogchannel"), true).get(0);
            textChannel.sendMessage(embed.build()).queue();
        }
    }
}
