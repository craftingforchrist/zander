package com.shadowolfyt.zander.discord;

import com.shadowolfyt.zander.ZanderMain;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.security.auth.login.LoginException;

import static org.bukkit.Bukkit.getServer;

public class DiscordMain extends ListenerAdapter implements Listener {
    ZanderMain plugin;
    public JDA jda;
    public DiscordMain (ZanderMain instance) {
        plugin = instance;
        startBot();
        jda.addEventListener(this);

    }

    private void startBot() {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken(plugin.getConfig().getString("discord.token")).buildBlocking();
            getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "[Discord] " + ChatColor.GREEN + "Zander is now connected to Discord.");
            TextChannel textChannel = jda.getTextChannelsByName(plugin.getConfig().getString("discord.chatchannel"), true).get(0);
            textChannel.sendMessage("** :white_check_mark: Server has started **").queue();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "Zander has encountered an error and can't login to Discord. The Discord Token may not be set, discord integrations might not function.");
        }
    }

    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        TextChannel textChannel = jda.getTextChannelsByName(plugin.getConfig().getString("discord.chatchannel"), true).get(0);
        textChannel.sendMessage("**" + event.getPlayer().getName() + "**: " + message).queue();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        TextChannel textChannel = jda.getTextChannelsByName(plugin.getConfig().getString("discord.chatchannel"), true).get(0);
        textChannel.sendMessage(":heavy_plus_sign: **" + event.getPlayer().getName() + " joined the server **").queue();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        TextChannel textChannel = jda.getTextChannelsByName(plugin.getConfig().getString("discord.chatchannel"), true).get(0);
        textChannel.sendMessage(":heavy_minus_sign: **" + event.getPlayer().getName() + " left the server **").queue();
    }

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event) {
        TextChannel textChannel = jda.getTextChannelsByName(plugin.getConfig().getString("discord.chatchannel"), true).get(0);
        LivingEntity entity = event.getEntity();
        textChannel.sendMessage("** :skull: " + event.getDeathMessage() + " **").queue();
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.getAuthor().isFake() || event.isWebhookMessage()) return;
        String message = event.getMessage().getContentRaw();
        User user = event.getAuthor();
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("discord.chatprefix")) + " " + user.getName() + "#" + user.getDiscriminator() + ": " + message);
    }
}
