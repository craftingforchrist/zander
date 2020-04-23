package me.benrobson.zander;

public class Variables {
    public static String pluginprefix = ConfigurationManager.getConfig().getString("prefix.plugin");
    public static String developmentprefix = ConfigurationManager.getConfig().getString("prefix.development");
    public static String reportprefix = ConfigurationManager.getConfig().getString("prefix.report");
    public static String voteprefix = ConfigurationManager.getConfig().getString("prefix.vote");

    public static String siteaddress = ConfigurationManager.getConfig().getString("web.siteaddress");
    public static String discordtoken = ConfigurationManager.getConfig().getString("discord.token");
}
