package com.shadowolfyt.zander.events;

    import com.shadowolfyt.zander.ZanderMain;
    import net.md_5.bungee.api.ChatColor;
    import org.bukkit.entity.Player;
    import org.bukkit.event.EventHandler;
    import org.bukkit.event.Listener;

    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.Locale;

public class PlayerOnQuit implements Listener {
    ZanderMain plugin;

    public PlayerOnQuit(ZanderMain instance) {
        plugin = instance;
    }

    @EventHandler
    public void onQuit(org.bukkit.event.player.PlayerQuitEvent event){
        Player player = event.getPlayer();

        event.setQuitMessage("");
        if(player.isOp()) {
            event.setQuitMessage(ChatColor.GOLD + "Server Operator " + player.getName() + " has left the server");
        } else {
            event.setQuitMessage(ChatColor.YELLOW + player.getName() + " has left the server");
        }

        // Adds +1 to leaves in config.yml.
        int leaves = plugin.getConfig().getInt("players" + "." + player.getDisplayName() + ".leaves");
        plugin.getConfig().set("players" + "." + player.getDisplayName() + ".leaves", leaves + 1);
        plugin.saveConfig();

        // Logs last seen for player on logout.
        String pattern = "EEEEE MMMMM yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("EN", "AU"));
        String date = simpleDateFormat.format(new Date());

        plugin.getConfig().set("players" + "." + player.getDisplayName() + ".lastseen", date);
        plugin.saveConfig();

    }
}
