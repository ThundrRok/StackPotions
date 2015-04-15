package com.gmail.thundrrok1.StackPotions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

public class StackPotions extends JavaPlugin {
	private HashMap<String, Boolean> booleanConfigMap = new HashMap<String, Boolean>();

	public void onEnable() {
		getServer().getPluginManager().registerEvents(
				new StackPotionsListener(this), this);
		saveDefaultConfig();
		Configuration config = getConfig();
		loadConfig(config);
		getCommand("potion").setExecutor(new StackPotionsCommand(this));
	}

	public void onDisable() {
		this.saveConfig();
	}

	private void loadConfig(Configuration config) {
		booleanConfigMap.put("brewStackedPotions", false);
		booleanConfigMap.put("throwStackedPotions", true);
		String configMapKey;
		for (int i = 0; i < booleanConfigMap.size(); i++) {
			configMapKey = booleanConfigMap.keySet().toArray()[i].toString();
			if (config.get(configMapKey, null) != null) {
				booleanConfigMap.put(configMapKey,
						config.getBoolean(configMapKey));
			} else
				config.set(configMapKey, booleanConfigMap.get(configMapKey));
		}
	}

	public void potionStackRun(int cmd_len, Player p) {
		if (cmd_len != 0) {
			p.sendMessage("Correct usage: /potion)");
		} else {

			HashMap<Short, Integer> better_potions = new HashMap<Short, Integer>();
			HashMap<Short, List<PotionEffect>> hold_my_effects = new HashMap<Short, List<PotionEffect>>();

			boolean potion_flag = false;
			ItemStack item;
			ItemStack potions;

			ItemStack[] inventory_contents = p.getInventory().getContents();
			for (int i = 0; i < inventory_contents.length; i++) {
				item = inventory_contents[i];
				if ((item != null) && (item.getType().equals(Material.POTION))) {
					short potion_type = item.getDurability();
					// store the updated amount of potions at the key of its type
					if (better_potions.containsKey(potion_type)) {
						int better_ammount = better_potions.get(potion_type);
						better_potions.put(potion_type, better_ammount + item.getAmount());
					} else {
						better_potions.put(potion_type, item.getAmount());
					}
					PotionMeta pmeta = (PotionMeta) item.getItemMeta();
					if(pmeta.hasCustomEffects()){
						//could check that we are using the same effects, but they all 'should' 
						//'theoretically' never have the same potion type
						if(!hold_my_effects.containsKey(potion_type)){
							hold_my_effects.put(potion_type, pmeta.getCustomEffects());
						}
					}
					p.getInventory().clear(i);

				}
			}

			Iterator<Short> keySetIterator = better_potions.keySet().iterator();

			while (keySetIterator.hasNext()) {
				short key = keySetIterator.next();
				potions = new ItemStack(Material.POTION,better_potions.get(key), key);
				
				PotionMeta pmeta = (PotionMeta) potions.getItemMeta();
				
				if(hold_my_effects.containsKey(key)){
					for (PotionEffect effect : hold_my_effects.get(key)) {
		                pmeta.addCustomEffect(effect, true);
		            }
				}

		        potions.setItemMeta(pmeta);
				p.getInventory().addItem(new ItemStack[] { potions });
				potion_flag = true;
			}
			if (potion_flag)
				p.sendMessage(ChatColor.BLUE
						+ "You're potions have been magically stacked!");
			else
				p.sendMessage(ChatColor.RED + "You have no potions to stack!");
		}
	}

	public boolean getConfigBoolean(String path) {
		return this.booleanConfigMap.get(path);
	}
}
