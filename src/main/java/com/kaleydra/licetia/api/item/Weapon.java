package com.kaleydra.licetia.api.item;

import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface Weapon extends CustomItem {
	
	/**
	 * what happens if the player does a right click
	 * @param event
	 * @see PlayerInteractEvent
	 */
	public void onLeftClick(PlayerInteractEvent event);
	
	/**
	 * what happens if the player does a right click
	 * @param event
	 * @see PlayerInteractEvent
	 */
	public void onRightClick(PlayerInteractEvent event);
	
	/**
	 * what happens when a living entity gets damaged by someone holding that weapon, projectile hits will get ignored
	 * @param event
	 * @see EntityDamageEvent
	 */
	public void onHit(EntityDamageEvent event);
	
}