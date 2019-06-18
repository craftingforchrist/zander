package com.shadowolfyt.zander.events;

import com.shadowolfyt.zander.ZanderMain;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

import static org.apache.commons.lang.StringUtils.containsIgnoreCase;

public class SwearFilter implements Listener {
    private ZanderMain plugin;
    public SwearFilter(ZanderMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void SwearFilter(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        List<String> FilteredWords = plugin.configurationManager.getfilter().getStringList("filteredwords");
        String message = event.getMessage();

        for (String word: FilteredWords){
            if (containsIgnoreCase(message, word)){
                player.sendMessage(ChatColor.RED + "You cannot speak that foul language!");
                event.setCancelled(true);
                break;
            }
        }
    }
}
