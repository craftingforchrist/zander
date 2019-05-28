package com.shadowolfyt.zander.commands;

import com.shadowolfyt.zander.ZanderMain;
import com.shadowolfyt.zander.guis.DifficultyGUI;
import com.shadowolfyt.zander.guis.PunishGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class difficulty implements CommandExecutor {
    ZanderMain plugin;

    public difficulty(ZanderMain instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("zander.difficulty")) {

            new DifficultyGUI((Player) sender);

//            if (args[1] == "status") {
//                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix")) + " Server difficulty is currently set to " + ChatColor.BOLD +  ChatColor.YELLOW + player.getWorld().getDifficulty());
//            } else {
//                //new DifficultyGUI((Player) sender);
//            }
            return true;
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
    }
}
