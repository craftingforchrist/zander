package me.benrobson.zander.commands;

import me.benrobson.zander.ConfigurationManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class discord extends Command {
    public discord() {
        super("discord");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        String siteaddress = ConfigurationManager.getConfig().getString("web.siteaddress");
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            TextComponent message = new TextComponent("Get to know the community and join our Discord here: " + ChatColor.BLUE + siteaddress + "discord");
            message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, siteaddress + "discord"));
            return;
        }
    }
}
