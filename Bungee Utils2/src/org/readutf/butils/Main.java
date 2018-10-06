package org.readutf.butils;

import net.md_5.bungee.api.*;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.readutf.butils.Utils.Metrics;
import org.readutf.butils.Utils.Settings;
import org.readutf.butils.commands.StaffChatCMD;
import org.readutf.butils.commands.WhitelistCMD;
import org.readutf.butils.commands.bBroadcastCMD;
import org.readutf.butils.commands.reportCMD;
import org.readutf.butils.commands.requestCMD;
import org.readutf.butils.events.PlayerJoin;
import org.readutf.butils.events.ProxyPingListener;

import java.io.*;
import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.config.*;

public class Main extends Plugin {

	public static Configuration config;
	
	public static Main instance;
	
	public void onEnable() {
		instance = this;
		
		//Generate Configuration
        try {
        	if(!getDataFolder().exists()) {
        		getDataFolder().mkdir();
        	}
        	File file = new File(getDataFolder(), "config.yml");
        	if(!file.exists()) {
        		Files.copy(this.getResourceAsStream("config.yml"), file.toPath(), new CopyOption[0]);
        	}
        	loadConfig();
        	registerEvents();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        if(!config.getString("CONFIG-VERSION").equalsIgnoreCase("B5")) {
        	System.out.println("DUE TO CHANGES IN THE LATEST VERSION PLEASE DELETE YOUR CONFIG.YML");
        	System.out.println("DUE TO CHANGES IN THE LATEST VERSION PLEASE DELETE YOUR CONFIG.YML");
        	return;
        }
		
		getLogger().info(ChatColor.DARK_GRAY + "-=-=-=-=-=-=-=-=-=-=-=-=-");
        getLogger().info(ChatColor.GREEN + "[b] Enabled");
        getLogger().info(ChatColor.GREEN + "Version: " + this.getDescription().getVersion());
        getLogger().info(ChatColor.DARK_GRAY + "-=-=-=-=-=-=-=-=-=-=-=-=-");
        registerCommands();
        
        Metrics metrics = new Metrics(this);

        // Optional: Add custom charts
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "My value";
            }
        }));
        
        getProxy().getScheduler().schedule(this, new Runnable() {
			
			@Override
			public void run() {
				requestCooldown();
				reportCooldown();
				
			}
		}, 0L, 1L, TimeUnit.SECONDS);
        
        
        
	}
	
	void registerCommands() {
		getProxy().getPluginManager().registerCommand(this, new reportCMD());
		getProxy().getPluginManager().registerCommand(this, new StaffChatCMD());
		getProxy().getPluginManager().registerCommand(this, new requestCMD());
		getProxy().getPluginManager().registerCommand(this, new WhitelistCMD());
		getProxy().getPluginManager().registerCommand(this, new bBroadcastCMD());
	}
	
	void registerEvents() {
		getProxy().getPluginManager().registerListener(this, new PlayerJoin());
		getProxy().getPluginManager().registerListener(this, new ProxyPingListener());
	}
	
	public void loadConfig() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.getDataFolder(), "config.yml"));
        }
        catch (IOException ex) {}
    }
	
	void reportCooldown() {
		for(ProxiedPlayer p : reportCMD.cooldown.keySet()) {
			int newvalue = reportCMD.cooldown.get(p) - 1;
			if(newvalue != 0) {
				reportCMD.cooldown.put(p, newvalue);
			} else {
				reportCMD.cooldown.remove(p);
			}
		}
	}
	
	void requestCooldown() {
		for(ProxiedPlayer p : requestCMD.cooldown.keySet()) {
			int newvalue = requestCMD.cooldown.get(p) - 1;
			if(newvalue != 0) {
				requestCMD.cooldown.put(p, newvalue);
			} else {
				requestCMD.cooldown.remove(p);
			}
		}
	}
	
	public void save() {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(getDataFolder(), "config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
