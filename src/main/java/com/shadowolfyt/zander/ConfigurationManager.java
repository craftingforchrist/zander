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
    public FileConfiguration filterFile;
    public File filterfile;
    public FileConfiguration motdFile;
    public File motdfile;
    public FileConfiguration linksfilterFile;
    public File linksfilterfile;

    public void loadlocalConfiguration() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        plugin.saveConfig();
    }

    //
    // Language File
    //
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

    //
    // Filter File
    //
    public void setupfilter() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        filterfile = new File(plugin.getDataFolder(), "filter.yml");

        if (!filterfile.exists()) {
            plugin.saveResource("filter.yml", false);
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "The filter.yml file has been created.");
        }
        filterFile = YamlConfiguration.loadConfiguration(filterfile);
    }

    public FileConfiguration getfilter() {
        return filterFile;
    }

    //
    // MOTD File
    //
    public void setupmotd() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        motdfile = new File(plugin.getDataFolder(), "motd.yml");

        if (!motdfile.exists()) {
            plugin.saveResource("motd.yml", false);
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "The motd.yml file has been created.");
        }
        motdFile = YamlConfiguration.loadConfiguration(motdfile);
    }

    public FileConfiguration getmotd() {
        return motdFile;
    }

    //
    // Links Filter File
    //
    public void setuplinksfilter() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        linksfilterfile = new File(plugin.getDataFolder(), "linkfilter.yml");

        if (!linksfilterfile.exists()) {
            plugin.saveResource("linkfilter.yml", false);
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "The linkfilter.yml file has been created.");
        }
        linksfilterFile = YamlConfiguration.loadConfiguration(linksfilterfile);
    }

    public FileConfiguration getlinksfilter() {
        return linksfilterFile;
    }
}
