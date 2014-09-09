package com.kaleydra.licetia.api.supplies;

import org.bukkit.inventory.ItemStack;

import com.kaleydra.licetia.supplies.PlayerPickupSupplyEvent;

public interface LiCETIASupply {
	
	public abstract void onPickup(PlayerPickupSupplyEvent e);
	
	public abstract String getIdentifier();
	
	/**
	 * @return the drop
	 */
	public abstract ItemStack getDrop();
	
	/**
	 * check if an item is the drop of this supply just checks for same name and
	 * type
	 * 
	 * @param item
	 * @return
	 */
	public abstract boolean isDrop(ItemStack item);
	
}