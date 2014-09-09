package com.kaleydra.licetia.api.item;


public interface EquipActivator extends CustomItem {
	
	/**
	 * triggered when the item is held in hand or equipped as armor
	 * @param item
	 */
	public void onEquip(CustomItem item);
	
	/**
	 * triggered when the is no more in hand or equipped as armor
	 * @param item
	 */
	public void onDisarm(CustomItem item);
}
