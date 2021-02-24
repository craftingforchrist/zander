package net.craftingforchrist.zander.hub;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigurationManager {
    static ZanderHubMain plugin;
    public ConfigurationManager(ZanderHubMain plugin) {
        this.plugin = plugin;
    }
    private static Location HubLocation;

    public FileConfiguration motdFile;
    public File motdfile;

    public void loadlocalConfiguration() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        plugin.saveConfig();
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

    public static Location getHubLocation() {
        World defaultworld = Bukkit.getServer().getWorlds().get(0);
        World hubworld = Bukkit.getWorld(plugin.getConfig().getString("hub.world", defaultworld.getName()));
        if (hubworld == null) {
            Bukkit.getLogger().warning("No world by the name of " + hubworld.getName() + " was found! Assuming default world.");
            hubworld = Bukkit.getServer().getWorlds().get(0);
        }
        double hubx = plugin.getConfig().getDouble("hub.x", defaultworld.getSpawnLocation().getX());
        double huby = plugin.getConfig().getDouble("hub.y", defaultworld.getSpawnLocation().getY());
        double hubz = plugin.getConfig().getDouble("hub.z", defaultworld.getSpawnLocation().getZ());
        float hubyaw = (float)plugin.getConfig().getDouble("hub.yaw", 0);
        float hubpitch = (float)plugin.getConfig().getDouble("hub.pitch", 0);

        HubLocation = new Location(hubworld, hubx, huby, hubz, hubyaw, hubpitch);

        return HubLocation;
    }
}
