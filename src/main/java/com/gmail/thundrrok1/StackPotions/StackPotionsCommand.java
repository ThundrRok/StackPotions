package com.gmail.thundrrok1.StackPotions;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StackPotionsCommand implements CommandExecutor {

	StackPotions plugin;
	
	public StackPotionsCommand(StackPotions potionStackerPlugin) {
		this.plugin = potionStackerPlugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
		if (sender instanceof Player) {
			plugin.potionStackRun(args.length,(Player) sender);
			return(true);
		}
		else {
			sender.sendMessage(ChatColor.RED + "Only a player can stack their potions!");
			return(false);
		}
	}

}