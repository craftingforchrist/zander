package com.shadowolfyt.zander.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SwearFilter implements Listener {
    @EventHandler
    public void SwearFilter(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        String[] FilteredWords = {"anal", "anus", "arse", "ass", "motherfucker", "balls", "bastard", "bitch", "blowjob", "blow job", "buttplug","cock","coon","cunt","dildo","fag","dyke","fuck","fucking","nigger","Goddamn","jizz","nigga","pussy","shit","whore"};
        String[] message = event.getMessage().split(" ");

        for(int i = 0;i < message.length;i++){
            for(int b = 0; b < FilteredWords.length;b++){
                if (message[i].equalsIgnoreCase(FilteredWords[b])) {
                    player.sendMessage(ChatColor.RED + "You cannot speak that foul language!");
                    event.setCancelled(true);
                }
            }
        }
    }
}
