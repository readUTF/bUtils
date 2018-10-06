package org.readutf.butils.commands;

import java.util.HashMap;

import org.readutf.butils.Utils.Settings;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class requestCMD extends Command {

    public requestCMD() {
        super("request", "butils.command.request", new String[] {
            "helpop"
        });
    }

    public static BungeeCord proxy = BungeeCord.getInstance();
    public static HashMap < ProxiedPlayer, Integer > cooldown = new HashMap < ProxiedPlayer, Integer > ();



    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (cooldown.containsKey(p)) {
                p.sendMessage(new TextComponent(Settings.cooldown.replace("%cooldown%", "" + cooldown.get(p))));
                return;
            }
            if (args.length == 0) {
                p.sendMessage(new TextComponent("§cUsage: /request <Message..>"));
                return;
            }
            if (args.length >= 1) {
                StringBuilder str = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    str.append(args[i] + " ");
                }

                p.sendMessage(new TextComponent(Settings.srequest));

                for (ProxiedPlayer pp: proxy.getPlayers()) {
                    if (pp.hasPermission("butils.staff")) {
                        pp.sendMessage(new TextComponent(Settings.request.replace("%player%", p.getName()).replace("%msg%", str.toString()).replace("%server%", p.getServer().getInfo().getName())));
                    }
                }
            }
            cooldown.put(p, 120);
        }
    }

}