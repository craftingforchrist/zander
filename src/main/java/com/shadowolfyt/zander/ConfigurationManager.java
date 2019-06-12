package com.shadowolfyt.zander;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigurationManager {
    private ZanderMain plugin;
    public ConfigurationManager(ZanderMain plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration langFile;
    public File langfile;

    public void loadlocalConfiguration() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        plugin.saveConfig();
    }

    public void setuplang() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        langfile = new File(plugin.getDataFolder(), "lang.yml");

        if (!langfile.exists()) {
            plugin.saveResource("lang.yml", false);
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "The lang.yml file has been created.");
        }
        langFile = YamlConfiguration.loadConfiguration(langfile);
    }

    public FileConfiguration getlang() {
        return langFile;
    }

    public void reloadlang() {
        langFile = YamlConfiguration.loadConfiguration(langfile);
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "The lang.yml file has been reloaded.");

    }
}
