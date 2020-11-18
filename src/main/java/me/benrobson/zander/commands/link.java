package me.benrobson.zander.commands;

import me.benrobson.zander.ZanderBungeeMain;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class link extends Command {
    public link() {
        super("link");
    }
    private static ZanderBungeeMain plugin;

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;

            if (strings.length == 0 || strings.length < 32) {
                player.sendMessage(new TextComponent(ChatColor.RED + "Please provide a link code found in the registration email."));
                return;
            } else {
                //
                // Database Query
                // Check if the player is already linked.
                //
                try {
                    PreparedStatement findstatement = plugin.getConnection().prepareStatement("SELECT * FROM webaccounts where playerid = (select id from playerdata where username=?) AND registered = false;");
                    findstatement.setString(1, player.getDisplayName());
                    ResultSet results = findstatement.executeQuery();
                    if (results.next()) {
                        player.sendMessage(new TextComponent(ChatColor.RED + "This is a test."));
                    } else {
                        player.sendMessage(new TextComponent(ChatColor.RED + "This is another test."));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    player.sendMessage(new TextComponent(ChatColor.RED + "Database error, please try again later."));
                }
            }
            return;
        }
    }
}
