package com.kaleydra.licetia.api.item;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.kaleydra.licetia.api.Identifiable;

public interface CustomItem extends Identifiable{

	/**
	 * get an identifier for the item. In most cases the display name!
	 * @return the identifier
	 */
	public String getIdentifier();
	
	public List<String> getLore();
	
	public Material getMaterial();

//	/**
//	 * find the first appearance of the item in the inventory of a player
//	 * @param player
//	 * @return first index of the position in the ItemStack Array
//	 * @see Inventory#getContents() 
//	 */
//	public int getSlot(Player player);
//	
	/**
	 * check whether or not an item is a custom Item
	 * @param item
	 * @return
	 */
	public boolean equals(ItemStack item);
	
	/**
	 * get the itemStack
	 * @return
	 */
	public ItemStack getItem();
}
