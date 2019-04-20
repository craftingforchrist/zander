package com.shadowolfyt.zander.discord;

import com.shadowolfyt.zander.ZanderMain;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.Listener;

import javax.security.auth.login.LoginException;

import static org.bukkit.Bukkit.getServer;

public class DiscordMain implements Listener {
    ZanderMain plugin;
    public JDA jda;
    public DiscordMain (ZanderMain instance) {
        plugin = instance;
        startBot();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private void startBot() {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken(plugin.getConfig().getString("discord.token")).buildBlocking();
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Zander is now connected to Discord.");
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
