package com.shadowolf.zander.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class profile implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        player.sendMessage(ChatColor.GOLD.toString() + ChatColor.GOLD + player.getDisplayName() + "'s Profile");
        player.sendMessage(ChatColor.AQUA.toString() + ChatColor.ITALIC + "Joins: ");

        return true;
    }
}
