package com.shadowolfyt.zander.commands;

import com.shadowolfyt.zander.ZanderMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class changelog implements CommandExecutor {
    ZanderMain plugin;
    public changelog(ZanderMain instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player  = (Player) sender;
        PluginDescriptionFile pdf = plugin.getDescription();
        player.sendMessage("Want to see what has been added in version" + pdf.getVersion() + "?");
        player.sendMessage("You can look at updates, features added and bugs fixed here: https://github.com/shadowolfyt/zander/releases/tag/"+ pdf.getVersion());
        return true;
    }
}
