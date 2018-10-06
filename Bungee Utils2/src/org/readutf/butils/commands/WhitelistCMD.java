package org.readutf.butils.commands;

import java.util.List;

import org.readutf.butils.Main;
import org.readutf.butils.Utils.Settings;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class WhitelistCMD extends Command {

	public WhitelistCMD() {
		super("whitelist", "butils.command.whitelist", new String[] { "whitelist" });
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		CommandSender p = sender;
		
		if(args.length == 0) {
			p.sendMessage(new TextComponent("§c/whitelist <Toggle/Add/Remove/List>"));
			return;
		}
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("toggle")) {
				Boolean whitelist = Main.config.getBoolean("WHITELIST.ENABLED");
				if(whitelist == true) {
					Main.config.set("WHITELIST.ENABLED", false);
					Main.instance.save();
					p.sendMessage(new TextComponent("§cWhitelist mode has been disabled"));
					for(ProxiedPlayer pp : BungeeCord.getInstance().getPlayers()) {
						Settings.sendMaintanance(pp);
					}
					return;
				} else if(whitelist == false) {
					Main.config.set("WHITELIST.ENABLED", true);
					Main.instance.save();
					p.sendMessage(new TextComponent("§aWhitelist mode has been enabled"));
					for(ProxiedPlayer pp : BungeeCord.getInstance().getPlayers()) {
						Settings.sendMaintanance(pp);
					}
					for(ProxiedPlayer pp : BungeeCord.getInstance().getPlayers()) {
						if (!Main.config.getStringList("WHITELIST.PLAYERS").contains(pp.getName().toLowerCase())) {
							pp.disconnect(new TextComponent(Settings.getMaintananceMsg()));
						}
					}
					return;
				} else {
					Main.config.set("WHITELIST.ENABLED", true);
					Main.instance.save();
					p.sendMessage(new TextComponent("§aWhitelist mode has been enabled"));
					return;
				}
			}
			if(args[0].equalsIgnoreCase("add")) {
				p.sendMessage(new TextComponent("§cUsage: /whitelist add <Player>"));
				return;
			}
			if(args[0].equalsIgnoreCase("remove")) {
				p.sendMessage(new TextComponent("§cUsage: /whitelist remove <Player>"));
				return;
			}
			if(args[0].equalsIgnoreCase("list")) {
				List<String> whitelist = Main.config.getStringList("WHITELIST.PLAYERS");
				p.sendMessage(new TextComponent("§eCurrently Whitelisted: " + whitelist));
				return;
			} else {
				p.sendMessage(new TextComponent("§c/whitelist <Toggle/Add/Remove/List>"));
				return;
			}
		}
		if(args.length == 2) {
			if(args[0].equalsIgnoreCase("add")) {
				List<String> whitelist = Main.config.getStringList("WHITELIST.PLAYERS");
				if(!whitelist.contains(args[1].toLowerCase())) {
					whitelist.add(args[1].toLowerCase());
					Main.config.set("WHITELIST.PLAYERS", whitelist);
					Main.instance.save();
					p.sendMessage(new TextComponent("§a" + args[1] + " has been added to the whitelist."));
				} else {
					p.sendMessage(new TextComponent("§cThat player is already whitelisted."));
				}
				return;
			}
			if(args[0].equalsIgnoreCase("remove")) {
				List<String> whitelist = Main.config.getStringList("WHITELIST.PLAYERS");
				if(whitelist.contains(args[1].toLowerCase())) {
					whitelist.remove(args[1].toLowerCase());
					Main.config.set("WHITELIST.PLAYERS", whitelist);
					Main.instance.save();
					p.sendMessage(new TextComponent("§a" + args[1] + " has been added to the whitelist."));
				} else {
					p.sendMessage(new TextComponent("§cThat player is not whitelisted."));
				}
				return;
			} else {
				p.sendMessage(new TextComponent("§c/whitelist <Toggle/Add/Remove/List>"));
			}
		}
		
		
	}
	
}
