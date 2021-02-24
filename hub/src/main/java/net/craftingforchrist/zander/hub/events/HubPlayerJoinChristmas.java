package net.craftingforchrist.zander.hub.events;

import net.craftingforchrist.zander.hub.ZanderHubMain;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Calendar;
import java.util.Date;

public class HubPlayerJoinChristmas implements Listener {
    ZanderHubMain plugin;
    public HubPlayerJoinChristmas(ZanderHubMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void HubPlayerJoinChristmas(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (isChristmasOccasion() == true) {
            // Send player a title to their screen.
            player.sendTitle(ChatColor.GREEN + "Merry " + ChatColor.RED + "Christmas!", player.getDisplayName() + " have a very Merry Christmas!", 10, 60, 10);

            // Send player a message to their chat.
            player.sendMessage(ChatColor.WHITE + ChatColor.BOLD.toString() + "============= " + ChatColor.GREEN + ChatColor.BOLD.toString()+ "Merry " + ChatColor.RED + ChatColor.BOLD.toString() + "Christmas" + ChatColor.WHITE + ChatColor.BOLD.toString() + " =============");
            player.sendMessage("Merry Christmas " + ChatColor.GREEN + player.getDisplayName() + "!");
            player.sendMessage("Have a wonderful day with all your friends and family. Remember the reason for the season.");
            player.sendMessage("For to us a child is born, to us a son is given, and the government will be on his shoulders. And he will be called Wonderful Counselor, Mighty God, Everlasting Father, Prince of Peace.");
            player.sendMessage(ChatColor.AQUA + "Isaiah 9:6 // New International Version (NIV)");
        }
    }

    public boolean isChristmasOccasion() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        int month = calendar.get(Calendar.MONTH);
        int monthplus = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (monthplus == 12 && day == 24 || monthplus == 12 && day == 25) {
            return true;
        } else {
            return false;
        }
    }
}
