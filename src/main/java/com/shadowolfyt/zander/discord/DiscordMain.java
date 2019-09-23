package com.shadowolfyt.zander.discord;

import com.shadowolfyt.zander.ZanderMain;
import com.shadowolfyt.zander.discord.moderation.LinkFilter;
import com.shadowolfyt.zander.discord.moderation.SwearFilter;
import lombok.Getter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import javax.security.auth.login.LoginException;

import static org.bukkit.Bukkit.getServer;

public class DiscordMain extends ListenerAdapter implements Listener {
    @Getter private static DiscordMain instance;
    @Getter private JDA jda;
    @Getter private ZanderMain plugin;

    public DiscordMain(ZanderMain plugin) {
        instance = this;
        this.plugin = plugin;

        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);

        if (startBot()) {
            setGame(Game.GameType.DEFAULT, plugin.getConfig().getString("discord.status"));
            registerDiscordEventListeners();
        }
    }

    private void registerDiscordEventListeners() {
        this.jda.addEventListener(this);
        this.jda.addEventListener(new SwearFilter(plugin));
        this.jda.addEventListener(new LinkFilter(plugin));
    }

    /**
     * Starts the Discord connection via JDA library
     * <p>
     * Blocks the current thread until the connection to Discord is successful.
     * <p>
     * See JDA#awaitReady
     *
     * @return True if connection is successful
     */
    private boolean startBot() {
        try {
            // Build JDA/bot connection
            this.jda = new JDABuilder(AccountType.BOT).setToken(plugin.getConfig().getString("discord.token")).build().awaitReady();
            jda.getPresence().setGame(Game.playing(this.plugin.getConfig().getString("discord.status")));

            // Show signs of life
            getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "[Discord] " + ChatColor.GREEN + "Zander is now connected to Discord.");
            TextChannel textChannel = this.jda.getTextChannelsByName(this.plugin.getConfig().getString("discord.chatchannel"),true).get(0);
            textChannel.sendMessage("** :white_check_mark: Server has started **").queue();

            return true;
        } catch (LoginException | InterruptedException e) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "Zander has encountered an error and can't login to Discord. The Discord Token may not be set, discord integrations might not function.");
        }
        return false;
    }

    //
    // Bridge Chat Handler
    //
    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        TextChannel textChannel = jda.getTextChannelsByName(plugin.getConfig().getString("discord.chatchannel"), true).get(0);
        textChannel.sendMessage("**" + event.getPlayer().getName() + "**: " + message).queue();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.getAuthor().isFake() || event.isWebhookMessage()) return;
        MessageChannel channel = event.getChannel();
        TextChannel textChannel = jda.getTextChannelsByName(plugin.getConfig().getString("discord.chatchannel"), true).get(0);

        if (channel == textChannel) {
            String message = event.getMessage().getContentRaw();
            User user = event.getAuthor();
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.configurationManager.getlang().getString("discord.chatprefix")) + " " + user.getName() + "#" + user.getDiscriminator() + ": " + message);
        }
    }

    //
    // Player Join
    //
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            TextChannel textChannel = jda.getTextChannelsByName(plugin.getConfig().getString("discord.chatchannel"), true).get(0);
            textChannel.sendMessage(":tada: **" + event.getPlayer().getName() + " joined for the first time! **").queue();
        } else {
            TextChannel textChannel = jda.getTextChannelsByName(plugin.getConfig().getString("discord.chatchannel"), true).get(0);
            textChannel.sendMessage(":heavy_plus_sign: **" + event.getPlayer().getName() + " joined the server **").queue();
        }
    }

    //
    // Player Leave
    //
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        TextChannel textChannel = jda.getTextChannelsByName(plugin.getConfig().getString("discord.chatchannel"), true).get(0);
        textChannel.sendMessage(":heavy_minus_sign: **" + event.getPlayer().getName() + " left the server **").queue();
    }

    //
    // Player Death
    //
    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event) {
        TextChannel textChannel = jda.getTextChannelsByName(plugin.getConfig().getString("discord.chatchannel"), true).get(0);
        LivingEntity entity = event.getEntity();
        textChannel.sendMessage("** :skull: " + event.getDeathMessage() + " **").queue();
    }

    //
    // Player Advancement Event
    //
    @EventHandler
//    public void PlayerAdvancementDoneEvent (PlayerAdvancementDoneEvent event) {
//        if (event.getAdvancement() == null || event.getAdvancement().getKey().getKey().contains("recipe/") || event.getPlayer() == null) return;
//
//        JsonObject obj = new Gson().fromJson(ClassLoader.getSystemResourceAsStream("assets/minecraft/lang/en_us.json"), JsonObject.class);
//
//        try {
//            Object craftAdvancement = ((Object) event.getAdvancement()).getClass().getMethod("getHandle").invoke(event.getAdvancement());
//            Object advancementDisplay = craftAdvancement.getClass().getMethod("c").invoke(craftAdvancement);
//            boolean display = (boolean) advancementDisplay.getClass().getMethod("i").invoke(advancementDisplay);
//            if (!display) return;
//        } catch (NullPointerException e) {
//            return;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return;
//        }
//
//        String rawAdvancementName = event.getAdvancement().getKey().getKey();
//        String advancementName = Arrays.stream(rawAdvancementName.substring(rawAdvancementName.lastIndexOf("/") + 1).toLowerCase().split("_"))
//                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
//                .collect(Collectors.joining(" "));
//
//        TextChannel textChannel = jda.getTextChannelsByName(plugin.getConfig().getString("discord.chatchannel"), true).get(0);
//        textChannel.sendMessage("** :medal: " + event.getPlayer().getDisplayName() + " gained the advancement: " + advancementName + " **").queue();
//    }

    /**
     * Be very careful about this, a rate limit of 5 per minute is enforced
     * <p>
     * See Presence.setGame
     *
     * @param type "type of game" so streaming, playing, etc.
     * @param text Text to show
     */
    public static void setGame(Game.GameType type, String text) {
        DiscordMain.getInstance().getJda().getPresence().setGame(Game.of(type, text));
    }
}
