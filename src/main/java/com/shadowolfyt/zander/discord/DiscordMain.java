package com.shadowolfyt.zander.discord;

import com.shadowolfyt.zander.ZanderMain;
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
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.security.auth.login.LoginException;

import java.util.*;

import static org.bukkit.Bukkit.getServer;

public class DiscordMain extends ListenerAdapter implements Listener {

    public HashMap<UUID, String>uuidCodeMap;
    public HashMap<UUID, String>uuididMap;
    public List<UUID>verifiedMembers;
    public Guild guild;

    ZanderMain plugin;
    public JDA jda;
    public DiscordMain (ZanderMain instance) {
        plugin = instance;
        startBot();
        jda.addEventListener(this);
        //plugin.getCommand("verify").setExecutor(this);
        uuidCodeMap = new HashMap<>();
        uuididMap = new HashMap<>();
        verifiedMembers = new ArrayList<>();
        Bukkit.getScheduler().runTaskLater(plugin, ()-> guild = jda.getGuilds().get(0), 100L);
    }

    private void startBot() {
        try {
            jda = new JDABuilder(AccountType.BOT)
                .setToken(plugin.getConfig().getString("discord.token"))
                .buildBlocking();

            getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "[Discord] " + ChatColor.GREEN + "Zander is now connected to Discord.");
            TextChannel textChannel = jda.getTextChannelsByName(plugin.getConfig().getString("discord.chatchannel"), true).get(0);
            textChannel.sendMessage("** :white_check_mark: Server has started **").queue();
        } catch (LoginException | InterruptedException e) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "Zander has encountered an error and can't login to Discord. The Discord Token may not be set, discord integrations might not function.");
        }
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
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("discord.chatprefix")) + " " + user.getName() + "#" + user.getDiscriminator() + ": " + message);
        } else {
            return;
        }
    }

    //
    // Player Join
    //
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        TextChannel textChannel = jda.getTextChannelsByName(plugin.getConfig().getString("discord.chatchannel"), true).get(0);
        textChannel.sendMessage(":heavy_plus_sign: **" + event.getPlayer().getName() + " joined the server **").queue();
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
    // Discord Verification
    //
    //@Override
//    public void GuildMessageReceived(GuildMessageReceivedEvent event) {
//        if (event.getAuthor().isBot() || event.getAuthor().isFake() || event.isWebhookMessage()) return;
//        String[] args = event.getMessage().getContentRaw().split(" ");
//        if (args[0].equalsIgnoreCase(plugin.getConfig().getString("discord.commandprefix") + "link")) {
//            if (event.getMember().getRoles().stream().filter(role -> role.getName().equals("Verified")).findAny().orElse(null) != null){
//                event.getChannel().sendMessage("Error " + event.getAuthor().getAsMention() + ", you are already verified.").queue();
//                return;
//            }
//
//            if (args.length != 2) {
//                event.getChannel().sendMessage("Error! You need to specify a user.").queue();
//                return;
//            }
//
//            Player target = Bukkit.getPlayer(args[1]);
//            if (target == null){
//                event.getChannel().sendMessage("Error! This player is not online.").queue();
//                return;
//            }
//            String randomcode = new Random().nextInt(800000)+200000+"DVC"; // DVC is Disoord Verification Code
//            uuidCodeMap.put(target.getUniqueId(), randomcode);
//            uuididMap.put(target.getUniqueId(), event.getAuthor().getId());
//            event.getAuthor().openPrivateChannel().complete().sendMessage("Your verification code has been generated!\n Use this command in game `/verify " + randomcode + "`").queue();
//        }
//    }
//
//    @EventHandler
//    public void onJoinVerification(PlayerJoinEvent event) {
//        if (plugin.playerData.contains("Data." + event.getPlayer().getUniqueId().toString())){
//            verifiedMembers.add(event.getPlayer().getUniqueId());
//        }
//    }
//
//    @EventHandler
//    public void onQuitVerification(PlayerQuitEvent event) {
//        if (plugin.playerData.contains("Data." + event.getPlayer().getUniqueId().toString())){
//            verifiedMembers.remove(event.getPlayer().getUniqueId());
//        }
//    }
//
//    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
//        if (!(sender instanceof Player)){
//            sender.sendMessage("Only players can execute this command.");
//        }
//        Player player = (Player) sender;
//        if (plugin.playerData.contains("Data." + player.getUniqueId().toString())){
//            player.sendMessage("Sorry, you are already verified.");
//            return true;
//        }
//        if (uuidCodeMap.containsKey(player.getUniqueId())){
//            player.sendMessage("Not pending verification process.");
//            return true;
//        }
//        if (args.length != 1) {
//            player.sendMessage("Incorrect Usage: /verify [code]");
//            return true;
//        }
//        String actualcode = uuidCodeMap.get(player.getUniqueId());
//        if (actualcode.equals(args[0])) {
//            player.sendMessage("Code is not valid! Check again");
//            return true;
//        }
//
//        String discordid = uuididMap.get(player.getUniqueId());
//        Member target = guild.getMemberById(discordid);
//        if (target == null) {
//            uuidCodeMap.remove(player.getUniqueId());
//            player.sendMessage("Error: It seems that you left our Discord server.");
//            return true;
//        }
//
//        plugin.playerData.set("Data." + player.getUniqueId(),discordid);
//        try {
//            plugin.playerData.save(plugin.data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        uuidCodeMap.remove(player.getUniqueId());
//        uuididMap.remove(player.getUniqueId());
//        verifiedMembers.add(player.getUniqueId());
//
//        Role verifiedrole = guild.getRolesByName("Verified", false).get(0);
//        guild.getController().addSingleRoleToMember(target, verifiedrole).queue();
//        target.getUser().openPrivateChannel().complete().sendMessage("Verification Successful. You have linked your account with Minecraft Account: " + player.getName()).queue();
//        player.sendMessage("You have verified correctly. You have linked your account with member: " + target.getUser().getName() + "#" + target.getUser().getDiscriminator());
//        return true;
//    }
}
