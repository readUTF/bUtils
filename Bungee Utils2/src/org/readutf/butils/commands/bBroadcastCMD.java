package org.readutf.butils.commands;

import org.readutf.butils.Utils.Settings;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class bBroadcastCMD extends Command {

	public bBroadcastCMD() {
		super("bbroadcast", "butils.command.broadcast", new String[] { "bbc", "balert", "ba" });
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		CommandSender p = sender;
		
		if(args.length < 1) {
			p.sendMessage(new TextComponent("§aUsage: /bbroadcast <Message..>"));
			return;
		} else if(args.length >= 1) {
			StringBuilder str = new StringBuilder();
			for(int i = 0; i < args.length; i++) {
				str.append(args[i]);
			}
			
			for(ProxiedPlayer pp : BungeeCord.getInstance().getPlayers()) {
				pp.sendMessage(new TextComponent(Settings.alert.replaceAll("%msg%", str.toString())));
			}
			
		}
		
		
		
		
		
	}
	
	

}
