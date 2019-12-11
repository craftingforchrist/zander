package com.benrobson.zander.recipes;

import com.benrobson.zander.ZanderMain;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class ExperienceBottleRecipe implements Listener {
    ZanderMain plugin;
    public ExperienceBottleRecipe(ZanderMain instance) {
        plugin = instance;
    }

    public void ExperienceBottleRecipe() {
        ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
        NamespacedKey key = new NamespacedKey(plugin, "ExperienceBottle");
        ShapedRecipe ebr = new ShapedRecipe(key, item);

        ebr.shape("$@$","$@$","$#$");
        ebr.setIngredient('$', Material.QUARTZ);
        ebr.setIngredient('@', Material.GLOWSTONE_DUST);
        ebr.setIngredient('#', Material.POTION);
        plugin.getServer().addRecipe(ebr);
    }
}
