package com.shadowolf.zander;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigurationManager {
    private zander plugin = zander.getPlugin(zander.class);

    // Files and Configs here.
    public FileConfiguration playersconfig;
    public File playersfile;

    public void setup() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        playersfile = new File(plugin.getDataFolder(), "players.yml");

        if (!playersfile.exists()) {
            try {
                playersfile.createNewFile();
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "The players.yml file has been created successfully.");
            } catch (IOException event) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not create the players.yml file.");
            }
        }

        playersconfig = YamlConfiguration.loadConfiguration(playersfile);
    }

    public FileConfiguration getPlayers() {
        return playersconfig;
    }

    public void savePlayers() {
        try {
            playersconfig.save(playersfile);
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "The players.yml file has been successfully saved.");
        } catch (IOException event) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Failed to save players.yml file.");
        }
    }

    public void reloadPlayers() {
        playersconfig = YamlConfiguration.loadConfiguration(playersfile);
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "The players.yml file has been successfully reloaded.");
    }
}
