package com.benrobson.zander.recipes;

import com.benrobson.zander.ZanderMain;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class RabbitSkinRecipe implements Listener {
    ZanderMain plugin;
    public RabbitSkinRecipe(ZanderMain instance) {
        plugin = instance;
    }

    public void RabbitSkinRecipe() {
        ItemStack item = new ItemStack(Material.RABBIT_HIDE, 1);
        NamespacedKey key = new NamespacedKey(plugin, "RabbitHide");
        ShapedRecipe rsr = new ShapedRecipe(key, item);

        rsr.shape("$$$","$$$","$$$");
        rsr.setIngredient('$', Material.ROTTEN_FLESH);
        plugin.getServer().addRecipe(rsr);
    }
}
