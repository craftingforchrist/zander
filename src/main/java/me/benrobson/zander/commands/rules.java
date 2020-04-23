package me.benrobson.zander.commands;

import me.benrobson.zander.ConfigurationManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class rules extends Command {
    public rules() {
        super("rules");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        String siteaddress = ConfigurationManager.getConfig().getString("web.siteaddress");
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            TextComponent message = new TextComponent("Please read and abide by the Network rules which you can find on our website here: " + ChatColor.BLUE + siteaddress + "rules");
            message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, siteaddress + "rules"));
            return;
        }
    }
}
