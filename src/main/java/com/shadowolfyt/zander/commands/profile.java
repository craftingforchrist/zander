package com.shadowolfyt.zander.commands;

import com.shadowolfyt.zander.ZanderMain;
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

        try {
            PreparedStatement findstatement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.getConfig().getString("database.playerdatatable") + " WHERE uuid=?");
            findstatement.setString(1, player.getUniqueId().toString());
            ResultSet results = findstatement.executeQuery();
            if (results.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (args.length == 0) {
            player.sendMessage("\n");
            player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + player.getDisplayName() + "'s Profile");
            player.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Joins: " + ChatColor.RESET + plugin.getConfig().getInt("players" + "." + player.getDisplayName() +  ".joins"));
            player.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Leaves: " + ChatColor.RESET + plugin.getConfig().getInt("players" + "." + player.getDisplayName() +  ".leaves"));
            player.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Deaths: " + ChatColor.RESET + plugin.getConfig().getInt("players" + "." + player.getDisplayName() +  ".deaths"));
            player.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Last Seen: " + ChatColor.RESET + plugin.getConfig().getString("players" + "." + player.getDisplayName() +  ".lastseen"));
            if (player.hasPermission("zander.displayipaddress")) {
                player.sendMessage(ChatColor.RED.toString() + ChatColor.ITALIC + "IP Address: " + ChatColor.RESET + plugin.getConfig().getString("players" + "." + player.getDisplayName() + ".ipaddress"));
                player.sendMessage(ChatColor.RED.toString() + ChatColor.ITALIC + "UUID: " + ChatColor.RESET + plugin.getConfig().getString("players" + "." + player.getDisplayName() + ".uuid"));
                player.sendMessage("\n");
                player.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + "[NOTE]: " + ChatColor.RESET + "Normal users cannot see the fields in red. Users that are opped or have the permission node can.");
            }
            player.sendMessage("\n");
            return true;
        }

        if (plugin.getConfig().getString("players" + "." + args[0]) == null) {
            player.sendMessage(ChatColor.RED + "That player could not be located.");
            return true;
        } else {
            player.sendMessage("\n");
            player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + plugin.getConfig().getString("players" + "." + args[0]) + "'s Profile");
            player.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Joins: " + ChatColor.RESET + plugin.getConfig().getInt("players" + "." + args[0] +  ".joins"));
            player.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Leaves: " + ChatColor.RESET + plugin.getConfig().getInt("players" + "." + args[0] +  ".leaves"));
            player.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Deaths: " + ChatColor.RESET + plugin.getConfig().getInt("players" + "." + args[0] +  ".deaths"));
            player.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Last Seen: " + ChatColor.RESET + plugin.getConfig().getString("players" + "." + args[0] +  ".lastseen"));
            if (player.hasPermission("zander.displaydeveloperinformation")) {
                player.sendMessage(ChatColor.RED.toString() + ChatColor.ITALIC + "IP Address: " + ChatColor.RESET + plugin.getConfig().getString("players" + "." + args[0] + ".ipaddress"));
                player.sendMessage(ChatColor.RED.toString() + ChatColor.ITALIC + "UUID: " + ChatColor.RESET + plugin.getConfig().getString("players" + "." + args[0] + ".uuid"));
                player.sendMessage("\n");
                player.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + "[NOTE]: " + ChatColor.RESET + "Normal users cannot see the fields in red. Users that are opped or have the permission node can.");
            }
            player.sendMessage("\n");
            return true;
        }
    }
}
