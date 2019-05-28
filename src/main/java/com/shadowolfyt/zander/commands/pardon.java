package com.shadowolfyt.zander.commands;

import com.shadowolfyt.zander.ZanderMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.bukkit.Bukkit.getServer;

public class pardon implements CommandExecutor {
    ZanderMain plugin;

    public pardon(ZanderMain instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("zander.pardon")) {

            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Please specify a player to pardon.");
                return true;
            }

            // TODO: Make this to get offline player.
            Player target = getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "That player could not be located.");
                return true;
            } else if (!target.isBanned()) {
                player.sendMessage(ChatColor.RED + "That player is not banned.");
            }

            for (Player p : Bukkit.getOnlinePlayers()){
                if (p.hasPermission("zander.punishnotify")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix")) + " " + target.getDisplayName() + " has been pardoned by " + player.getDisplayName());
                }
            }
            return true;
            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
        }
    }
}
