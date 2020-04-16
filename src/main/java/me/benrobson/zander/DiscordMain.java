package me.benrobson.zander;

import lombok.Getter;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;

import javax.security.auth.login.LoginException;

public class DiscordMain extends ListenerAdapter implements Listener {
    String developmentprefix = ConfigurationManager.getConfig().getString("developmentprefix");
    @Getter private static DiscordMain instance;
    @Getter private JDA jda;
    @Getter private ZanderBungeeMain plugin;

    public DiscordMain(ZanderBungeeMain plugin) {
        instance = this;
        this.plugin = plugin;
//        plugin.getProxy().getPluginManager().registerListener(this, plugin);
        if (startBot()) {
//            setGame(Game.GameType.DEFAULT, plugin.getConfig().getString("discord.status"));
            registerDiscordEventListeners();
        }
    }

    private void registerDiscordEventListeners() {
        this.jda.addEventListener(this);
    }

    //
    // Start the Discord side bot
    //
    private boolean startBot() {
        try {
            // Build JDA/bot connection
            this.jda = new JDABuilder(AccountType.BOT).setToken(ConfigurationManager.getConfig().getString("discord.token")).build().awaitReady();
            // Show signs of life
            plugin.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', developmentprefix + " Zander is now connected to Discord.")));
            return true;
        } catch (LoginException | InterruptedException e) {
            plugin.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', developmentprefix + " Zander has encountered an error and can't login to Discord. The Discord Token may not be set, Discord integrations might not function.")));
        }
        return false;
    }
}
