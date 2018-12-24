package com.shadowolf.zander.Events;

import com.connorlinfoot.titleapi.TitleAPI;
import com.shadowolf.zander.zander;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;

import java.time.LocalDate;

public class MerryChristmas implements Listener {

    zander plugin;

    public MerryChristmas(zander instance) {
        plugin = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        LocalDate localDate = LocalDate.now();

        if (localDate.getMonthValue() == 12 && localDate.getDayOfMonth() == 24) {
            TitleAPI.sendTitle(player,40,60,50,ChatColor.GREEN + "Merry " + ChatColor.RED + "Christmas!", player.getDisplayName() + " have a very Merry Christmas!");
            player.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + "[zander] " + ChatColor.RESET + "Merry Christmas " + ChatColor.BLUE + player.getDisplayName() + "!" + ChatColor.RESET + "\n\nHave a wonderful day with all your friends and family.\nRemember the reason for the season.\n\nFor to us a child is born, to us a son is given, and the government will be on his shoulders. And he will be called Wonderful Counselor, Mighty God, Everlasting Father, Prince of Peace. [Isaiah 9:6]");
            player.sendMessage("\n\n");
        } else {
            if(player.isOp()) {
                event.setJoinMessage(ChatColor.RED.toString() + ChatColor.BOLD + "[!!!] " + ChatColor.GOLD + "Server Operator " + player.getName() + " has joined the server");
            } else {
                event.setJoinMessage(ChatColor.YELLOW + player.getName() + " has joined the server");
            }
        }
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        LocalDate localDate = LocalDate.now();

        if (localDate.getMonthValue() == 12 && localDate.getDayOfMonth() == 24) {
            plugin.getConfig().set("motd" + "." + "line1", "&b&lzander &b&cM&ae&cr&ar&cy &aC&ch&ar&ci&as&ct&am&ca&as");
            plugin.getConfig().set("motd" + "." + "line2", "&6Remember the reason for the Season.");
            event.setMotd(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("motd.line1") + "\n" + plugin.getConfig().getString("motd.line2")));
        } else {
            plugin.getConfig().set("motd" + "." + "line1", "&b&lzander&r enabled server");
            plugin.getConfig().set("motd" + "." + "line2", "&eHermitCraft SMP Server.");
            event.setMotd(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("motd.line1") + "\n" + plugin.getConfig().getString("motd.line2")));
        }
    }
}
