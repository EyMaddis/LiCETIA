package com.kaleydra.licetia.api;

import org.bukkit.inventory.ItemStack;

import com.kaleydra.licetia.api.item.CustomItem;

public class ItemManager extends Manager<CustomItem> {

	public boolean contains(ItemStack item){
		if (item == null) return false;
		if(!item.hasItemMeta()) return false;
		if(!item.getItemMeta().hasDisplayName()) return false;
		return super.contains(item.getItemMeta().getDisplayName());
	}
	
	public CustomItem get(ItemStack item){
		if(!contains(item)) return null;
		return super.get(item.getItemMeta().getDisplayName());
	}
}
