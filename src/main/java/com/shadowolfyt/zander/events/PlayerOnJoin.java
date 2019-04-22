package com.shadowolfyt.zander.events;

import com.connorlinfoot.titleapi.TitleAPI;
import com.shadowolfyt.zander.ZanderMain;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerOnJoin implements Listener {
    ZanderMain plugin;

    public PlayerOnJoin (ZanderMain instance) {
        plugin = instance;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        TitleAPI.sendTitle(player,40,50,40,"Welcome " + ChatColor.AQUA + player.getDisplayName(),ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("server.titleSubText")));
        TitleAPI.sendTabTitle(player, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("server.tablineHeader")), ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("server.tablineFooter")));

        event.setJoinMessage("");
        // New user Joins for first time celebratory firework
        if (!player.hasPlayedBefore()) {
            Firework firework = (Firework) event.getPlayer().getWorld().spawn(event.getPlayer().getLocation(), Firework.class);
            FireworkMeta fireworkmeta = firework.getFireworkMeta();
            fireworkmeta.addEffect(FireworkEffect.builder()
                .flicker(false)
                .trail(true)
                .with(FireworkEffect.Type.CREEPER)
                .withColor(Color.GREEN)
                .withFade(Color.BLUE)
                .build());
            fireworkmeta.setPower(3);
            firework.setFireworkMeta(fireworkmeta);
            event.setJoinMessage(ChatColor.LIGHT_PURPLE + player.getDisplayName() + ChatColor.YELLOW + " has joined for the first time!");
        } else {
            if(player.isOp()) {
                event.setJoinMessage(ChatColor.RED.toString() + ChatColor.BOLD + "[!!!] " + ChatColor.GOLD + "Server Operator " + player.getName() + " has joined the server");
            } else {
                event.setJoinMessage(ChatColor.YELLOW + player.getName() + " has joined the server");
            }
        }

        //
        // Database Query
        // Create a new player profile in Database.
        //
        try {
            PreparedStatement findstatement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.getConfig().getString("database.playerdatatable") + " WHERE uuid=?");
            findstatement.setString(1, player.getUniqueId().toString());
            ResultSet results = findstatement.executeQuery();
            if (!results.next()) {
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("developmentprefix")) + " " + player.getDisplayName() + " is a new player, creating a player profile.");
                String ip = player.getPlayer().getAddress().toString().replaceAll("/", "");
                PreparedStatement insertstatement = plugin.getConnection().prepareStatement("INSERT INTO " + plugin.getConfig().getString("database.playerdatatable") + " (uuid, username, joins, leaves, deaths, lastseen, ipaddress) VALUES (?, ?, ?, ?, ?, ?, ?)");

                insertstatement.setString(1, player.getUniqueId().toString());
                insertstatement.setString(2, player.getDisplayName());
                insertstatement.setString(3, "1");
                insertstatement.setString(4, "0");
                insertstatement.setString(5, "0");
                insertstatement.setString(6, "Currently Online");
                insertstatement.setString(7, ip);

                insertstatement.executeUpdate();
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("developmentprefix")) + " Inserted information into " + player.getDisplayName() + "'s profile");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //
        // Database Query
        // Add +1 to joins and update IP address on login.
        //
        try {
            String ip = player.getPlayer().getAddress().toString().replaceAll("/", "");
            PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE " + plugin.getConfig().getString("database.playerdatatable") + " SET joins = joins+1, ipaddress = ? WHERE uuid=?");
            updatestatement.setString(1, ip);
            updatestatement.setString(2, player.getUniqueId().toString());
            updatestatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
