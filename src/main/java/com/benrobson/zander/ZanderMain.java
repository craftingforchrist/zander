package com.benrobson.zander;

import com.benrobson.zander.commands.*;
import com.benrobson.zander.discord.DiscordMain;
import com.benrobson.zander.discord.moderation.BlockPlaceAdminLog;
import com.benrobson.zander.discord.moderation.CommandAdminLog;
import com.benrobson.zander.discord.moderation.TNTLightAdminLog;
import com.benrobson.zander.events.*;
import com.benrobson.zander.guis.DifficultyGUI;
import com.benrobson.zander.guis.JukeboxGUI;
import com.benrobson.zander.guis.WhitelistGUI;
import com.benrobson.zander.guis.WhitelistListGUI;
import com.benrobson.zander.recipes.ElytraRecipe;
import com.benrobson.zander.recipes.ExperienceBottleRecipe;
import com.benrobson.zander.recipes.RabbitSkinRecipe;
import com.benrobson.zander.recipes.TridentRecipe;
import com.benrobson.zander.commands.*;
import com.benrobson.zander.events.*;
import com.benrobson.zander.guis.*;
import net.dv8tion.jda.core.entities.TextChannel;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class ZanderMain extends JavaPlugin {
    private Connection connection;
    public static ZanderMain plugin;
    public ConfigurationManager configurationManager;

    @Override
    public void onEnable() {
        plugin = this;
        loadConfigurationManager();
        establishConnection();

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

        // Init Message
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nZander has been enabled.\nRunning Version " + plugin.getDescription().getVersion() + "\nGitHub Repository: https://github.com/benrobson/zander\nCreated by Ben Robson\n\n");

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
        getServer().getPluginManager().registerEvents(new PlayerGamemodeChange(this), this);
        getServer().getPluginManager().registerEvents(new FallIntoEndVoidListener(), this);
        getServer().getPluginManager().registerEvents(new EnderDragonDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new SwearFilter(this), this);
        getServer().getPluginManager().registerEvents(new LinkFilter(this), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceAdminLog(this), this);
        getServer().getPluginManager().registerEvents(new TNTLightAdminLog(this), this);
        getServer().getPluginManager().registerEvents(new CommandAdminLog(this), this);
        getServer().getPluginManager().registerEvents(new PlayerSleepEvent(this), this);
        getServer().getPluginManager().registerEvents(new CraftDisable(this), this);
        getServer().getPluginManager().registerEvents(new EndCrystalPercentDrop(this), this);
        getServer().getPluginManager().registerEvents(new ChargedCreeperHeadDrop(this), this);
        getServer().getPluginManager().registerEvents(new PigmanQuartzAgro(this), this);
        getServer().getPluginManager().registerEvents(new BanListener(this), this);

        DiscordMain DiscordMain = new DiscordMain(this);

        // Command Registry
        this.getCommand("heal").setExecutor(new heal());
        this.getCommand("feed").setExecutor(new feed());
        this.getCommand("starve").setExecutor(new starve());
        this.getCommand("adventure").setExecutor(new adventure());
        this.getCommand("creative").setExecutor(new creative());
        this.getCommand("survival").setExecutor(new survival());
        this.getCommand("spectator").setExecutor(new spectator());
        this.getCommand("fly").setExecutor(new fly());
//        this.getCommand("profile").setExecutor(new profile(this));
        this.getCommand("whitelist").setExecutor(new whitelist());
        this.getCommand("jukebox").setExecutor(new jukebox());
        this.getCommand("punish").setExecutor(new punish(this));
        this.getCommand("kick").setExecutor(new kick(this));
        this.getCommand("ban").setExecutor(new ban(this));
        this.getCommand("warn").setExecutor(new warn(this));
        this.getCommand("report").setExecutor(new report(this));
        this.getCommand("pardon").setExecutor(new pardon(this));
        this.getCommand("discord").setExecutor(new discord(this));
        this.getCommand("freeze").setExecutor(new freeze());
        this.getCommand("difficulty").setExecutor(new difficulty(this));
        this.getCommand("rules").setExecutor(new rules(this));
        this.getCommand("announce").setExecutor(new announce(this));

        // Trident Recipe
        TridentRecipe tr = new TridentRecipe(this);
        tr.TridentRecipe();
        // Rabbit Skin Recipe
        RabbitSkinRecipe rsr = new RabbitSkinRecipe(this);
        rsr.RabbitSkinRecipe();
        // Experience Bottle Recipe
        ExperienceBottleRecipe ebr = new ExperienceBottleRecipe(this);
        ebr.ExperienceBottleRecipe();
        // Elytra Recipe
        ElytraRecipe er = new ElytraRecipe(this);
        er.ElytraRecipe();

        // Annoucer
        AnnouncerManager messager = new AnnouncerManager(this);
        double minutes = this.getConfig().getDouble("interval") * 60.0D;
        messager.runTaskTimer(this, 100L, (long)minutes * 20L);
    }

    public void loadConfigurationManager() {
        configurationManager = new ConfigurationManager(plugin);
        configurationManager.loadlocalConfiguration(); // Loading the config.yml

        configurationManager.setuplang();
        configurationManager.setupfilter();
        configurationManager.setuplinksfilter();
        configurationManager.setupmotd();
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
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', configurationManager.getlang().getString("main.developmentprefix")) + ChatColor.GREEN + " Database connection was successful.");
        } catch (SQLException e) {
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', configurationManager.getlang().getString("main.developmentprefix")) + ChatColor.RED + " Database connection failed!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', configurationManager.getlang().getString("main.developmentprefix")) + ChatColor.RED + " Database connection failed!");
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
                e.printStackTrace();
            }
            establishConnection();
        }
        return connection;
    }

    @Override
    public void onDisable() {
        try {
            TextChannel textChannel = DiscordMain.getInstance().getJda().getTextChannelsByName(plugin.getConfig().getString("discord.chatchannel"), true).get(0);
            textChannel.sendMessage("** :x: Server has closed **").queue();

            DiscordMain.getInstance().getJda().shutdown();
        } catch(NullPointerException npe) {
            npe.printStackTrace();
        }

        getServer().getConsoleSender().sendMessage(ChatColor.RED + "\n\nZander has been disabled.\n");
        loadConfigurationManager();
    }
}
