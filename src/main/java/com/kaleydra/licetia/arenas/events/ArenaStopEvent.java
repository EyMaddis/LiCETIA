package com.kaleydra.licetia.arenas.events;

import com.kaleydra.licetia.arenas.Arena;
import com.kaleydra.licetia.arenas.GamePhase;

public class ArenaStopEvent extends ArenaEvent {

/**
 * event which gets called if the arena switches to the {@link GamePhase} "Stopping"
 */
	ArenaStopEvent(Arena arena) {
		super(arena);
	}

}
