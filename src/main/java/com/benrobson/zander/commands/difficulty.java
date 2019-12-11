package com.benrobson.zander.commands;

import com.benrobson.zander.guis.DifficultyGUI;
import com.benrobson.zander.ZanderMain;
import org.bukkit.ChatColor;
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
