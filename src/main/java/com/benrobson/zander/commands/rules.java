package com.benrobson.zander.commands;

import com.benrobson.zander.ZanderMain;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class rules implements CommandExecutor {
    ZanderMain plugin;

    public rules(ZanderMain instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        player.sendMessage("Please read and abide by the rules which you can find on our website here: " + ChatColor.BLUE + plugin.getConfig().getString("web.siteaddress") + "rules");
        return true;
    }
}
