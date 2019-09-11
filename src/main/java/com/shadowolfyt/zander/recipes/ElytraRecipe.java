package com.shadowolfyt.zander.recipes;

import com.shadowolfyt.zander.ZanderMain;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class ElytraRecipe implements Listener {
    ZanderMain plugin;
    public ElytraRecipe(ZanderMain instance) {
        plugin = instance;
    }

    public void ElytraRecipe() {
        ItemStack item = new ItemStack(Material.ELYTRA, 1);
        NamespacedKey key = new NamespacedKey(plugin, "Elytra");
        ShapedRecipe er = new ShapedRecipe(key, item);

        er.shape("@%@","@#@","@$@");
        er.setIngredient('@', Material.PHANTOM_MEMBRANE);
        er.setIngredient('#', Material.DIAMOND_CHESTPLATE);
        er.setIngredient('$', Material.NETHER_STAR);
        er.setIngredient('%', Material.ENDER_EYE);
        plugin.getServer().addRecipe(er);
    }
}
