package com.shadowolfyt.zander.events;

import com.connorlinfoot.titleapi.TitleAPI;
import com.shadowolfyt.zander.ZanderMain;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static org.bukkit.Bukkit.getServer;

public class PlayerOnJoin implements Listener {
    ZanderMain plugin;

    public PlayerOnJoin (ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        TitleAPI.sendTitle(player,40,50,40,"Welcome " + ChatColor.AQUA + player.getDisplayName(),ChatColor.GOLD + "Enjoy your stay!");
        TitleAPI.sendTabTitle(player, plugin.getConfig().getString("tabtitle.header"),plugin.getConfig().getString("tabtitle.footer"));

        event.setJoinMessage("");
        // New User Joins for first time.
        if (!player.hasPlayedBefore()) {
            Firework firework = (Firework) event.getPlayer().getWorld().spawn(event.getPlayer().getLocation(), Firework.class);
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
            event.setJoinMessage(ChatColor.LIGHT_PURPLE + player.getDisplayName() + ChatColor.YELLOW + " has joined for the first time!");
        } else {
            // Join Chat Message.
            if(player.isOp()) {
                event.setJoinMessage(ChatColor.RED.toString() + ChatColor.BOLD + "[!!!] " + ChatColor.GOLD + "Server Operator " + player.getName() + " has joined the server");
            } else {
                event.setJoinMessage(ChatColor.YELLOW + player.getName() + " has joined the server");
            }
        }

        // Adding join information to YML file.
        if (!player.hasPlayedBefore()){
            plugin.getConfig().set("players" + "." + player.getDisplayName() + ".joins", 0);
            plugin.getConfig().set("players" + "." + player.getDisplayName() + ".leaves", 0);
            plugin.getConfig().set("players" + "." + player.getDisplayName() + ".deaths", 0);
            plugin.getConfig().set("players" + "." + player.getDisplayName() + ".lastseen", null);
            plugin.getConfig().set("players" + "." + player.getDisplayName() + ".uuid", null);
            plugin.getConfig().set("players" + "." + player.getDisplayName() + ".ipaddress", null);
        }

        int joined = plugin.getConfig().getInt("players" + "." + player.getDisplayName() + ".joins");
        plugin.getConfig().set("players" + "." + player.getDisplayName() + ".joins", joined + 1);
        plugin.saveConfig();

        // Create UUID field in config.
        String playerUUID = player.getPlayer().getUniqueId().toString();
        plugin.getConfig().set("players" + "." + player.getDisplayName() + ".uuid", playerUUID);
        plugin.saveConfig();

        // Create IP Address field config.
        String ip = player.getPlayer().getAddress().toString().replaceAll("/", "");
        plugin.getConfig().set("players" + "." + player.getDisplayName() + ".ipaddress", ip);
        plugin.saveConfig();

        // Changes Last Seen to Currently Online.
        plugin.getConfig().set("players" + "." + player.getDisplayName() + ".lastseen", ChatColor.GREEN.toString() + ChatColor.BOLD + "Currently Online" + ChatColor.RESET);
        plugin.saveConfig();

        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int number = random.nextInt(4) + 1;

                if (number == 1) {
                    Bukkit.broadcastMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "[zander] " + ChatColor.RESET + "Have you joined this Discord server? If you haven't you should totally join: " + ChatColor.BLUE + "https://bit.ly/mancavediscord");
                } else if (number == 2) {
                    Bukkit.broadcastMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "[zander] " + ChatColor.RESET + "A new cube has formed at " +  ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD +  "-240 -750" +  ChatColor.RESET + ". More information to come soon..");
                } else if (number == 3) {
                    Bukkit.broadcastMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "[zander] " + ChatColor.RESET + "To see what is happening in the development of zander, check out the Open Source GitHub Repository here: " + ChatColor.RED + "https://github.com/shadowolfyt/zander");
                }
            }
        }, 300, 25000);
    }
}
