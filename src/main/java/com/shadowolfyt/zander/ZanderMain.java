package com.shadowolfyt.zander;

import com.shadowolfyt.zander.commands.*;
import com.shadowolfyt.zander.events.*;
import com.shadowolfyt.zander.guis.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class ZanderMain extends JavaPlugin {

    @Override
    public void onEnable() {
        loadConfig();
        configDefaults();
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nZander has been enabled.\n");

        // Events Registry
        //getServer().getPluginManager().registerEvents(new EnderDragonDeath(), this);
        getServer().getPluginManager().registerEvents(new PlayerOnJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerOnQuit(this), this);
        getServer().getPluginManager().registerEvents(new ServerListPing(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(this), this);
        getServer().getPluginManager().registerEvents(new WhitelistListener(this), this);
        getServer().getPluginManager().registerEvents(new WhitelistGUI(null), this);
        getServer().getPluginManager().registerEvents(new WhitelistListGUI(null), this);
        getServer().getPluginManager().registerEvents(new JukeboxGUI(this), this);
        //getServer().getPluginManager().registerEvents(new MerryChristmas(this), this); // December 25th Christmas MOTD Changes

        // Command Registry
        this.getCommand("heal").setExecutor((CommandExecutor)new heal());
        this.getCommand("feed").setExecutor((CommandExecutor)new feed());
        this.getCommand("starve").setExecutor((CommandExecutor)new starve());
        this.getCommand("adventure").setExecutor((CommandExecutor)new adventure());
        this.getCommand("creative").setExecutor((CommandExecutor)new creative());
        this.getCommand("survival").setExecutor((CommandExecutor)new survival());
        this.getCommand("spectator").setExecutor((CommandExecutor)new spectator());
        this.getCommand("fly").setExecutor((CommandExecutor)new fly());
        this.getCommand("profile").setExecutor((CommandExecutor)new profile(this));
        this.getCommand("whitelist").setExecutor((CommandExecutor)new whitelist());
        this.getCommand("jukebox").setExecutor((CommandExecutor)new jukebox());

        // Check for Bot token in config files.
        if (this.getConfig().getString("bottoken") == "TOKEN") {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "Zander has disabled the Discord Integration feature due to an invalid token.");
        } else {
            getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Zander has detected a Discord bot token. Attempting to make connection.");
        }
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void configDefaults() {
        this.getConfig().addDefault("bottoken", "TOKEN"); // Discord Integration Bot Token [Coming Soon]
        // Default MOTD Message.
        this.getConfig().addDefault("motd.line1", "&b&lzander&r enabled server");
        this.getConfig().addDefault("motd.line2", "&eYou should probably change this in the config.yml");
        // Starting players database file.
        this.getConfig().addDefault("players", null);
        saveConfig();
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "\n\nZander has been disabled.\n");
        loadConfig();
    }
}
