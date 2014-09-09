/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerQuitEvent;

import com.kaleydra.licetia.api.LiCETIAArenaPlugin;
import com.kaleydra.licetia.arenas.Arena;
import com.kaleydra.licetia.arenas.GamePhase;

//TODO: DOUBLE JUMP!
@Deprecated
public class PlayerHandler implements Listener {

	LiCETIAArenaPlugin plugin;
	
	//////////////////////////////////
	// NON-STATIC
	//////////////////////////////////
	
	public PlayerHandler(LiCETIAArenaPlugin plugin){
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){		
		Arena arena = plugin.getArenaManager().getArena(e.getPlayer());
		if(arena != null){
			if(arena.getGamePhase().equals(GamePhase.RUNNING)) // TODO: Lifes?
				e.setQuitMessage(ChatColor.RED+e.getPlayer().getDisplayName()+" left the arena and ran home.");
			plugin.getArenaManager().removePlayerFromArena(e.getPlayer());
		}
	}
	
	// Hunger
	
//	@EventHandler
//	public void onHunger(FoodLevelChangeEvent e){
//		e.setCancelled(true);
//	}
	
	@EventHandler
	public void onHungerLoss(EntityDamageEvent e){
		if(!(e.getEntity() instanceof Player)) return;	
		if(!e.getCause().equals(DamageCause.STARVATION)) return;
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onHealthRegain(EntityRegainHealthEvent e){
		if(!e.getEntityType().equals(EntityType.PLAYER)) return;
		
		RegainReason reason = e.getRegainReason();
		if(reason.equals(RegainReason.SATIATED) || reason.equals(RegainReason.REGEN)){
			e.setCancelled(true);
		}
	}
	
	
	
}
