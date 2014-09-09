package com.kaleydra.licetia.weapons;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.kaleydra.licetia.api.item.AmmunitionUser;

/**
 * basic implementation of AmmunitionUser for quicker access.
 * override getAmmo and setAmmo methods (or more) if you want to implement a custom ammo system.
 *
 */
public abstract class AmmunitionHandler implements AmmunitionUser {
	
	public Map<String,Integer> playerAmmo = new HashMap<String, Integer>();

	int maxAmmo;
//	int ammoPerPickup;
	
	int ammoUseLeftClick;
	int ammoUseRightClick;
	
	public AmmunitionHandler(int maxAmmo, int ammoUseLeftClick, int ammoUseRightClick){
		this.maxAmmo = maxAmmo;
		this.ammoUseLeftClick = ammoUseLeftClick;
		this.ammoUseRightClick = ammoUseRightClick;
	}

	@Override
	public int getMaxAmmo() {
		return maxAmmo;
	}
	
	@Override
	public void setMaxAmmo(int maxAmmo) {
		this.maxAmmo = maxAmmo;
	}
	
	@Override
	public int getAmmoUseLeftClick() {
		return ammoUseLeftClick;
	}
	
	@Override
	public void setAmmoUseLeftClick(int ammoUseLeftClick) {
		this.ammoUseLeftClick = ammoUseLeftClick;
	}
	
	@Override
	public int getAmmoUseRightClick() {
		return ammoUseRightClick;
	}
	
	@Override
	public void setAmmoUseRightClick(int ammoUseRightClick) {
		this.ammoUseRightClick = ammoUseRightClick;
	}
	
	@Override
	public int getAmmo(Player player) {
		return playerAmmo.get(player.getName());
	}
	
	@Override
	public int setAmmo(Player player, int amount){
		playerAmmo.put(player.getName(), amount%maxAmmo);
		return getAmmo(player);
	}
	
	@Override
	public void decreaseAmmo(Player player, int amount) {
		int ammo = getAmmo(player);
		ammo -= amount;
		ammo %= maxAmmo;
		setAmmo(player, ammo);
	}
	
	@Override
	public boolean increaseAmmo(Player player, int amount) {
		int ammo = amount%maxAmmo;
		if(ammo != amount) return false;
		setAmmo(player, ammo);
		return true;
	}
	
	@Override
	public boolean hasEnoughAmmoLeftClick(Player player) {
		return (getAmmo(player) <= ammoUseLeftClick);
	}
	
	@Override
	public boolean hasEnoughAmmoRightClick(Player player) {
		return (getAmmo(player) <= ammoUseRightClick);
	}
	
}
