package com.shadowolfyt.zander;

import com.shadowolfyt.zander.commands.*;
import com.shadowolfyt.zander.discord.DiscordMain;
import com.shadowolfyt.zander.events.*;
import com.shadowolfyt.zander.guis.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ZanderMain extends JavaPlugin {
    private Connection connection;

    public static ZanderMain plugin;
    public FileConfiguration configuration;

    @Override
    public void onEnable() {
        plugin = this;
        establishConnection();
        loadConfiguration();

        PluginDescriptionFile pdf = plugin.getDescription();
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nZander has been enabled.\nRunning Version " + pdf.getVersion() + "\nGitHub Repository: https://github.com/shadowolfyt/zander\nCreated by shadowolfyt\n\n");

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
        getServer().getPluginManager().registerEvents(new DiscordMain(this), this);

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
        this.getCommand("discord").setExecutor((CommandExecutor)new discord(this));
    }


    public void establishConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                "jdbc:mysql://" + getConfig().getString(
                    "database.ip") + ":" + getConfig().getString(
                    "database.port") + "/" + getConfig().getString("database.databasename"),
                getConfig().getString("database.username"),
                getConfig().getString("database.password"));
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Database connection was successful.");
        } catch (SQLException e) {
            e.printStackTrace();

            getServer().getConsoleSender().sendMessage(ChatColor.RED + "Database connection failed!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "Database connection failed!");
        }
    }


    public Connection getConnection()
    {
        return connection;
    }

    private void loadConfiguration() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        plugin.saveConfig();
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "\n\nZander has been disabled.\n");
        loadConfiguration();
    }
}
