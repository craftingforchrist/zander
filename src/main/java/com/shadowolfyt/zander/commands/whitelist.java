package com.shadowolfyt.zander.commands;

import com.shadowolfyt.zander.guis.WhitelistGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class whitelist implements CommandExecutor {
    private final String EN_ERR_PLAYER_DOES_NOT_EXIST = ChatColor.RED + "Player does not exist!";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("zander.whitelist")) {
            // No permissions.
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        // Sender has 'zander.whitelist'

        if (args.length == 0 && (sender instanceof Player)) {
            new WhitelistGUI((Player) sender);
            return true;
        } else if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "The GUI is only available for players only.");
            return true;
        }

        // args != 0

        if (args[0].equalsIgnoreCase("add")) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

            if (target != null) {
                if (target.isWhitelisted()) {
                    sender.sendMessage(ChatColor.RED + "This player is already whitelist.");
                } else {
                    target.setWhitelisted(true);
                    Bukkit.getServer().reloadWhitelist();
                    Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + target.getName() + " has been whitelisted.");
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 100000, 1);
                    }
                }
            } else {
                sender.sendMessage(EN_ERR_PLAYER_DOES_NOT_EXIST);
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("remove")) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

            if (target != null) {
                if (!target.isWhitelisted()) {
                    sender.sendMessage(ChatColor.RED + "This player is not in the whitelist.");

                } else {
                    target.setWhitelisted(false);
                    Bukkit.getServer().reloadWhitelist();
                    Bukkit.broadcastMessage(ChatColor.RED + target.getName() + " has been removed from the whitelist.");
                }
            } else {
                sender.sendMessage(EN_ERR_PLAYER_DOES_NOT_EXIST);
            }


        } else if (args[0].equalsIgnoreCase("list")) {
            sender.sendMessage(ChatColor.YELLOW + "Currently whitelisted: ");
            Bukkit.getServer().getWhitelistedPlayers().forEach(p -> sender.sendMessage(p.getPlayer().getDisplayName()));
            return true;
        }
        return true;
    }
}
