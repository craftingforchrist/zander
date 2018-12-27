package com.shadowolfyt.zander;

import com.connorlinfoot.titleapi.TitleAPI;
import com.shadowolfyt.zander.commands.*;
import com.shadowolfyt.zander.events.*;
import com.shadowolfyt.zander.guis.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Random;

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


        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int number = random.nextInt(4) + 1;

                if (number == 1) {
                    Bukkit.broadcastMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "[zander] " + ChatColor.RESET + "Have you joined this Discord server? If you haven't you should totally join: " + ChatColor.BLUE + "https://bit.ly/mancavediscord");
                } else if (number == 2) {
                    PluginDescriptionFile pluginInfo = getDescription();
                    String pluginVer = pluginInfo.getVersion();
                    Bukkit.broadcastMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "[zander] " + ChatColor.RESET + "This server is running zander version " + pluginVer + " developed by shadowolfyt.");
                } else if (number == 3) {
                    Bukkit.broadcastMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "[zander] " + ChatColor.RESET + "A new cube has formed at " +  ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD +  "-240 -750" +  ChatColor.RESET + ". More information to come soon..");
                } else if (number == 4) {
                    Bukkit.broadcastMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "[zander] " + ChatColor.RESET + "To see what is happening in the development of zander, check out the Open Source GitHub Repository here: " + ChatColor.RED + "https://github.com/shadowolfyt/zander");
                }
            }
        }, 300, 25000);
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
