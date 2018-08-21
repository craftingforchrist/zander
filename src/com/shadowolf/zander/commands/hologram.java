package com.shadowolf.zander.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

public class hologram implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player  = (Player) sender;

        if (player.hasPermission("zander.hologram")) {
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "You must provide text to be displayed.");
                return true;
            }

            ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);

            stand.setGravity(false);
            stand.setVisible(false);

            stand.setCustomNameVisible(true);
            stand.setCustomName(ChatColor.GREEN + args[0]);
            return true;
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
    }
}
