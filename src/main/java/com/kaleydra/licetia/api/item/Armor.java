package com.kaleydra.licetia.api.item;

import org.bukkit.event.entity.EntityDamageEvent;

public interface Armor extends EquipActivator {
	
	/**
	 * triggered when an entity with this armor gets hit
	 * @param event
	 */
	public void onDamage(EntityDamageEvent event);
	
}
