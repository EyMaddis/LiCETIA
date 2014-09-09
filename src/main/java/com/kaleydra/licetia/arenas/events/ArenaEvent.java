/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.arenas.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.kaleydra.licetia.arenas.Arena;

public abstract class ArenaEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	 
	Arena arena;
	ArenaEvent(Arena arena){
		this.arena = arena;
	}
	
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
