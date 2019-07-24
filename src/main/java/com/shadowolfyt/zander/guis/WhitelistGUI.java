package com.shadowolfyt.zander.guis;

import com.shadowolfyt.zander.ZanderMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class WhitelistGUI implements Listener {
    ZanderMain plugin;

    public WhitelistGUI (ZanderMain instance) {
        plugin = instance;
    }

    Inventory inv = Bukkit.createInventory(null, 9, "Whitelist Manager");

    public WhitelistGUI(Player player) {
        if (player == null) {
            return;
        }

        ItemStack on = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta onMeta = on.getItemMeta();
        onMeta.setDisplayName(ChatColor.GREEN + "Enable Whitelist");
        onMeta.setLore(Arrays.asList("Enable the whitelist to limit players."));
        on.setItemMeta(onMeta);
        inv.setItem(2, on);

        ItemStack off = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta offMeta = off.getItemMeta();
        offMeta.setDisplayName(ChatColor.GREEN + "Disable Whitelist");
        offMeta.setLore(Arrays.asList("Disable the whitelist so that all players can join."));
        off.setItemMeta(offMeta);
        inv.setItem(6, off);

        ItemStack list = new ItemStack(Material.PAPER);
        ItemMeta listMeta = list.getItemMeta();
        listMeta.setDisplayName(ChatColor.GREEN + "Whitelisted Players");
        listMeta.setLore(Arrays.asList("List all whitelisted players."));
        list.setItemMeta(listMeta);
        inv.setItem(4, list);

        player.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equalsIgnoreCase("Whitelist Manager")) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getCurrentItem().hasItemMeta()) {
            player.closeInventory();
            return;
        }

        switch (event.getCurrentItem().getType()) {
            // Enable
            case EMERALD_BLOCK:
                Bukkit.setWhitelist(true);
                player.sendMessage(ChatColor.GREEN + "You have enabled the whitelist.");
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.pluginprefix")) + " The server whitelist has been enabled.");
                break;

            // Disable
            case REDSTONE_BLOCK:
                Bukkit.setWhitelist(false);
                player.sendMessage(ChatColor.RED + "You have disabled the whitelist.");
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.pluginprefix")) + " The server whitelist has been disabled.");
                break;

            // List
            case PAPER:
                new WhitelistListGUI(player);
                break;

            default:
                break;
        }
    }
}
