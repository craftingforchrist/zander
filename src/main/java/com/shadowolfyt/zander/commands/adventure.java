package com.shadowolfyt.zander.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class adventure implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player  = (Player) sender;
        GameMode pgamemode = player.getGameMode();

        if (player.hasPermission("zander.adventure")) {
            if (args.length == 0) {
                if (pgamemode == GameMode.ADVENTURE) {
                    player.sendMessage(ChatColor.YELLOW + "You are currently in this gamemode.");
                    return true;
                } else {
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(ChatColor.GREEN + "Your gamemode has been updated to " + ChatColor.GOLD.toString() + ChatColor.BOLD + player.getGameMode());
                    return true;
                }
            }

            // Something to come back to: If the targeted player is already in said gamemode it won't complete the operation and will display a message.
            Player target = Bukkit.getServer().getPlayer(args[0]);
            GameMode tgamemode = target.getGameMode();
            if (target == null) {
                player.sendMessage(ChatColor.RED + "That player could not be located.");
                return true;
            }

            if (tgamemode == GameMode.ADVENTURE) {
                player.sendMessage(ChatColor.YELLOW + "That player is currently in this gamemode.");
                return true;
            }

            player.setGameMode(GameMode.ADVENTURE);
            target.sendMessage(ChatColor.GREEN + "Your gamemode has been updated to " + ChatColor.GOLD.toString() + ChatColor.BOLD + player.getGameMode());
            player.sendMessage(ChatColor.GREEN + target.getName() + "'s gamemode has been updated to " + ChatColor.GOLD.toString() + ChatColor.BOLD + player.getGameMode());
            return true;
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
    }
}
