package com.gmail.thundrrok1.StackPotions;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class StackPotionsListener implements Listener {

	StackPotions plugin;
	
	public StackPotionsListener(StackPotions plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event) {
		Player p = null;
		int amount;
		if (event.getEntity().getShooter() instanceof Player ) {
			p = (Player) event.getEntity().getShooter();
			ItemStack item = p.getInventory().getItemInHand();
			if ((item != null) && (item.getType().equals(Material.POTION))) {
				if(!plugin.getConfigBoolean("throwStackedPotions")) {
					amount=item.getAmount();
					if (amount>0) {
						event.setCancelled(true);
						item.setAmount(amount+1);
						p.setItemInHand(item);
						p.updateInventory();
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerUse(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			Player p = null;
			int amount;
			p = event.getPlayer();
			ItemStack item = p.getInventory().getItemInHand();
			if ((item != null) && (item.getType().equals(Material.POTION))) {
				if(!plugin.getConfigBoolean("throwStackedPotions")) {
					amount=item.getAmount();
					if (amount>1) {
						event.setCancelled(true);
						item.setAmount(amount);
						p.setItemInHand(item);
						p.updateInventory();
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if ((event.getWhoClicked() instanceof Player)&&(event.getInventory().getType().equals(InventoryType.BREWING))&&!(event.getCursor().getType().equals(Material.AIR))&&(event.getRawSlot()<4)) {
			Player p = (Player) event.getWhoClicked();
			ItemStack item = p.getItemOnCursor();
			if ((item != null) && (item.getType().equals(Material.POTION))) {
				if(!plugin.getConfigBoolean("brewStackedPotions")) {
					int amount = item.getAmount();
					if (amount > 1) {
						event.getInventory().clear(event.getRawSlot());
						event.setCancelled(true);
						item.setAmount(amount);
						p.setItemOnCursor(item);
						p.updateInventory();
					}
				}
			}
		}
	}
}
