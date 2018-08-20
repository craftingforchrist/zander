package com.shadowolf.zander.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class kick implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0){
            sender.sendMessage(ChatColor.RED + "Please specify a player to kick.");
            return true;
        }
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if(target == null){
            sender.sendMessage(ChatColor.RED + "Could not find the player " + args[0]);
            return true;
        }
        target.kickPlayer(ChatColor.RED + "You have been kicked!");
        Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "Player " + target.getName() + " has been kicked by " + sender.getName());
        return true;
    }
}
