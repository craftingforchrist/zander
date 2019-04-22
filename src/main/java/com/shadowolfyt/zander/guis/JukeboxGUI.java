package com.shadowolfyt.zander.guis;

import com.shadowolfyt.zander.ZanderMain;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static org.bukkit.Bukkit.getServer;

public class JukeboxGUI implements Listener {

    ZanderMain plugin;

    public JukeboxGUI(ZanderMain instance) {
        plugin = instance;
    }

    Inventory inv = Bukkit.createInventory(null, 27, "Jukebox Radio");

    public JukeboxGUI(Player player) {
        if (player == null) {
            return;
        }

        // 13
        ItemStack MUSICDISC13 = new ItemStack(Material.MUSIC_DISC_13);
        ItemMeta MUSICDISC13Meta = MUSICDISC13.getItemMeta();
        MUSICDISC13Meta.setDisplayName("Music Disc");
        MUSICDISC13.setItemMeta(MUSICDISC13Meta);
        inv.setItem(0, MUSICDISC13);

        // CAT
        ItemStack MUSICDISCCAT = new ItemStack(Material.MUSIC_DISC_CAT);
        ItemMeta MUSICDISCCATMeta = MUSICDISCCAT.getItemMeta();
        MUSICDISCCATMeta.setDisplayName("Music Disc");
        MUSICDISCCAT.setItemMeta(MUSICDISCCATMeta);
        inv.setItem(1, MUSICDISCCAT);

        // BLOCKS
        ItemStack MUSICDISCBLOCKS = new ItemStack(Material.MUSIC_DISC_BLOCKS);
        ItemMeta MUSICDISCBLOCKSMeta = MUSICDISCBLOCKS.getItemMeta();
        MUSICDISCBLOCKSMeta.setDisplayName("Music Disc");
        MUSICDISCBLOCKS.setItemMeta(MUSICDISCBLOCKSMeta);
        inv.setItem(2, MUSICDISCBLOCKS); // Change this when sound is imported.

        // CHIRP
        ItemStack MUSICDISCCHRIP = new ItemStack(Material.MUSIC_DISC_CHIRP);
        ItemMeta MUSICDISCCHRIPMeta = MUSICDISCCHRIP.getItemMeta();
        MUSICDISCCHRIPMeta.setDisplayName("Music Disc");
        MUSICDISCCHRIP.setItemMeta(MUSICDISCCHRIPMeta);
        inv.setItem(3, MUSICDISCCHRIP); // Change this when sound is imported.

        // FAR
        ItemStack MUSICDISCFAR = new ItemStack(Material.MUSIC_DISC_FAR);
        ItemMeta MUSICDISCFARMeta = MUSICDISCCHRIP.getItemMeta();
        MUSICDISCFARMeta.setDisplayName("Music Disc");
        MUSICDISCFAR.setItemMeta(MUSICDISCFARMeta);
        inv.setItem(4, MUSICDISCFAR); // Change this when sound is imported.


        // MALL
        ItemStack MUSICDISCMALL = new ItemStack(Material.MUSIC_DISC_MALL);
        ItemMeta MUSICDISCMALLMeta = MUSICDISCCHRIP.getItemMeta();
        MUSICDISCMALLMeta.setDisplayName("Music Disc");
        MUSICDISCMALL.setItemMeta(MUSICDISCMALLMeta);
        inv.setItem(5, MUSICDISCMALL);

        // MELLOHI
        ItemStack MUSICDISCMELLOHI = new ItemStack(Material.MUSIC_DISC_MELLOHI);
        ItemMeta MUSICDISCMELLOHIMeta = MUSICDISCMELLOHI.getItemMeta();
        MUSICDISCMELLOHIMeta.setDisplayName("Music Disc");
        MUSICDISCMELLOHI.setItemMeta(MUSICDISCMELLOHIMeta);
        inv.setItem(6, MUSICDISCMELLOHI);

        // STAL
        ItemStack MUSICDISCSTAL = new ItemStack(Material.MUSIC_DISC_STAL);
        ItemMeta MUSICDISCSTALMeta = MUSICDISCSTAL.getItemMeta();
        MUSICDISCSTALMeta.setDisplayName("Music Disc");
        MUSICDISCSTAL.setItemMeta(MUSICDISCSTALMeta);
        inv.setItem(7, MUSICDISCSTAL);

        // STRAD
        ItemStack MUSICDISCSTRAD = new ItemStack(Material.MUSIC_DISC_STRAD);
        ItemMeta MUSICDISCSTRADMeta = MUSICDISCSTRAD.getItemMeta();
        MUSICDISCSTRADMeta.setDisplayName("Music Disc");
        MUSICDISCSTRAD.setItemMeta(MUSICDISCSTRADMeta);
        inv.setItem(8, MUSICDISCSTRAD); // Change this when sound is imported.

        // WARD
        ItemStack MUSICDISCWARD = new ItemStack(Material.MUSIC_DISC_WARD);
        ItemMeta MUSICDISCWARDMeta = MUSICDISCWARD.getItemMeta();
        MUSICDISCWARDMeta.setDisplayName("Music Disc");
        MUSICDISCWARD.setItemMeta(MUSICDISCWARDMeta);
        inv.setItem(9, MUSICDISCWARD); // Change this when sound is imported.

        // 11
        ItemStack MUSICDISC11 = new ItemStack(Material.MUSIC_DISC_11);
        ItemMeta MUSICDISC11Meta = MUSICDISC11.getItemMeta();
        MUSICDISC11Meta.setDisplayName("Music Disc");
        MUSICDISC11.setItemMeta(MUSICDISC11Meta);
        inv.setItem(10, MUSICDISC11);

        // WAIT
        ItemStack MUSICDISCWAIT = new ItemStack(Material.MUSIC_DISC_WAIT);
        ItemMeta MUSICDISCWAITMeta = MUSICDISCWAIT.getItemMeta();
        MUSICDISCWAITMeta.setDisplayName("Music Disc");
        MUSICDISCWAIT.setItemMeta(MUSICDISCWAITMeta);
        inv.setItem(11, MUSICDISCWAIT); // Change this when sound is imported.

        // STOP SOUND
        ItemStack stopsound = new ItemStack(Material.CYAN_WOOL);
        ItemMeta stopsoundMeta = stopsound.getItemMeta();
        stopsoundMeta.setDisplayName("Stop Current Music");
        stopsoundMeta.setLore(Arrays.asList("Stop what is currently playing."));
        stopsound.setItemMeta(stopsoundMeta);
        inv.setItem(26, stopsound); // Change this when sound is imported.

        player.openInventory(inv);
    }

    @EventHandler
    public void onClick (InventoryClickEvent event) {
        if (!event.getInventory().getName().equalsIgnoreCase("Jukebox Player")) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == null || !event.getCurrentItem().hasItemMeta()) {
            player.closeInventory();
            return;
        }

        switch (event.getCurrentItem().getType()) {
            case MUSIC_DISC_CAT:
                player.playSound(player.getLocation(), Sound.MUSIC_DISC_CAT, 9999999, 1);
                player.closeInventory();
                break;

            case MUSIC_DISC_13:
                player.playSound(player.getLocation(), Sound.MUSIC_DISC_13, 9999999, 1);
                player.closeInventory();
                break;

            case MUSIC_DISC_BLOCKS:
                player.playSound(player.getLocation(), Sound.MUSIC_DISC_BLOCKS, 9999999, 1);
                player.closeInventory();
                break;

            case MUSIC_DISC_CHIRP:
                player.playSound(player.getLocation(), Sound.MUSIC_DISC_CHIRP, 9999999, 1);
                player.closeInventory();
                break;

            case MUSIC_DISC_FAR:
                player.playSound(player.getLocation(), Sound.MUSIC_DISC_FAR, 9999999, 1);
                player.closeInventory();
                break;

            case MUSIC_DISC_MALL:
                player.playSound(player.getLocation(), Sound.MUSIC_DISC_MALL, 9999999, 1);
                player.closeInventory();
                break;

            case MUSIC_DISC_MELLOHI:
                player.playSound(player.getLocation(), Sound.MUSIC_DISC_MELLOHI, 9999999, 1);
                player.closeInventory();
                break;

            case MUSIC_DISC_STAL:
                player.playSound(player.getLocation(), Sound.MUSIC_DISC_STAL, 9999999, 1);
                player.closeInventory();
                break;

            case MUSIC_DISC_STRAD:
                player.playSound(player.getLocation(), Sound.MUSIC_DISC_STRAD, 9999999, 1);
                player.closeInventory();
                break;

            case MUSIC_DISC_WARD:
                player.playSound(player.getLocation(), Sound.MUSIC_DISC_WARD, 9999999, 1);
                player.closeInventory();
                break;

            case MUSIC_DISC_11:
                player.playSound(player.getLocation(), Sound.MUSIC_DISC_11, 9999999, 1);
                player.closeInventory();
                break;

            case MUSIC_DISC_WAIT:
                player.playSound(player.getLocation(), Sound.MUSIC_DISC_WAIT, 9999999, 1);
                player.closeInventory();
                break;

            case CYAN_WOOL:
                getServer().dispatchCommand(Bukkit.getConsoleSender(), "stopsound " + player.getName());
                player.closeInventory();
                break;

            default:
                break;
        }
    }
}
