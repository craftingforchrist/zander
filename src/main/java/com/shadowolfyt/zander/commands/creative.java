package com.shadowolfyt.zander.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class creative implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player  = (Player) sender;
        GameMode gamemode = player.getGameMode();

        if (player.hasPermission("zander.creative")) {
            if (gamemode == GameMode.CREATIVE) {
                player.sendMessage(ChatColor.YELLOW + "You are currently in this gamemode.");
                return true;
            }

            if (args.length == 0) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(ChatColor.GREEN + "Your gamemode has been updated to " + ChatColor.GOLD.toString() + ChatColor.BOLD + player.getGameMode());
                return true;
            }

            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "That player could not be located.");
                return true;
            }

            player.setGameMode(GameMode.CREATIVE);
            target.sendMessage(ChatColor.GREEN + "Your gamemode has been updated to " + ChatColor.GOLD.toString() + ChatColor.BOLD + player.getGameMode());
            player.sendMessage(ChatColor.GREEN + target.getName() + "'s gamemode has been updated to " + ChatColor.GOLD.toString() + ChatColor.BOLD + player.getGameMode());
            return true;
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
    }
}
