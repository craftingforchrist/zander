package com.shadowolfyt.zander.commands;

import com.shadowolfyt.zander.ZanderMain;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class freeze implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("zander.freeze")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        if(args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /freeze <player>");
            return true;
        }

        Player resolve = Bukkit.getPlayer(args[0]);
        if(resolve == null) {
            sender.sendMessage(ChatColor.YELLOW + "Player " + args[0] + " not found.");
            return true;
        }

        Freezer f = getFreezer(resolve);
        f.setFrozen(!f.isFrozen());

        if (f.isFrozen()) {
            sender.sendMessage(ChatColor.YELLOW + resolve.getName() + " was frozen.");
        } else {
            sender.sendMessage(ChatColor.YELLOW + resolve.getName() + " is no longer frozen.");
        }
        return true;
    }

    private static Map<UUID, Freezer> frozenPlayers = new HashMap<>();

    private Freezer getFreezer(Player p) {
        if(frozenPlayers.containsKey(p.getUniqueId())) {
            return frozenPlayers.get(p.getUniqueId());
        } else {
            return new Freezer(p, false);
        }
    }

    public class Freezer implements Listener {
        /**
         * Just in case the user logs out... I'm not sure if a different player object is given.
         */
        @Getter
        private UUID user;
        @Getter
        private boolean frozen;

        private Freezer(Player p, boolean freeze) {
            if(frozenPlayers.containsKey(p.getUniqueId())) {
                throw new IllegalArgumentException("Cannot create another freezer for " + p.getUniqueId());
            }

            this.user = p.getUniqueId();
            this.frozen = freeze;

            ZanderMain.plugin.getServer().getPluginManager().registerEvents(this, ZanderMain.plugin);

            frozenPlayers.put(p.getUniqueId(), this);
        }

        public Player resolvePlayer() {
            return Bukkit.getPlayer(this.user);
        }

        public void setFrozen(boolean f) {
            this.frozen = f;

            // Fly so they aren't glitching mid air.
            this.resolvePlayer().setFlying(f);

            this.resolvePlayer().sendMessage(f ? ChatColor.RED + "You have been frozen!" : ChatColor.GREEN + "You have been unfrozen!");
            this.resolvePlayer().sendTitle(f ? ChatColor.RED + "You have been frozen!" : ChatColor.GREEN + "You have been unfrozen!", "");
        }

        @EventHandler
        public void move(PlayerMoveEvent e) {
            if(e.getPlayer().getUniqueId().equals(this.user)) {
                // Allow moving head.
                if((e.getFrom().getX() == e.getTo().getX()) && (e.getFrom().getY() == e.getTo().getY()) && (e.getFrom().getZ() == e.getTo().getZ())) {
                    return;
                }
                // Check if it they are frozen and freeze them
                if(this.frozen) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "You are frozen!");
                }
            }
        }

        @EventHandler
        public void flightToggle(PlayerToggleFlightEvent e) {
            e.getPlayer().setFlying(this.isFrozen());
        }
    }
}
