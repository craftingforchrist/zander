package com.shadowolfyt.zander.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class feed implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if (player.hasPermission("zander.feed")) {
            if (args.length == 0) {
                player.setFoodLevel(20);
                player.sendMessage(ChatColor.GREEN + "You have been fed.");
                return true;
            }

            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "That player could not be located.");
                return true;
            }

            target.setFoodLevel(20);
            target.sendMessage(ChatColor.GREEN + "You have been fed.");
            player.sendMessage(ChatColor.GREEN + target.getName() + " has been successfully fed.");
            return true;
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
    }
}
