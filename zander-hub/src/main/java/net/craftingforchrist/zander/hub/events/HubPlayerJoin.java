package net.craftingforchrist.zander.hub.events;

import net.craftingforchrist.zander.hub.items.navigationcompass;
import net.craftingforchrist.zander.hub.ConfigurationManager;
import net.craftingforchrist.zander.hub.ZanderHubMain;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.MetadataValue;

import java.util.Arrays;
import java.util.List;

public class HubPlayerJoin implements Listener {
    ZanderHubMain plugin;
    public HubPlayerJoin(ZanderHubMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void HubPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.getInventory().clear();

        // Teleport Player to Hub spawn point
        if (ConfigurationManager.getHubLocation() != null) {
            player.teleport(ConfigurationManager.getHubLocation());
        }

        navigationcompass.givecompass(player); // Give player navigation compass
        player.getInventory().setHeldItemSlot(4); // Set the players current slot to the navigation compass

        event.setJoinMessage(null); // disable default join message

        if (!player.hasPlayedBefore()) {
            // New user Joins for first time celebratory firework
            Firework firework = event.getPlayer().getWorld().spawn(event.getPlayer().getLocation(), Firework.class);
            FireworkMeta fireworkmeta = firework.getFireworkMeta();
            fireworkmeta.addEffect(FireworkEffect.builder()
                    .flicker(false)
                    .trail(true)
                    .with(FireworkEffect.Type.CREEPER)
                    .withColor(Color.GREEN)
                    .withFade(Color.BLUE)
                    .build());
            fireworkmeta.setPower(3);
            firework.setFireworkMeta(fireworkmeta);

            // Send new user a MOTD seperate to the main MOTD
            List<String> newplayermotd = plugin.configurationManager.getmotd().getStringList("newplayermotd");
            for (String s : newplayermotd) {
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', s));
            }
            event.getPlayer().sendMessage(" "); // Seperate between messages

//        NOTE:: This has been removed due to message giving false unique player count.

            // Add +1 to the unique players in config.yml
//            final int unique = plugin.getConfig().getInt("uniqueplayers");
//            plugin.getConfig().set("uniqueplayers", unique + 1);
//            plugin.saveConfig();
            // Broadcast new Unique player message to hub.
//            Bukkit.broadcastMessage(ChatColor.YELLOW + event.getPlayer().getDisplayName() + ChatColor.BLUE + " joined for the first time!" + ChatColor.GRAY + " (" + ChatColor.BLUE + unique + ChatColor.GRAY + ")");
//            event.getPlayer().sendMessage(" "); // Seperate between messages
        }

//        NOTE:: This has been removed due to the MOTD having lack of purpose and content.

//        if (player.hasPlayedBefore()) {
//            // Dispatch MOTD to user
//            List<String> motd = plugin.configurationManager.getmotd().getStringList("motd");
//            for (String s : motd) {
//                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', s));
//            }
//        }

        player.playSound(player.getLocation(), Arrays.asList(Sound.values()).get((int) (Math.random() * (Sound.values().length - 1))), 1f,1f); // Play random sound

        if (isVanished(player) == false) {
            Bukkit.broadcastMessage(ChatColor.GRAY + event.getPlayer().getDisplayName() + " joined.");
        }

        // Disable player collision.
        player.setCollidable(true);

        // If user has the fly permission, enable flight
        if (player.hasPermission("zanderhub.fly")) {
            player.setAllowFlight(true);
        }
    }

    private boolean isVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }
}
