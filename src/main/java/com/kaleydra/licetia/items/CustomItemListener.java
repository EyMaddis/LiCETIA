package com.kaleydra.licetia.items;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.kaleydra.licetia.LiCETIA;
import com.kaleydra.licetia.api.ItemManager;
import com.kaleydra.licetia.api.item.ConsumableItem;
import com.kaleydra.licetia.api.item.CustomItem;
import com.kaleydra.licetia.api.item.LeftClickItem;
import com.kaleydra.licetia.api.item.RightClickItem;

public class CustomItemListener implements Listener {

	JavaPlugin plugin;
	public CustomItemListener(JavaPlugin plugin){
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onItemConsume(PlayerItemConsumeEvent event){
		if(event.isCancelled()) return;
		ItemManager itemManager = LiCETIA.getInstance().getItemManager();
		ItemStack item = event.getItem();
		if(!itemManager.contains(item)) return;
		CustomItem customItem = itemManager.get(item);
		if(!(customItem instanceof ConsumableItem)) return;
		((ConsumableItem) customItem).onConsume(event);
	}
	
	@EventHandler(ignoreCancelled=false)
	public void onInteract(PlayerInteractEvent event){
		if(event.getAction().equals(Action.PHYSICAL)) return;
		
		ItemManager itemManager = LiCETIA.getInstance().getItemManager();
		ItemStack item = event.getItem();
		if(!itemManager.contains(item)) return;
		CustomItem customItem = itemManager.get(item);
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(!(customItem instanceof RightClickItem)) return;
			((RightClickItem) customItem).onRightClick(event);	
		} else { // physical not possible
			if(!(customItem instanceof LeftClickItem)) return;
			((LeftClickItem) customItem).onLeftClick(event);
		}
	}
	
}
