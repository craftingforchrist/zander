package com.benrobson.zander.commands;

import com.benrobson.zander.ZanderMain;
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
                PreparedStatement findstatement = plugin.getConnection().prepareStatement("SELECT * FROM playerdata WHERE username=?");
                findstatement.setString(1, args[0]);
                ResultSet results = findstatement.executeQuery();
                if (results.next()) {
                    sender.sendMessage("\n");
                    sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + results.getString("username") + "'s Profile");
                    sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Joins: " + ChatColor.RESET + results.getInt("joins"));
                    sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Deaths: " + ChatColor.RESET + results.getInt("deaths"));
                    sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Status: " + ChatColor.RESET + results.getString("status"));
                    sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Last Seen: " + ChatColor.RESET + results.getString("lastseen"));
                    sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Total Playtime: " + ChatColor.RESET + results.getString("totalplaytime"));
                    sender.sendMessage("\n");
                    sender.sendMessage(ChatColor.BLUE + "View " + results.getString("username") + "'s profile here: " + plugin.getConfig().getString("web.siteaddress") + "/profile/" + args[0]);
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
}
