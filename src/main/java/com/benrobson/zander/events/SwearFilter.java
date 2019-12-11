package com.benrobson.zander.events;

import com.benrobson.zander.ZanderMain;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

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
            if (message.toLowerCase().matches("^" + word + "$")){
                player.sendMessage(plugin.configurationManager.getlang().getString("punish.swearfilteringameprompt"));
                event.setCancelled(true);
            }
        }
    }
}
