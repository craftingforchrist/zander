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
        getServer().getPluginManager().registerEvents(new PlayerOnJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerOnQuit(this), this);
        getServer().getPluginManager().registerEvents(new ServerListPing(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(this), this);
        getServer().getPluginManager().registerEvents(new WhitelistListener(this), this);
        getServer().getPluginManager().registerEvents(new WhitelistGUI(null), this);
        getServer().getPluginManager().registerEvents(new WhitelistListGUI(null), this);
        getServer().getPluginManager().registerEvents(new JukeboxGUI(this), this);
        getServer().getPluginManager().registerEvents(new PunishGUI(this), this);

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
        this.getCommand("punish").setExecutor((CommandExecutor)new punish());
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void configDefaults() {
        // Default MOTD Message.
        this.getConfig().addDefault("motd.line1", "&b&lzander&r enabled server");
        this.getConfig().addDefault("motd.line2", "&eYou should probably change this in the config.yml");
        // Default MOTD Message.
        this.getConfig().addDefault("tabtitle.header", ChatColor.AQUA.toString() + ChatColor.BOLD + "zander" + ChatColor.GOLD + " enabled server");
        this.getConfig().addDefault("tabtitle.footer", ChatColor.GOLD + "You should probably change this in the config.yml");
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
