package org.readutf.butils.commands;

import java.util.HashMap;

import org.readutf.butils.Utils.Settings;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatCMD extends Command {

	public static BungeeCord proxy = BungeeCord.getInstance();
	public static HashMap<ProxiedPlayer, Boolean> toggled = new HashMap<>();
	
	public StaffChatCMD() {
		super("staffchat", "butils.command.staffchat", new String[] { "sc" });
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			if(args.length == 0) {
				p.sendMessage(new TextComponent("§cUsage: /staffchat <Message..>"));
				return;
			}
			if(args.length >= 1) {
				StringBuilder str = new StringBuilder();
                for(int i = 0; i < args.length; i++) {
                	str.append(args[i] + " ");
                }
                for(ProxiedPlayer pp : proxy.getPlayers()) {
                		if(pp.hasPermission("butils.staff")) {
                    		pp.sendMessage(new TextComponent(Settings.scformat
                        			.replace("%name%", p.getName())
                        			.replace("%message%", str.toString())
                        			.replace("%server%", p.getServer().getInfo().getName())));
                	}
                }
			}
		}
		
	}

}
