package com.shadowolfyt.zander.events;

import com.connorlinfoot.titleapi.TitleAPI;
import com.shadowolfyt.zander.ZanderMain;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    }
}
