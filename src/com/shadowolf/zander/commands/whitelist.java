package com.shadowolf.zander.commands;

import com.shadowolf.zander.guis.WhitelistGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class whitelist implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("zander.whitelist")) {
            if (args.length == 0) {
                new WhitelistGUI(player);
            } else {
                new WhitelistGUI(player);
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
        return true;
    }
}