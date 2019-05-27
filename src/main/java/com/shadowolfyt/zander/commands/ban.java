package com.shadowolfyt.zander.commands;

import com.shadowolfyt.zander.ZanderMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.bukkit.Bukkit.getServer;

public class ban implements CommandExecutor {
    ZanderMain plugin;

    public ban(ZanderMain instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("zander.ban")) {

            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Please specify a player to ban.");
                return true;
            }

            Player target = getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "That player could not be located.");
                return true;
            } else {
                //
                // Database Query
                // Add new punishment to database.
                //
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                    Date date = new Date(System.currentTimeMillis());

                    String reason = "";
                    for (String s : args) {
                        reason += s + " ";
                    }
                    reason = reason.replaceFirst(args[0], "");
                    reason.trim();

                    PreparedStatement insertstatement = plugin.getConnection().prepareStatement("INSERT INTO punishments (punisheduseruuid, punishedusername, punisheruuid, punisherusername, punishtype, reason, punishtimestamp) VALUES (?, ?, ?, ?, ?, ?, ?)");

                    insertstatement.setString(1, target.getUniqueId().toString());
                    insertstatement.setString(2, target.getDisplayName());
                    insertstatement.setString(3, player.getUniqueId().toString());
                    insertstatement.setString(4, player.getDisplayName());
                    insertstatement.setString(5, "BAN");
                    insertstatement.setString(6, reason);
                    insertstatement.setString(7, formatter.format(date));

                    insertstatement.executeUpdate();
                    plugin.getServer().getConsoleSender().sendMessage(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("developmentprefix")) + " " + target.getDisplayName() + " has been punished. Adding punishment record to database.");
                    target.getPlayer().kickPlayer(ChatColor.YELLOW + "You have been banned by " + ChatColor.RESET +  player.getDisplayName() + "\nReason: " + ChatColor.YELLOW +  reason);

                    for (Player p : Bukkit.getOnlinePlayers()){
                        if (p.hasPermission("zander.punishnotify")) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix")) + " " + target.getDisplayName() + " has been banned by " + player.getDisplayName() + " for " + reason);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
    }
}
