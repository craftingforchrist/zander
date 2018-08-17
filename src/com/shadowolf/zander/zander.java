package com.shadowolf.zander;

import com.shadowolf.zander.Events.events;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class zander extends JavaPlugin {
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nZander has been enabled.\n");
        getServer().getPluginManager().registerEvents(new events(), this);
        loadConfig();
    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "\n\nZander has been disabled.\n");
    }

    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("youtube")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage("This is a test command.");
                return true;
            } else {
                sender.sendMessage("Only players can use this command.");
                return true;
            }
        }
        return false;
    }
}
