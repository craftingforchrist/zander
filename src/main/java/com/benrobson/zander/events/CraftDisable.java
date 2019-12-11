package com.benrobson.zander.events;

import com.benrobson.zander.ZanderMain;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class CraftDisable implements Listener {
    ZanderMain plugin;

    public CraftDisable(ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void CraftDisable(CraftItemEvent event) {
        Material itemType = event.getRecipe().getResult().getType();
        Byte itemData = event.getRecipe().getResult().getData().getData();

        if (event.getRecipe().getResult() == null) return;

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
