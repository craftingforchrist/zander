package com.shadowolf.zander;

import com.shadowolf.zander.Events.events;
import com.shadowolf.zander.commands.feed;
import com.shadowolf.zander.commands.heal;
import com.shadowolf.zander.commands.kick;
import com.shadowolf.zander.commands.starve;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class zander extends JavaPlugin {
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nZander has been enabled.\n");
        getServer().getPluginManager().registerEvents(new events(), this);
        loadConfig();

        // Command Registry
        this.getCommand("heal").setExecutor((CommandExecutor)new heal());
        this.getCommand("feed").setExecutor((CommandExecutor)new feed());
        this.getCommand("starve").setExecutor((CommandExecutor)new starve());
        this.getCommand("kick").setExecutor((CommandExecutor)new kick());
    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "\n\nZander has been disabled.\n");
    }

    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
