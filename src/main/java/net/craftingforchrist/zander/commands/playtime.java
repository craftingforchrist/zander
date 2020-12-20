package net.craftingforchrist.zander.commands;

import net.craftingforchrist.zander.ZanderBungeeMain;
import net.craftingforchrist.zander.utilities.IntervalFormat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class playtime extends Command {
    public playtime() {
        super("playtime");
    }
    private static ZanderBungeeMain plugin;

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;

            if (args.length == 0) {
                //
                // Database Query
                // Grab the player that executes the command and their playtime in seconds.
                //
                try {
                    PreparedStatement findplaytimestatement = plugin.getInstance().getConnection().prepareStatement("select sum(TIME_TO_SEC(timediff(gamesessions.sessionend, gamesessions.sessionstart))) as 'totaltimeplayed' from gamesessions left join playerdata on playerdata.id = gamesessions.playerid where playerdata.username=?;");
                    findplaytimestatement.setString(1, player.getDisplayName());
                    ResultSet results = findplaytimestatement.executeQuery();

                    if (results.next()) {
                        int totaltimeplayed = results.getInt("totaltimeplayed");
                        player.sendMessage(new TextComponent(ChatColor.GRAY + "You have played for " + ChatColor.YELLOW + IntervalFormat.format(totaltimeplayed)));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return;
            } else if (args.length == 1) {
                //
                // Database Query
                // Check the database to see if the args matches to a player that has logged in.
                //
                try {
                    PreparedStatement findplayerstatement = plugin.getInstance().getConnection().prepareStatement("select username from playerdata where username=?;");
                    findplayerstatement.setString(1, args[0]);
                    ResultSet findplayerresults = findplayerstatement.executeQuery();

                    if (findplayerresults.next()) {
                        //
                        // Database Query
                        // Grab the mentioned player and their playtime in seconds.
                        //
                        try {
                            PreparedStatement findplaytimestatement = plugin.getInstance().getConnection().prepareStatement("select sum(TIME_TO_SEC(timediff(gamesessions.sessionend, gamesessions.sessionstart))) as 'totaltimeplayed' from gamesessions left join playerdata on playerdata.id = gamesessions.playerid where playerdata.username=?;");
                            findplaytimestatement.setString(1, args[0]);
                            ResultSet results = findplaytimestatement.executeQuery();

                            if (results.next()) {
                                int totaltimeplayed = results.getInt("totaltimeplayed");
                                player.sendMessage(new TextComponent(ChatColor.GRAY + args[0] + " has played for " + ChatColor.YELLOW + IntervalFormat.format(totaltimeplayed)));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        player.sendMessage(new TextComponent(ChatColor.RED + "The requested player does not exist or has not logged in."));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return;
        }
    }

//    @Override
//    public Iterable<String> onTabComplete(CommandSender commandSender, String[] args) {
//        if (args.length > 2 || args.length == 0 ) {
//            return ImmutableSet.of();
//        }
//
//        //
//        // Database Query
//        // Grab all players username from the database.
//        //
//        try {
//            PreparedStatement findplayersstatement = plugin.getInstance().getConnection().prepareStatement("select username from playerdata order by username asc;");
//            ResultSet results = findplayersstatement.executeQuery();
//
//            if (results.next()) {
//                Set<String> matches = new HashSet<>();
//                if (args.length == 1) {
//                    while (results.next()) {
//                        if (args[0].startsWith(args[0])) {
//                            matches.add(results.getString("username"));
//                        }
//                    }
//                }
//                return matches;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}