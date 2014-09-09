package com.kaleydra.licetia.api.item;

import org.bukkit.entity.Player;

/**
 * Something that needs ammunition to function. Should be used with {@link Weapon}
 *
 */
public interface AmmunitionUser {

	/**
	 * how much ammo can the weapon have at maximum?
	 * @return maximum ammo capacity
	 */
	public int getMaxAmmo();
	
	/**
	 * set maximum capacity for weapon ammunition
	 * @param maxAmmo
	 */
	public void setMaxAmmo(int maxAmmo);
	
	
//	// TODO: Needed here?
//	public int getAmmoPerPickup();
//	
//	public void setAmmoPerPickup(int ammoPerPickup);
//	
	
	
	/**
	 * @return the ammoUseLeftClick
	 */
	public int getAmmoUseLeftClick();
	
	/**
	 * @param ammoUseLeftClick the ammoUseLeftClick to set
	 */
	public void setAmmoUseLeftClick(int ammoUseLeftClick);
	
	/**
	 * @return the ammoUseRightClick
	 */
	public int getAmmoUseRightClick();
	
	/**
	 * @param ammoUseRightClick the ammoUseRightClick to set
	 */
	public void setAmmoUseRightClick(int ammoUseRightClick);
	
	/**
	 * get the current ammunition of a player
	 * @param player
	 * @return
	 */
	public int getAmmo(Player player);
	
	/**
	 * set the ammo value for a player
	 * @param player
	 * @param ammo
	 * @return the ammo of the player after the setting
	 */
	public int setAmmo(Player player, int ammo);
	
	/**
	 * decrease how much ammo a player currently has
	 * @param player
	 * @param amount
	 */
	public void decreaseAmmo(Player player, int amount);
	
	/**
	 * increase how much ammo a player currently has
	 * @param player
	 * @param amount
	 * @return
	 * @see AmmunitionUser#getMaxAmmo()
	 */
	public boolean increaseAmmo(Player player, int amount);
	
	/**
	 * @param player
	 * @return true if item can be used again
	 */
	public boolean hasEnoughAmmoLeftClick(Player player);
	
	/**
	 * @param player
	 * @return true if item can be used again
	 */
	public boolean hasEnoughAmmoRightClick(Player player);

}
