package me.benrobson.zander;

import me.benrobson.zander.commands.*;
import me.benrobson.zander.events.PlayerOnDisconnect;
import me.benrobson.zander.events.PlayerOnJoin;
import me.benrobson.zander.events.PlayerOnVote;
import me.benrobson.zander.events.ServerListPing;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ZanderBungeeMain extends Plugin {
    private static ZanderBungeeMain plugin;
    public static ConfigurationManager configurationManager;
    private Connection connection;

    @Override
    public void onEnable() {
        setInstance(this);
        configurationManager.initConfig(); // Create and load config.yml
        establishConnection(); // Connect to the database

        //
        // Database Query
        // End all players game sessions that are NULL.
        //
        try {
            PreparedStatement updatestatement = plugin.getConnection().prepareStatement("UPDATE gamesessions SET sessionend = NOW() where sessionend is null");
            updatestatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Command Registry
        getProxy().getPluginManager().registerCommand(this, new rules());
        getProxy().getPluginManager().registerCommand(this, new ping());
        getProxy().getPluginManager().registerCommand(this, new hub());
        getProxy().getPluginManager().registerCommand(this, new discord());
        getProxy().getPluginManager().registerCommand(this, new build());
        getProxy().getPluginManager().registerCommand(this, new survival());

        // Event Registry
        getProxy().getPluginManager().registerListener(this, new PlayerOnJoin());
        getProxy().getPluginManager().registerListener(this, new PlayerOnDisconnect());
        getProxy().getPluginManager().registerListener(this, new PlayerOnVote());
        getProxy().getPluginManager().registerListener(this, new ServerListPing());
    }

    @Override
    public void onDisable() {
        this.getLogger().info(ChatColor.BLUE + "Shutting down.");
    }

    public static ZanderBungeeMain getInstance() {
        return plugin;
    }

    private static void setInstance(ZanderBungeeMain instance) {
        ZanderBungeeMain.plugin = instance;
    }

    public void establishConnection() {
        try {
            String host = ConfigurationManager.getConfig().getString("database.host");
            String database = ConfigurationManager.getConfig().getString("database.database");
            String username = ConfigurationManager.getConfig().getString("database.username");
            String password = ConfigurationManager.getConfig().getString("database.password");

            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database, username, password);
            this.getLogger().info(ChatColor.GREEN + "Database connection was successful.");
        } catch (SQLException e) {
            this.getLogger().info(ChatColor.RED + "Database connection failed!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            this.getLogger().info(ChatColor.RED + "Database connection failed!");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        if (this.connection == null) {
            establishConnection();
        } else {
            try {
                this.connection.close();
            } catch (SQLException e) {
                this.getLogger().info(ChatColor.RED + "Database connection failed!");
                e.printStackTrace();
            }
            establishConnection();
        }
        return connection;
    }
}
