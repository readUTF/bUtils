package org.readutf.butils.events;

import org.readutf.butils.Main;
import org.readutf.butils.Utils.Settings;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingListener implements Listener {

	@EventHandler
    public void onProxyPing(final ProxyPingEvent event) {
       
		final ServerPing ping = event.getResponse();
        ping.setDescription(Settings.getMotd());
		
		if (!Main.config.getBoolean("WHITELIST.ENABLED")) {
            return;
        }
        
        if(!Main.config.getStringList("WHITELIST.PLAYERS").contains(event.getConnection().getName())) {
        	 ping.setVersion(new ServerPing.Protocol(ChatColor.translateAlternateColorCodes('&', Main.config.getString("WHITELIST.PLAYERCOUNT-MESSAGE")), 1));
        }
        return;
    }
	
}
