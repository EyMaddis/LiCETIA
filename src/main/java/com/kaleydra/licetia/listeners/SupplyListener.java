/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import com.kaleydra.licetia.api.LiCETIAArenaPlugin;
import com.kaleydra.licetia.api.supplies.LiCETIASupply;
import com.kaleydra.licetia.arenas.Arena;
import com.kaleydra.licetia.arenas.ArenaManager;
import com.kaleydra.licetia.arenas.GamePhase;
import com.kaleydra.licetia.supplies.PlayerPickupSupplyEvent;
import com.kaleydra.licetia.supplies.SupplyStation;
import com.kaleydra.licetia.supplies.PlayerPickupSupplyEvent.AfterPickupEventState;

public class SupplyListener implements Listener {
	
	LiCETIAArenaPlugin plugin;
	

	public SupplyListener(LiCETIAArenaPlugin plugin) {
		this.plugin = plugin;
	}


	// prevent painting and drop destruction.
		@EventHandler(ignoreCancelled=false,priority = EventPriority.HIGHEST)
		public void onDamage(EntityDamageEvent e){
			if(e.getEntityType().equals(EntityType.PLAYER)) return;
			e.setCancelled(true);
		}
		
		// prevent painting and drop destruction.
		@EventHandler(ignoreCancelled=false,priority = EventPriority.HIGHEST)
		public void onDamage(HangingBreakEvent e){
			e.setCancelled(true);
		}
	
	
	
//	@EventHandler
//	public void onItemSwitch(PlayerItemHeldEvent e){
//		int level = 0;
//		
//		Player player = e.getPlayer();
//		ItemStack newItem = player.getInventory().getContents()[e.getNewSlot()];
//		
//		if(newItem != null && newItem.hasItemMeta() && newItem.getItemMeta().hasDisplayName()) {
//			Weapon weapon = plugin.getWeaponManager().getWeapon(newItem.getItemMeta().getDisplayName());
//			if(weapon != null){
//				level = weapon.getAmmo(player);
//			}
//		}
//		player.setLevel(level);				
//	}

	@EventHandler (priority=EventPriority.LOWEST)
	public void onItemPickup(PlayerPickupItemEvent e){
		ArenaManager arenaManager = plugin.getArenaManager();
		Arena arena = arenaManager.getArena(e.getPlayer());
		if(arena == null || !arena.getGamePhase().equals(GamePhase.RUNNING)){
			e.setCancelled(true);
			return;
		}
		
		if(!e.getItem().hasMetadata(SupplyStation.METADATA_NAME)){
			e.setCancelled(true);
			return;
		}
		
		ItemStack item = e.getItem().getItemStack();
		if(!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()){
			e.setCancelled(true);
			return;
		}
				
		String displayName = item.getItemMeta().getDisplayName();
		LiCETIASupply supply = arena.getSupplyManager().getSupply(displayName);
		if(supply == null){
			e.setCancelled(true);
			return;
		}
		PlayerPickupSupplyEvent supplyEvent = new PlayerPickupSupplyEvent(e, supply);
		supply.onPickup(supplyEvent);
		if(supplyEvent.isCancelled()){
			e.setCancelled(true);
			if(supplyEvent.getAfterPickupEventState().equals(AfterPickupEventState.DeletePickUpItem))
				e.getItem().remove();
		}
		if(supplyEvent.getAfterPickupEventState().equals(AfterPickupEventState.DoNothing))
			return;
			

		SupplyStation station = (SupplyStation) e.getItem().getMetadata(SupplyStation.METADATA_NAME).get(0);
		station.refill();
		
		e.getPlayer().getLocation().getWorld().playSound(e.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.0F);
		
	}
	
	@EventHandler
	public void onItemDespawn(ItemDespawnEvent e){
		if(!e.getEntity().hasMetadata(SupplyStation.METADATA_NAME)) return;
		e.setCancelled(true);
	}
		
	
	
}
