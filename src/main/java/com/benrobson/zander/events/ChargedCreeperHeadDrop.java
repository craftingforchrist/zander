package com.benrobson.zander.events;

import com.benrobson.zander.ZanderMain;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ChargedCreeperHeadDrop implements Listener {
    ZanderMain plugin;

    public ChargedCreeperHeadDrop(ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void ChargedCreeperHeadDrop(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity target = event.getEntity();

        if (damager.getType() != EntityType.CREEPER) return;
        if (target.getType() != EntityType.PLAYER) return;

        Player player = (Player) target;
        Creeper creeper = (Creeper) damager;

        if (player.getHealth() - event.getFinalDamage() > 0) return;
        if (!creeper.isPowered()) return;

        ItemStack ExplPlayer = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta ExplPlayerMeta = (SkullMeta) ExplPlayer.getItemMeta();
        ExplPlayerMeta.setOwningPlayer(player);
        ExplPlayerMeta.setDisplayName(player.getDisplayName() + "'s Skull");
        ExplPlayer.setItemMeta(ExplPlayerMeta);

        event.getEntity().getLocation().getWorld().dropItem(event.getEntity().getLocation(), new ItemStack(ExplPlayer));
    }
}
