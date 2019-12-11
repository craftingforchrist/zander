package com.benrobson.zander.commands;

import com.benrobson.zander.ZanderMain;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class pardon implements CommandExecutor {
    ZanderMain plugin;

    public pardon(ZanderMain instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("zander.pardon")) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

            if (target.isBanned()) {
                String pardoner = "CONSOLE";
                if (sender instanceof Player) {
                    pardoner = sender.getName();
                }

                for (Player p : Bukkit.getOnlinePlayers()){
                    if (p.hasPermission("zander.punishnotify")) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.punishmentprefix")) + " " + args[0] + " has been unbanned by " + pardoner);
                    }
                }

                // Unban the user.
                Bukkit.getBanList(BanList.Type.NAME).pardon(args[0]);
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("punish.playernotbanned")));
            }


        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("error.nopermission")));
            return true;
        }
        return true;
    }
}
