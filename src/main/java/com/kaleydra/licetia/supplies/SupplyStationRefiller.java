/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.supplies;

import org.bukkit.scheduler.BukkitRunnable;

import com.kaleydra.licetia.arenas.GamePhase;

public class SupplyStationRefiller extends BukkitRunnable {

	SupplyStation station;
		
	public SupplyStationRefiller(SupplyStation station) {
		this.station = station;
	}

	@Override
	public void run() {
		if(station.getArena().getGamePhase().equals(GamePhase.RUNNING))
			station.dropSupply();

		station.isRefilling = false;
	}

}
