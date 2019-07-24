package com.shadowolfyt.zander.guis;

import com.shadowolfyt.zander.ZanderMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class DifficultyGUI implements Listener {
    ZanderMain plugin;

    public DifficultyGUI(ZanderMain instance) {
        plugin = instance;
    }

    Inventory inv = Bukkit.createInventory(null, 9, "Difficulty Manager");

    public DifficultyGUI(Player player) {
        if (player == null) {
            return;
        }

        ItemStack diffpeaceful = new ItemStack(Material.FEATHER);
        ItemMeta diffpeacefulMeta = diffpeaceful.getItemMeta();
        diffpeacefulMeta.setDisplayName("Peaceful");
        diffpeacefulMeta.setLore(Arrays.asList("Turn the server global difficulty to Peaceful."));
        diffpeaceful.setItemMeta(diffpeacefulMeta);
        inv.setItem(1, diffpeaceful);

        ItemStack diffeasy = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta diffeasyMeta = diffeasy.getItemMeta();
        diffeasyMeta.setDisplayName("Easy");
        diffeasyMeta.setLore(Arrays.asList("Turn the server global difficulty to Easy."));
        diffeasy.setItemMeta(diffeasyMeta);
        inv.setItem(3, diffeasy);

        ItemStack diffnormal = new ItemStack(Material.IRON_SWORD);
        ItemMeta diffnormalMeta = diffnormal.getItemMeta();
        diffnormalMeta.setDisplayName("Normal");
        diffnormalMeta.setLore(Arrays.asList("Turn the server global difficulty to Normal."));
        diffnormal.setItemMeta(diffnormalMeta);
        inv.setItem(5, diffnormal);

        ItemStack diffhard = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta diffhardMeta = diffhard.getItemMeta();
        diffhardMeta.setDisplayName("Hard");
        diffhardMeta.setLore(Arrays.asList("Turn the server global difficulty to Hard."));
        diffhard.setItemMeta(diffhardMeta);
        inv.setItem(7, diffhard);

        player.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equalsIgnoreCase("Difficulty Manager")) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getCurrentItem().hasItemMeta()) {
            player.closeInventory();
            return;
        }

        switch (event.getCurrentItem().getType()) {
            // Peaceful
            case FEATHER:
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.pluginprefix")) + " Server difficulty has been set to Peaceful.");
                player.getWorld().setDifficulty(Difficulty.PEACEFUL);
                player.closeInventory();
                break;

            // Easy
            case WOODEN_SWORD:
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.pluginprefix")) + " Server difficulty has been set to Easy.");
                player.getWorld().setDifficulty(Difficulty.EASY);
                player.closeInventory();
                break;

            // Normal
            case IRON_SWORD:
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.pluginprefix")) + " Server difficulty has been set to Normal.");
                player.getWorld().setDifficulty(Difficulty.NORMAL);
                player.closeInventory();
                break;

            // Hard
            case DIAMOND_SWORD:
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.pluginprefix")) + " Server difficulty has been set to Hard.");
                player.getWorld().setDifficulty(Difficulty.HARD);
                player.closeInventory();
                break;

            default:
                break;
        }
    }
}
