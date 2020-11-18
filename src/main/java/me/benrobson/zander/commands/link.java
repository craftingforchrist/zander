//package me.benrobson.zander.commands;
//
//import me.benrobson.zander.ZanderBungeeMain;
//import net.md_5.bungee.api.ChatColor;
//import net.md_5.bungee.api.CommandSender;
//import net.md_5.bungee.api.chat.TextComponent;
//import net.md_5.bungee.api.connection.ProxiedPlayer;
//import net.md_5.bungee.api.plugin.Command;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class link extends Command {
//    public link() {
//        super("link");
//    }
//    private static ZanderBungeeMain plugin;
//
//    @Override
//    public void execute(CommandSender commandSender, String[] strings) {
//        if (commandSender instanceof ProxiedPlayer) {
//            ProxiedPlayer player = (ProxiedPlayer) commandSender;
//
//            if (strings.length == 0) {
//                player.sendMessage(new TextComponent(ChatColor.RED + "Please provide your link code."));
//                return;
//            } else {
//                //
//                // Database Query
//                // Check if the player is already linked.
//                //
//                try {
//                    PreparedStatement findstatement = plugin.getConnection().prepareStatement("select * from webaccounts where playerid = (select id from playerdata where username=?');");
//                    findstatement.setString(1, player.getUniqueId().toString());
//                    ResultSet results = findstatement.executeQuery();
//                    if (results.next()) {
//                        player.sendMessage(new TextComponent(ChatColor.RED + "Your account is already linked."));
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    player.sendMessage(new TextComponent(ChatColor.RED + "Database Error, please try again later."));
//                }
//            }
//            return;
//        }
//    }
//}
