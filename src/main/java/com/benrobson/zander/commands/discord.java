package com.benrobson.zander.commands;

import com.benrobson.zander.ZanderMain;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class discord implements CommandExecutor {
    ZanderMain plugin;

    public discord(ZanderMain instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if (plugin.getConfig().getString("discord.link", "LINK").equals("LINK")) {
            player.sendMessage(ChatColor.RED + "The owner has not added the Discord server to the config.yml, oops.");
        } else {
            player.sendMessage("Join the server's Discord here: " + ChatColor.BLUE + plugin.getConfig().getString("discord.link", "Unknown"));
        }
        return true;
    }
}
