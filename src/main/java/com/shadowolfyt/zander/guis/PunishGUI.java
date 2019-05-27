package com.shadowolfyt.zander.guis;

import com.shadowolfyt.zander.ZanderMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.bukkit.Material.LEATHER_BOOTS;
import static org.bukkit.Material.PAPER;

public class PunishGUI implements Listener {

    ZanderMain plugin;
    Inventory inv = Bukkit.createInventory(null, 9, "Punish Player");

    Player player;
    Player target;

    public PunishGUI(Player player, Player target) {
        if (player == null || target == null) {
            return;
        }

        this.plugin = ZanderMain.plugin;
        this.player = player;
        this.target = target;

        plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

        // Kick
        // Chat: Spamming
        ItemStack kickspam = new ItemStack(PAPER);
        ItemMeta kickspamMeta = kickspam.getItemMeta();
        kickspamMeta.setDisplayName("Spamming");
        kickspamMeta.setLore(Arrays.asList("Chat Related Punishment: Spam"));
        kickspam.setItemMeta(kickspamMeta);
        inv.setItem(1, kickspam);
        player.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equalsIgnoreCase("Punish Player")) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getCurrentItem().hasItemMeta()) {
            player.closeInventory();
            return;
        }

        switch (event.getCurrentItem().getType()) {
            case PAPER:
                player.closeInventory();

                //
                // Database Query
                // Add new punishment to database.
                //
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                    Date date = new Date(System.currentTimeMillis());
                    String reason = "Spamming";

                    PreparedStatement insertstatement = plugin.getConnection().prepareStatement("INSERT INTO punishments (punisheduseruuid, punishedusername, punisheruuid, punisherusername, punishtype, reason, punishtimestamp) VALUES (?, ?, ?, ?, ?, ?, ?)");

                    insertstatement.setString(1, this.target.getUniqueId().toString());
                    insertstatement.setString(2, this.target.getDisplayName());
                    insertstatement.setString(3, player.getUniqueId().toString());
                    insertstatement.setString(4, player.getDisplayName());
                    insertstatement.setString(5, "KICK");
                    insertstatement.setString(6, reason);
                    insertstatement.setString(7, formatter.format(date));

                    insertstatement.executeUpdate();
                    plugin.getServer().getConsoleSender().sendMessage(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("developmentprefix")) + " " + target.getDisplayName() + " has been punished. Adding punishment record to database.");
                    this.target.kickPlayer(ChatColor.RED + "You have been kicked by " + player.getDisplayName() + "\n Reason: " + reason);

                    for (Player p : Bukkit.getOnlinePlayers()){
                        if (p.hasPermission("zander.punishnotify")) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix")) + " " + this.target.getDisplayName() + " has been kicked by " + player.getDisplayName() + " for " + reason);
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
    }
}
