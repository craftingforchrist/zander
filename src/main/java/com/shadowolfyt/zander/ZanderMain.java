package com.shadowolfyt.zander;

import com.shadowolfyt.zander.commands.*;
import com.shadowolfyt.zander.events.*;
import com.shadowolfyt.zander.guis.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ZanderMain extends JavaPlugin {
    @Override
    public void onEnable() {
        establishConnection();
        loadConfig();
        configDefaults();

        PluginDescriptionFile pdf = this.getDescription();
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
        getServer().getPluginManager().registerEvents(new AnnouncementManager(this), this);

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
        this.getCommand("changelog").setExecutor((CommandExecutor)new changelog(this));
    }

    private Connection connection;
    private String connectionStr = "jbdc:mysql://localhost:3306/zander?user=zander&password=Passwordzander321&autoReconnect=true";

    private void establishConnection(){
        try {
            Class.forName("com.mysql.jbdc.Driver");
            this.connection = DriverManager.getConnection(connectionStr);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void configDefaults() {
        // Default MOTD Message.
        this.getConfig().addDefault("motd.line1", "&b&lzander&r enabled server");
        this.getConfig().addDefault("motd.line2", "&eYou should probably change this in the config.yml");
        // Config Booleans
        this.getConfig().addDefault("announcementsenable", true);
        // Announcements Defaults
        this.getConfig().addDefault("announcements.announce1", "This is an example announcement. This announcement can actually be changed in the config.yml believe it or not.");
        this.getConfig().addDefault("announcements.announce2", "This is an example announcement, but this is labelled number 2. This announcement can actually also be changed in the config.yml.. now that is epic.");
        this.getConfig().addDefault("announcements.announce3", "Now this another example announcement, how it got here, I have no clue how.. but guess what, yep, you guessed it, this announcement can actually also be changed in the config.yml.");
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
