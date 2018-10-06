package org.readutf.butils.Utils;

import org.readutf.butils.Main;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Settings {

	@Getter public static Boolean maintanance = false;
	
	
	public static String report = ChatColor.translateAlternateColorCodes('&', Main.config.getString("REPORT"));
	public static String scformat = ChatColor.translateAlternateColorCodes('&', Main.config.getString("STAFFCHAT-FORMAT"));
	public static String sjoin = ChatColor.translateAlternateColorCodes('&', Main.config.getString("STAFF-JOIN"));
	public static String cooldown = ChatColor.translateAlternateColorCodes('&', Main.config.getString("COOLDOWN"));
	public static String request = ChatColor.translateAlternateColorCodes('&', Main.config.getString("REQUEST-FORMAT"));
	public static String sreport = ChatColor.translateAlternateColorCodes('&', Main.config.getString("SUCCESSFULL-REPORT"));
	public static String srequest = ChatColor.translateAlternateColorCodes('&', Main.config.getString("SUCCESSFULL-REQUEST"));
	public static String estaffchat = ChatColor.translateAlternateColorCodes('&', Main.config.getString("ENABLED-STAFFCHAT"));
	public static String dstaffchat = ChatColor.translateAlternateColorCodes('&', Main.config.getString("DISABLED-STAFFCHAT"));
	public static String alert = ChatColor.translateAlternateColorCodes('&', Main.config.getString("BROADCAST-FORMAT"));
	public static String staffchattoggled = ChatColor.translateAlternateColorCodes('&', Main.config.getString("TOGGLED-STAFFCHAT"));


	public static String getMaintananceMsg() {
		StringBuilder str = new StringBuilder();
		for(String s : Main.config.getStringList("WHITELIST.KICK-MESSAGE")) {
			s = ChatColor.translateAlternateColorCodes('&', s);
			str.append(s + "\n");
		}
		
		return str.toString();
		
	}
	
	public static void sendMaintanance(ProxiedPlayer p) {
		Boolean whitelist = Main.config.getBoolean("WHITELIST.ENABLED");
		if(whitelist == true) {
			for(String s : Main.config.getStringList("WHITELIST.ACTIVATION-MESSAGE")) {
				p.sendMessage(new TextComponent(s));
			}
		} else {
			for(String s : Main.config.getStringList("WHITELIST.DEACTIVATION-MESSAGE")) {
				p.sendMessage(new TextComponent(s));
			}
		}
 	}
	
	public static String getMotd() {
		Boolean whitelist = Main.config.getBoolean("WHITELIST.ENABLED");
		String s;
		if(whitelist == true) {
			s = Main.config.getString("WHITELIST.MOTD");
			s = translate(s);
			s = s.replaceAll("%doublearrow%", "»");
			s = s.replaceAll("%newline%", "\n");
		} else {
			s = Main.config.getString("MOTD");
			s = translate(s);
			s = s.replaceAll("%doublearrow%", "»");
			s = s.replaceAll("%newline%", "\n");
		}
		return s;
		
	}
	
	static String translate(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
}
