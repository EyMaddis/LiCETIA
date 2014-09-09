/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.arenas.events;

import com.kaleydra.licetia.arenas.Arena;
import com.kaleydra.licetia.arenas.GamePhase;

/**
 * event which gets called if the arena switches to the {@link GamePhase} "Running"
 */
public class ArenaStartEvent extends ArenaEvent {

	ArenaStartEvent(Arena arena) {
		super(arena);
	}

}
