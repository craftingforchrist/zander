package com.shadowolfyt.zander.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class starve implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player  = (Player) sender;

        if (player.hasPermission("zander.starve")) {
            if (args.length == 0) {
                player.setFoodLevel(0);
                player.sendMessage(ChatColor.GREEN + "You have been" + ChatColor.RED + ChatColor.BOLD + " STARVED.");
                return true;
            }

            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "That player could not be located.");
                return true;
            }

            target.setFoodLevel(0);
            target.sendMessage(ChatColor.GREEN + "You have been fed.");
            player.sendMessage(ChatColor.GREEN + target.getName() + " has been successfully" + ChatColor.RED + ChatColor.BOLD + " STARVED.");
            return true;
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
    }
}
