/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.listeners;
 
import java.util.Locale;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.kaleydra.licetia.LiCETIA;
import com.kaleydra.licetia.api.LiCETIAWeaponPlugin;
import com.kaleydra.licetia.api.item.AmmunitionUser;
import com.kaleydra.licetia.api.item.BowWeapon;
import com.kaleydra.licetia.api.item.CooldownWeapon;
import com.kaleydra.licetia.api.item.Weapon;

public class WeaponListener implements Listener {
	LiCETIAWeaponPlugin plugin;
	
	public WeaponListener(LiCETIAWeaponPlugin plugin){
		this.plugin = plugin;
	}
	
	@EventHandler(ignoreCancelled=false, priority=EventPriority.MONITOR)
	public void onPlayerInteract(PlayerInteractEvent e){
		if(e.getAction().equals(Action.PHYSICAL)) return;
		if(e.isCancelled() && !e.getAction().equals(Action.LEFT_CLICK_AIR) && !e.getAction().equals(Action.RIGHT_CLICK_AIR))
			return;
		
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(LiCETIA.isInteractiveBlock(e.getMaterial().getId())) {
				return;
			}
		}
		
		ItemStack item = e.getItem();
		if(item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return;
		
//		String weaponName = Weapon.getNameWithoutAmmo(item.getItemMeta().getDisplayName());
		Weapon weapon = plugin.getWeaponManager().getWeapon(item.getItemMeta().getDisplayName());
		if(weapon == null) {
			plugin.getLogger().info("Player "+e.getPlayer().getName()+" is not using a weapon");
			return;
		}
		if(weapon instanceof CooldownWeapon){
			long remainingCooldown =((CooldownWeapon) weapon).getRemainingCooldown(e.getPlayer());
//			plugin.getLogger().info("Cooldown: "+remainingCooldown);
			// 3500
			if(remainingCooldown != 0){
				double remaining = ((double)remainingCooldown)/1000.0; // Math.round((((double)remainingCooldown)/100))/10;
				if(remaining >= 1.0)
					e.getPlayer().sendMessage(ChatColor.RED+"Weapon overheated! Wait "+ChatColor.GOLD+String.format(Locale.US,"%1$.1f", remaining)+ChatColor.RED+"s");
				return;
			}
		}
	
		if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
			
			if(weapon instanceof AmmunitionUser && !((AmmunitionUser)weapon).hasEnoughAmmoLeftClick(e.getPlayer())){
				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CLICK, 1.0F, 1.0F);
				return;
			}
			
			weapon.onLeftClick(e);			
		} else {
			if(weapon instanceof AmmunitionUser && !((AmmunitionUser)weapon).hasEnoughAmmoRightClick(e.getPlayer())){
				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CLICK, 1.0F, 0.75F);
				return;
			}
			weapon.onRightClick(e);
		}
	}
	


	@EventHandler
	public void onInventoryDrag(InventoryDragEvent e){
		Weapon weapon = plugin.getWeaponManager().getWeapon(e.getOldCursor());
		if(weapon == null) return;
		
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onInventoryDrag(InventoryClickEvent e){
		if(e.getAction().equals(InventoryAction.PLACE_ALL) || e.getAction().equals(InventoryAction.PICKUP_ALL)) return;
		Weapon weapon = plugin.getWeaponManager().getWeapon(e.getCursor());
		if(weapon == null) return;
		
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onProjectileLaunch(EntityShootBowEvent event){
		Weapon weapon = plugin.getWeaponManager().getWeapon(event.getBow());
		if(weapon == null) return;
		if(!(weapon instanceof BowWeapon)) return;
		((BowWeapon) weapon).onBowShoot(event);
	}
}
