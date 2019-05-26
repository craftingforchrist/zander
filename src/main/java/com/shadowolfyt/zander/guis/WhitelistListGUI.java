package com.shadowolfyt.zander.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static org.bukkit.Material.AIR;

public class WhitelistListGUI implements Listener {
    Inventory inv = Bukkit.createInventory(null, 54, "Whitelisted Players");

    public WhitelistListGUI(Player player) {

        if (player == null) {
            return;
        }

        int slot = 0;
        for (OfflinePlayer all : Bukkit.getWhitelistedPlayers()) {
            ItemStack listedplayer = new ItemStack(Material.PLAYER_HEAD);
            ItemMeta listedplayermeta = listedplayer.getItemMeta();
            listedplayermeta.setDisplayName(all.getName());
            listedplayermeta.setLore(Arrays.asList("This player is whitelisted."));
            listedplayer.setItemMeta(listedplayermeta);
            inv.setItem(slot, listedplayer);
            slot += 1;
        }

        player.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equalsIgnoreCase("Whitelisted Players")) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == AIR || !event.getCurrentItem().hasItemMeta()) {
            player.closeInventory();
            return;
        }

        switch (event.getCurrentItem().getType()) {
            case PLAYER_HEAD:
                break;

            default:
                break;
        }
    }
}
