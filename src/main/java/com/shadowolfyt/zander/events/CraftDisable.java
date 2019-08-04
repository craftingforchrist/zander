package com.shadowolfyt.zander.events;

import com.shadowolfyt.zander.ZanderMain;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class CraftDisable implements Listener {
    ZanderMain plugin;

    public CraftDisable(ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void CraftDisable(PrepareItemCraftEvent event) {
        Material itemType = event.getRecipe().getResult().getType();
        Byte itemData = event.getRecipe().getResult().getData().getData();

        if (itemType == Material.END_CRYSTAL) {
            event.getInventory().setResult(new ItemStack(Material.AIR));
            for (HumanEntity he : event.getViewers()) {
                if (he instanceof Player) {
                    ((Player) he).sendMessage(ChatColor.RED + "You cannot craft this!");
                    return;
                }
                return;
            }
        }
    }
}
