package com.shadowolf.zander.commands;

import com.shadowolf.zander.zander;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class profile implements CommandExecutor {

    zander plugin;

    public profile(zander instance){
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player  = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("\n");
            player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + player.getDisplayName() + "'s Profile");
            player.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Joins: " + ChatColor.RESET + plugin.getConfig().getInt(player.getDisplayName() +  ".joins"));
            player.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Leaves: " + ChatColor.RESET + plugin.getConfig().getInt(player.getDisplayName() +  ".leaves"));
            player.sendMessage("\n");
            return true;
        }

        Player target = Bukkit.getServer().getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "That player could not be located.");
            return true;
        } else {
            player.sendMessage("\n");
            player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + target.getDisplayName() + "'s Profile");
            player.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Joins: " + ChatColor.RESET + plugin.getConfig().getInt(target.getDisplayName() +  ".joins"));
            player.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Leaves: " + ChatColor.RESET + plugin.getConfig().getInt(target.getDisplayName() +  ".leaves"));

            player.sendMessage("\n");
            return true;
        }
    }
}
