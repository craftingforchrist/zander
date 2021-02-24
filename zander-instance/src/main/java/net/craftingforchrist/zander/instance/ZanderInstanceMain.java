package net.craftingforchrist.zander.instance;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;

public class ZanderInstanceMain extends JavaPlugin {
    public static ZanderInstanceMain plugin;
    private Connection connection;

    @Override
    public void onEnable() {
        plugin = this;

        // Plugin Load Message
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\n\n" + plugin.getDescription().getName() + " is now enabled.\nRunning Version: " + plugin.getDescription().getVersion() + "\nGitHub Repository: https://github.com/craftingforchrist/zander-instance\nCreated By: " + plugin.getDescription().getAuthors() + "\n\n");

        // Plugin Event Register
        PluginManager pluginmanager = this.getServer().getPluginManager();
//        pluginmanager.registerEvents(new EggFindEvent(), this);

        plugin.saveDefaultConfig(); // Generate configuration file
    }

    @Override
    public void onDisable() {
        // Plugin Shutdown Message
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "\n\n" + plugin.getDescription().getName() + " is now disabled.\n\n");
    }

}
