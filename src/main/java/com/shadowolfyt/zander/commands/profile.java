package com.shadowolfyt.zander.commands;

import com.shadowolfyt.zander.ZanderMain;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class profile implements CommandExecutor {
    ZanderMain plugin;

    public profile(ZanderMain instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Correct Usage: /profile [PlayerName]");
        } else {
            try {
                PreparedStatement findstatement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.getConfig().getString("database.playerdatatable") + " WHERE username=?");
                findstatement.setString(1, args[0]);
                ResultSet results = findstatement.executeQuery();
                if (results.next()) {
                    sender.sendMessage("\n");
                    sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + results.getString("username") + "'s Profile");
                    sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Joins: " + ChatColor.RESET + results.getInt("joins"));
                    sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Leaves: " + ChatColor.RESET + results.getInt("leaves"));
                    sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Deaths: " + ChatColor.RESET + results.getInt("deaths"));
                    sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Last Seen: " + ChatColor.RESET + results.getString("lastseen"));
                    sender.sendMessage("\n");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Profile not found.");
                }
            } catch (SQLException e) {
                sender.sendMessage(ChatColor.RED + "An error occurred while looking up this information. Please try again later.");
                e.printStackTrace();
            }
        }
        return true;
    }

    private static boolean doesProfileExist(String username) {
        return ZanderMain.plugin.getConfig().getConfigurationSection("players." + username) != null;
    }
}
