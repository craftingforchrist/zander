package com.benrobson.zander.commands;

import com.connorlinfoot.titleapi.TitleAPI;
import com.benrobson.zander.ZanderMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class warn implements CommandExecutor {
    ZanderMain plugin;

    public warn(ZanderMain instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("zander.warn")) {

            if (args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("punish.specifyplayerandreasoning")));
                return true;
            } else if (args.length == 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("punish.specifyreason")));
            } else {
                Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("punish.playernotonline")));
                    return true;
                }

                StringBuilder str = new StringBuilder();

                for (int i = 1; i < args.length; i++) {
                    str.append(args[i] + " ");
                }

                String warner = "CONSOLE";
                if (sender instanceof Player) {
                    warner = sender.getName();
                }

                for (Player p : Bukkit.getOnlinePlayers()){
                    if (p.hasPermission("zander.punishnotify")) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.punishmentprefix")) + " " + target.getDisplayName() + " has been warned by " + warner + " for " + str.toString().trim());
                    }
                }

                TitleAPI.sendTitle(target, 40, 50, 40, ChatColor.GOLD + "You have been warned!", ChatColor.WHITE + str.toString().trim());
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.pluginprefix")) + " You have been warned by " + warner + " for " + str.toString().trim());

                //
                // Database Query
                // Add new punishment to database.
                //
                try {
                    PreparedStatement insertstatement = plugin.getConnection().prepareStatement("INSERT INTO gamepunishments (punisheduser_id, punisher_id, punishtype, reason) values ((select id from playerdata where username = ?), (select id from playerdata where username = ?), 'WARN', ?);");

                    insertstatement.setString(1, target.getDisplayName());
                    insertstatement.setString(2, warner);
                    insertstatement.setString(3, str.toString().trim());

                    insertstatement.executeUpdate();
                    plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.developmentprefix")) + " " + target.getDisplayName() + " has been warned. Adding punishment record to database.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
        return true;
    }
}
