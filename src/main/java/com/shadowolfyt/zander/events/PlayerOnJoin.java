package com.shadowolfyt.zander.events;

import com.connorlinfoot.titleapi.TitleAPI;
import com.shadowolfyt.zander.ZanderMain;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PlayerOnJoin implements Listener {
    ZanderMain plugin;

    public PlayerOnJoin(ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().isBanned()) {
            for (Player p : Bukkit.getOnlinePlayers()){
                if (p.hasPermission("zander.punishnotify")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix")) + " " + event.getPlayer().getDisplayName() + " attempted to login but is banned.");
                }
            }
            return;
        }

        Player player = event.getPlayer();

        TitleAPI.sendTitle(player, 40, 50, 40, "Welcome " + ChatColor.AQUA + player.getDisplayName(), ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("server.titleSubText")));
        TitleAPI.sendTabTitle(player, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("server.tablineHeader")), ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("server.tablineFooter")));

        event.getPlayer().sendMessage(" ");
        List<String> motd = plugin.getConfig().getStringList("motd");
        for (String s : motd) {
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', s));
        }
        event.getPlayer().sendMessage(" ");

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
            // Join Chat Message.
            if (player.isOp()) {
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
            PreparedStatement findstatement = plugin.getConnection().prepareStatement("SELECT * FROM playerdata WHERE uuid=?");
            findstatement.setString(1, player.getUniqueId().toString());
            ResultSet results = findstatement.executeQuery();
            if (!results.next()) {
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("developmentprefix")) + " " + player.getDisplayName() + " is a new player, creating a player profile.");
                PreparedStatement insertstatement = plugin.getConnection().prepareStatement("INSERT INTO playerdata (uuid, username, status, ipaddress) VALUES (?, ?, ?, ?)");

                insertstatement.setString(1, player.getUniqueId().toString());
                insertstatement.setString(2, player.getDisplayName());
                insertstatement.setString(3, player.getAddress().getAddress().toString());
                insertstatement.setString(4, "Currently Online");

                insertstatement.executeUpdate();
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("developmentprefix")) + " Inserted information into " + player.getDisplayName() + "'s profile");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //
        // Database Query
        // Add +1 to joins, sets their ip address and sets player to currently online.
        //
        try {
            PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE playerdata SET joins = joins+1, lastseen = ?, status = ?, ipaddress = ? WHERE uuid=?");
            updatestatement.setString(1, "Currently Online");
            updatestatement.setString(2, "Currently Online");
            updatestatement.setString(3, player.getAddress().getAddress().toString());
            updatestatement.setString(4, player.getUniqueId().toString());
            updatestatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
