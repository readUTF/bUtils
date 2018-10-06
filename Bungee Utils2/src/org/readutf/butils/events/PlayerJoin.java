package org.readutf.butils.events;

import java.util.List;

import org.readutf.butils.Main;
import org.readutf.butils.Utils.Settings;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerJoin implements Listener {
	
	public static BungeeCord proxy = BungeeCord.getInstance();
	
	@EventHandler
	public void onJoin(ServerConnectedEvent e) {
		ProxiedPlayer p = e.getPlayer();
		if(p.hasPermission("butils.staff")) {
			for(ProxiedPlayer pp : proxy.getPlayers()) {
				if(pp.hasPermission("butils.staff")) {
					pp.sendMessage(new TextComponent(Settings.sjoin.replace("%player%", p.getName()).replace("%server%", e.getServer().getInfo().getName())));	
				}
			}
		}
	}
	
	@EventHandler
    public void onLogin(final LoginEvent Ev) {
        if (Ev.isCancelled()) {
            return;
        }
        List<String> whitelist = Main.config.getStringList("WHITELIST.PLAYERS");
        if (!whitelist.contains(Ev.getConnection().getName().toLowerCase())) {
            if(Main.config.getBoolean("WHITELIST.ENABLED") == true) {
            	Ev.setCancelled(true);
                Ev.setCancelReason(Settings.getMaintananceMsg());
            }
        }
    }

}
