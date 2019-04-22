package com.shadowolfyt.zander.commands;

import com.shadowolfyt.zander.guis.JukeboxGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.shadowolfyt.zander.ZanderMain.plugin;

public class jukebox implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("zander.jukebox")) {
            if (plugin.getConfig().getString("features.jukeboxradio") == "true") {
                if (args.length == 0) {
                    new JukeboxGUI(player);
                    return true;
                }
            } else {
                player.sendMessage(ChatColor.RED + "This feature is not enabled. You can enable this in the config.yml.");
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
        return true;
    }
}
