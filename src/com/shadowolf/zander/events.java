package com.shadowolf.zander;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class events implements Listener {
    @EventHandler
    public void onMove (PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Inventory pInv = player.getInventory();
        ItemStack item = new ItemStack(Material.GOLDEN_APPLE, 1);

        player.sendMessage(ChatColor.RED + "You are moving..");
        pInv.addItem(item);
    }
}
