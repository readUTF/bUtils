package org.readutf.butils.commands;

import java.util.HashMap;
import org.readutf.butils.Utils.Settings;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class reportCMD extends Command {

	public static HashMap<ProxiedPlayer, Integer> cooldown = new HashMap<ProxiedPlayer, Integer>();
	
    public static BungeeCord proxy = BungeeCord.getInstance();

    
    
    public reportCMD() {
        super("report");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (p.hasPermission("butils.command.report")) {
                if (args.length == 0) {
                    p.sendMessage(new TextComponent("§cUsage: /report <Player> <Reason>"));
                    return;
                }
                if (args.length == 1) {
                    p.sendMessage(new TextComponent("§cUsage: /report <Player> <Reason>"));
                }
                if (args.length >= 2) {
                    ProxiedPlayer target = proxy.getPlayer(args[0]);
                    if (target == null || !target.isConnected()) {
                        p.sendMessage(new TextComponent("§c" + args[0] + " is not online"));
                        return;
                    }
                    if(cooldown.containsKey(p)) {
                    	p.sendMessage(new TextComponent(Settings.cooldown.replace("%cooldown%", "" + cooldown.get(p))));
                    	return;
                    }
                    p.sendMessage(new TextComponent(Settings.sreport));
                    cooldown.put(p, 120);
                    for (ProxiedPlayer pp: BungeeCord.getInstance().getPlayers()) {
                        if (pp.hasPermission("butils.staff")) {
                            StringBuilder str = new StringBuilder();
                            for (int i = 1; i < args.length; i++) {
                                str.append(args[i] + " ");
                            }
                            pp.sendMessage(new TextComponent(Settings.report
                                .replace("%server%", p.getServer().getInfo().getName())
                                .replace("%name%", p.getName())
                                .replace("%target%", target.getName())
                                .replace("%reason%", str.toString())));
                        }
                    }
                }
            } else {
                p.sendMessage(new TextComponent("§cYou do not have permission for that command."));
            }
        }
    }



}