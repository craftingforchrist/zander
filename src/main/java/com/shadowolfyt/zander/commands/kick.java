package com.shadowolfyt.zander.commands;

    import com.shadowolfyt.zander.ZanderMain;
    import org.bukkit.Bukkit;
    import org.bukkit.ChatColor;
    import org.bukkit.command.Command;
    import org.bukkit.command.CommandExecutor;
    import org.bukkit.command.CommandSender;
    import org.bukkit.entity.Player;
    import org.bukkit.event.EventHandler;

    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.text.SimpleDateFormat;
    import java.util.Date;

    import static org.bukkit.Bukkit.getServer;

public class kick implements CommandExecutor {
    ZanderMain plugin;

    public kick(ZanderMain instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("zander.kick")) {

            if (args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("punish.specifyplayerandreasoning")));
                return true;
            } else if (args.length == 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("punish.specifyreason")));
            } else {
                Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("punish.playernotonline")));
                    return true;
                }

                StringBuilder str = new StringBuilder();

                for (int i = 1; i < args.length; i++) {
                    str.append(args[i] + " ");
                }

                String kicker = "CONSOLE";
                if (sender instanceof Player) {
                    kicker = sender.getName();
                }

                for (Player p : Bukkit.getOnlinePlayers()){
                    if (p.hasPermission("zander.punishnotify")) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.punishmentprefix")) + " " + target.getDisplayName() + " has been kicked by " + kicker + " for " + str.toString().trim());
                    }
                }

                target.getPlayer().kickPlayer(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.pluginprefix")) + "\n" + ChatColor.YELLOW + "You have been kicked by " + ChatColor.RESET +  kicker + "\n" + "Reason: " + ChatColor.YELLOW +  str.toString().trim());

                //
                // Database Query
                // Add new punishment to database.
                //
                try {
                    PreparedStatement insertstatement = plugin.getConnection().prepareStatement("INSERT INTO punishments (punisheduser_id, punisher_id, punishtype, reason) values ((select id from playerdata where username = ?), (select id from playerdata where username = ?), 'KICK', ?);");

                    insertstatement.setString(1, target.getDisplayName());
                    insertstatement.setString(2, kicker);
                    insertstatement.setString(3, str.toString().trim());

                    insertstatement.executeUpdate();
                    plugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("main.developmentprefix")) + " " + target.getDisplayName() + " has been kicked. Adding punishment record to database.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
        return true;
    }
}
