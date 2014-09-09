package com.kaleydra.licetia.api.item;

import org.bukkit.entity.Player;

public interface CooldownWeapon extends Weapon {
	
	/**
	 * time the weapon is deactivated after the left click attack happened
	 * @return time in milliseconds
	 */
	public int getCooldownLeftClick();

	/**
	 * set time the weapon is deactivated after the left click attack happened
	 * @param cooldownLeftClick time in milliseconds
	 */
	public void setCooldownLeftClick(int cooldownLeftClick);

	/**
	 * time the weapon is deactivated after the left click attack happened
	 * @return
	 */
	public int getCooldownRightClick();

	/**
	 * time the weapon is deactivated after the left click attack happened
	 * @param cooldownRightClick
	 */
	public void setCooldownRightClick(int cooldownRightClick);

	/**
	 * toggle cooldown for left click
	 * @param player the player that used the weapon
	 */
	public void activateCooldownLeft(Player player);


	/**
	 * toggle cooldown for right click
	 * @param player the player that used the weapon
	 */
	public void activateCooldownRight(Player player);


	/**
	 * get the time the weapon can't be used again
	 * @param player the player that used the weapon
	 * @return the time left in milliseconds
	 */
	public long getRemainingCooldown(Player player);

	
}
