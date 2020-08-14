package me.benrobson.zander.discord.commands;

//public class rank extends ListenerAdapter {
//    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
//        String[] args = event.getMessage().getContentRaw().split("\\s+");
//        LuckPerms api = LuckPermsProvider.get();
//
//
//        if (args[0].equalsIgnoreCase(Variables.discordprefix + "lp")) {
//            if (args[1].equalsIgnoreCase("add")) {
//                // This will add the rank from the user.
//
//                event.getChannel().sendMessage("This will add a user to a group.");
//            } else if (args[1].equalsIgnoreCase("remove")) {
//                // This will remove the rank from the user.
//
//                event.getChannel().sendMessage("This will remove a user to a group.");
//            } else if (args[1].equalsIgnoreCase("ranks")) {
//                // This will list all of the ranks that the user has.
//
//                event.getChannel().sendMessage("This will list all of the ranks the user has.");
//            }
//
//
//
//
//
//            EmbedBuilder embed = new EmbedBuilder();
//            embed.setTitle("Network Status");
//            embed.setColor(Color.ORANGE);
//
//            ProxyServer.getInstance().getServers().forEach((name, serverInfo) -> {
//                String servername = serverInfo.getName();
//                servername = servername.substring(0,1).toUpperCase() + servername.substring(1).toLowerCase();
//
//                embed.addField(servername, "Online: " + serverInfo.getPlayers().size(), true);
//            });
//
////            event.getChannel().sendMessage(embed.build()).queue();
//        }
//    }
//
//    public static boolean isPlayerInGroup(Player player, String group) {
//        return player.hasPermission("group." + group);
//    }
//}
