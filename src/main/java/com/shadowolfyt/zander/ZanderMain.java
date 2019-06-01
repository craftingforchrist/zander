package com.shadowolfyt.zander;

import com.shadowolfyt.zander.commands.*;
import com.shadowolfyt.zander.discord.DiscordMain;
import com.shadowolfyt.zander.events.*;
import com.shadowolfyt.zander.guis.*;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ZanderMain extends JavaPlugin {
    private Connection connection;
    public static ZanderMain plugin;

    @Override
    public void onEnable() {
        plugin = this;
        establishConnection();
        loadConfiguration();

        // Init Message
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nZander has been enabled.\nRunning Version " + plugin.getDescription().getVersion() + "\nGitHub Repository: https://github.com/shadowolfyt/zander\nCreated by shadowolfyt\n\n");

        // Events Registry
        getServer().getPluginManager().registerEvents(new PlayerOnJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerOnQuit(this), this);
        getServer().getPluginManager().registerEvents(new ServerListPing(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(this), this);
        getServer().getPluginManager().registerEvents(new WhitelistListener(this), this);
        getServer().getPluginManager().registerEvents(new WhitelistGUI(this), this);
        getServer().getPluginManager().registerEvents(new WhitelistListGUI(null), this);
        getServer().getPluginManager().registerEvents(new DifficultyGUI(this), this);
        getServer().getPluginManager().registerEvents(new JukeboxGUI(this), this);
        getServer().getPluginManager().registerEvents(new PlayerGamemodeChange(), this);
        getServer().getPluginManager().registerEvents(new FallIntoEndVoidListener(), this);
        getServer().getPluginManager().registerEvents(new MobDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new EnderDragonDeathListener(this), this);
        // Discord Events Registry
        DiscordMain discordMain = new DiscordMain(this);// DiscordMain registers itself as an event listener, no need to do it again!

        // Command Registry
        this.getCommand("heal").setExecutor(new heal());
        this.getCommand("feed").setExecutor(new feed());
        this.getCommand("starve").setExecutor(new starve());
        this.getCommand("adventure").setExecutor(new adventure());
        this.getCommand("creative").setExecutor(new creative());
        this.getCommand("survival").setExecutor(new survival());
        this.getCommand("spectator").setExecutor(new spectator());
        this.getCommand("fly").setExecutor(new fly());
        this.getCommand("profile").setExecutor(new profile(this));
        this.getCommand("whitelist").setExecutor(new whitelist());
        this.getCommand("jukebox").setExecutor(new jukebox());
        this.getCommand("punish").setExecutor(new punish());
        this.getCommand("kick").setExecutor(new kick(this));
        this.getCommand("discord").setExecutor(new discord(this));
        this.getCommand("freeze").setExecutor(new freeze());
        this.getCommand("pardon").setExecutor(new pardon(this));
        this.getCommand("difficulty").setExecutor(new difficulty(this));

        // Recipe Registry
        Bukkit.addRecipe(new FurnaceRecipe(new NamespacedKey(plugin, "furnace_flesh_leather"), new ItemStack(Material.LEATHER), Material.ROTTEN_FLESH, 0, 1200));
    }

    public void establishConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://" + getConfig().getString(
                            "database.ip") + ":" + getConfig().getString(
                            "database.port") + "/" + getConfig().getString("database.databasename"),
                    getConfig().getString("database.username"),
                    getConfig().getString("database.password"));
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Database connection was successful.");
        } catch (SQLException e) {
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("developmentprefix")) + ChatColor.RED + " Database connection failed!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("developmentprefix")) + ChatColor.RED + " Database connection failed!");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        if(this.connection == null) {
            establishConnection();
        }
        return connection;
    }

    private void loadConfiguration() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        plugin.saveConfig();
    }

    @Override
    public void onDisable() {
        try {
            TextChannel textChannel = DiscordMain.getInstance().getJda()
                    .getTextChannelsByName(plugin.getConfig().getString("discord.chatchannel"), true).get(0);
            textChannel.sendMessage("** :x: Server is offline **").queue();

            DiscordMain.getInstance().getJda().shutdown();
        } catch(NullPointerException npe) {
            npe.printStackTrace();
        }

        getServer().getConsoleSender().sendMessage(ChatColor.RED + "\n\nZander has been disabled.\n");
        loadConfiguration();
    }
}
