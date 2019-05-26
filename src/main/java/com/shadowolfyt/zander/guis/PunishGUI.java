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

import java.util.Arrays;

import static org.bukkit.Material.LEATHER_BOOTS;

public class PunishGUI implements Listener {

    ZanderMain plugin;
    Inventory inv = Bukkit.createInventory(null, 9, "Punishment GUI");

    Player player;
    Player target;

    public PunishGUI(Player player, Player target) {
        if (player == null || target == null) {
            return;
        }

        this.plugin = ZanderMain.plugin;

        this.player = player;
        this.target = target;

        // Listen
        plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

        // Kick
        ItemStack kick = new ItemStack(LEATHER_BOOTS);
        ItemMeta kickMeta = kick.getItemMeta();
        kickMeta.setDisplayName("Kick Player");
        kickMeta.setLore(Arrays.asList("Kick player from the Server."));
        kick.setItemMeta(kickMeta);
        inv.setItem(1, kick);

        player.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equalsIgnoreCase("Punishment GUI")) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getCurrentItem().hasItemMeta()) {
            player.closeInventory();
            return;
        }

        switch (event.getCurrentItem().getType()) {
            case LEATHER_BOOTS:
                player.closeInventory();
                this.target.kickPlayer(ChatColor.RED + "You have been kicked by an administrator: " + this.player.getDisplayName());
                break;

            default:
                break;
        }
    }
}
