package net.craftingforchrist.zander.hub;

import net.craftingforchrist.zander.hub.commands.fly;
import net.craftingforchrist.zander.hub.events.*;
import net.craftingforchrist.zander.hub.gui.HubCompass;
import net.craftingforchrist.zander.hub.protection.HubCreatureSpawnProtection;
import net.craftingforchrist.zander.hub.protection.HubInteractProtection;
import net.craftingforchrist.zander.hub.protection.HubProtection;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ZanderHubMain extends JavaPlugin {
    public static ZanderHubMain plugin;
    public ConfigurationManager configurationManager;

    @Override
    public void onEnable() {
        plugin = this;
        loadConfigurationManager();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessageChannel(this));

        // Init Message
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\nZander Hub has been enabled.\nRunning Version " + plugin.getDescription().getVersion() + "\nGitHub Repository: https://github.com/crafting-for-christ/zander-hub\nCreated by Ben Robson\n\n");

        // Event Registry
        PluginManager pluginmanager = this.getServer().getPluginManager();
        pluginmanager.registerEvents(new HubPlayerJoin(this), this);
        pluginmanager.registerEvents(new HubPlayerVoid(this), this);
        pluginmanager.registerEvents(new HubBoosterPlate(this), this);
        pluginmanager.registerEvents(new HubPlayerJoinChristmas(this), this);
            // Hub Protection
            pluginmanager.registerEvents(new HubProtection(this), this);
            pluginmanager.registerEvents(new HubInteractProtection(this), this);
            pluginmanager.registerEvents(new HubCreatureSpawnProtection(this), this);

        // Item Event Registry
        pluginmanager.registerEvents(new HubCompass(), this);

        // Command Registry
        this.getCommand("fly").setExecutor(new fly());

        configurationManager.getHubLocation();
        saveConfig();
    }

    public void loadConfigurationManager() {
        configurationManager = new ConfigurationManager(plugin);
        configurationManager.loadlocalConfiguration(); // Loading the config.yml

        configurationManager.setupmotd();
    }

    @Override
    public void onDisable() {

        loadConfigurationManager();
    }
}
