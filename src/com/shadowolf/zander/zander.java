package com.shadowolf.zander;

import com.shadowolf.zander.Events.events;
import com.shadowolf.zander.commands.feedCommand;
import com.shadowolf.zander.commands.healCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class zander extends JavaPlugin {
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nZander has been enabled.\n");
        getServer().getPluginManager().registerEvents(new events(), this);
        loadConfig();

        // Command Register
        this.getCommand("heal").setExecutor((CommandExecutor)new healCommand());
        this.getCommand("feed").setExecutor((CommandExecutor)new feedCommand());
    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "\n\nZander has been disabled.\n");
    }

    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
