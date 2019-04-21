package com.shadowolfyt.zander.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class fly implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("zander.fly")) {
            if(args.length == 0) {
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    toggleUserFly(player);
                } else {
                    sender.sendMessage("This command only works for you if you are in-game.");
                }
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null) {
                    toggleUserFly(target);
                    sender.sendMessage(ChatColor.GREEN + target.getName() + "'s fly was toggled.");
                } else {
                    sender.sendMessage(ChatColor.RED + "Player not found.");
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
        return true;
    }

    private boolean toggleUserFly(Player player) {
        if(player == null) return false;

        boolean fly = player.isFlying() || player.getAllowFlight();

        player.setAllowFlight(!fly);
        player.setFlying(!fly);
        player.sendMessage(ChatColor.GREEN + "You are " + (!fly ? "no longer" : "now") + " flying.");

        return true;
    }
}
