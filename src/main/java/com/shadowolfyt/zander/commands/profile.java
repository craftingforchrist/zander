package com.shadowolfyt.zander.commands;

import com.shadowolfyt.zander.ZanderMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class profile implements CommandExecutor {
    ZanderMain plugin;
    public profile(ZanderMain instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player  = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Correct Usage: /profile [PlayerName]");
        } else {
            try {
                PreparedStatement findstatement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.getConfig().getString("database.playerdatatable") + " WHERE username=?");
                findstatement.setString(1, args[0]);
                ResultSet results = findstatement.executeQuery();
                if (results.next()) {
                    player.sendMessage("\n");
                    player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + results.getString("username") + "'s Profile");
                    player.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Joins: " + ChatColor.RESET + results.getInt("joins"));
                    player.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Leaves: " + ChatColor.RESET + results.getInt("leaves"));
                    player.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Deaths: " + ChatColor.RESET + results.getInt("deaths"));
                    player.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Last Seen: " + ChatColor.RESET + results.getString("lastseen"));
                    player.sendMessage("\n");
                    return true;
                }
            } catch (SQLException e) {
                player.sendMessage(ChatColor.RED + "This player cannot be found in the database.");
                e.printStackTrace();
            }
        }
        return true;
    }
}
