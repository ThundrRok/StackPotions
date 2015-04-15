package com.gmail.thundrrok1.StackPotions.Logger;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class StackPotionsLogger {
	private static Level logLevel = Level.INFO;
    private static Logger logger = null;
    private static String logPluginName = null;

	public static void initLogger(String pluginName, Level logLevel) {
		if (StackPotionsLogger.logger == null) {
			Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(pluginName);
	        if (plugin != null) {
	        	StackPotionsLogger.logger = Logger.getLogger(plugin.getServer().getLogger().getName() + "." + pluginName);
	        }
	        
	        StackPotionsLogger.logLevel = logLevel;
	        StackPotionsLogger.logger.setLevel(logLevel);
	        StackPotionsLogger.logPluginName = pluginName;
	    }
	}
	
	public static void setLogLevel(Level logLevel) {
		StackPotionsLogger.logLevel = logLevel;
		StackPotionsLogger.logger.setLevel(logLevel);
	}
	
	public static void fancyLog(final Level logLevel, final String message) {
		final String fancyName = ("[" + getName() + "]");
        String fancyLogLine = fancyName;
        logger.log(logLevel, fancyLogLine + " " + message);
    }
	
	public static Level getLogLevel() {
		return logLevel;
    }
    
    public static String getName() {
    	return logPluginName;
    }
}
