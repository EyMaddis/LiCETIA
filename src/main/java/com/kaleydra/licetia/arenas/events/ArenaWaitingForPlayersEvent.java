package com.kaleydra.licetia.arenas.events;

import com.kaleydra.licetia.arenas.Arena;
import com.kaleydra.licetia.arenas.GamePhase;

public class ArenaWaitingForPlayersEvent extends ArenaEvent {


/**
 * event which gets called if the arena switches to the {@link GamePhase} "WAITING_FOR_PLAYERS"
 */
	ArenaWaitingForPlayersEvent(Arena arena) {
		super(arena);
	}
}
