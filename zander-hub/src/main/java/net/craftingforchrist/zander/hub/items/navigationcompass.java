package net.craftingforchrist.zander.hub.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class navigationcompass implements Listener {
    public static void givecompass(Player player) {
        ItemStack navcompass = new ItemStack(Material.COMPASS);
        ItemMeta meta = navcompass.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + ChatColor.BOLD.toString() + "Navigation Compass");

        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.YELLOW + "Right Click me to access Servers");
        meta.setLore(lore);
        navcompass.setItemMeta(meta);

        player.getInventory().setItem(4, navcompass); // Put the compass into the players middle hand
    }
}
