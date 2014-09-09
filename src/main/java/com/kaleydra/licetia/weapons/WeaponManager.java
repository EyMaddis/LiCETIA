/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.weapons;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import com.kaleydra.licetia.api.LiCETIAWeaponPlugin;
import com.kaleydra.licetia.api.item.Weapon;


public class WeaponManager{

	private LiCETIAWeaponPlugin plugin;
	
	/** DisplayName, Weapon */
	Map<String, Weapon> weapons = new HashMap<String, Weapon>();

	public WeaponManager(LiCETIAWeaponPlugin plugin){
		this.plugin = plugin;
	}
	
	public void addWeapon(Weapon weapon){
		weapons.put(weapon.getIdentifier().toLowerCase(), weapon);
//TODO:
//		SupplyManager.addExistingSupply(weapon);
	}
	
	public Weapon getWeapon(String displayName){
		if(displayName == null) return null;
		return weapons.get(displayName.toLowerCase());
	}
	
	public Weapon getWeapon(ItemStack item){
		if(item == null) return null;
		if(!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return null;
		Weapon weapon = getWeapon(item.getItemMeta().getDisplayName());
		if(weapon == null) return null;
		if(weapon.equals(item)) return weapon;
		return null;
	}
	
	public Collection<Weapon> getWeapons(){
		return weapons.values();
	}
	
	public LiCETIAWeaponPlugin getPlugin(){
		return plugin;
	}
	
	
}
