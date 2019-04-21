package com.shadowolfyt.zander.commands;

import com.shadowolfyt.zander.guis.PunishGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static org.bukkit.Material.LEATHER_BOOTS;

public class punish implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("zander.punish")) {

            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Please specify a player to punish.");
                return true;
            }

            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "That player could not be located.");
                return true;
            } else {
                new PunishGUI(player);
                return true;
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
    }
}
