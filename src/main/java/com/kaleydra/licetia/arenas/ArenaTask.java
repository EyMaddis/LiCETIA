/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.arenas;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.kaleydra.licetia.api.LiCETIAArenaPlugin;
import com.kaleydra.licetia.supplies.SupplyStation;

public class ArenaTask extends BukkitRunnable {
	LiCETIAArenaPlugin plugin;
	Arena arena;
	int runningTime = 0; // in seconds
	final int startUpCountdown = 5; // in seconds
	
	public ArenaTask(LiCETIAArenaPlugin plugin, Arena arena) {
		this.arena = arena;
		this.plugin = plugin;
	}
	
	
	@Override
	public void run() {
		if(!arena.arenaTaskShouldRun()){
			this.cancel();
			return;
		}
		if(runningTime <= startUpCountdown){ // round starting
			Player player;
			for(String playerName:arena.getPlayersInArena()){
				player = Bukkit.getPlayerExact(playerName);
				if(player == null || !player.isOnline()){
					arena.removePlayer(playerName);
					continue;
				}
				int countdown = startUpCountdown-runningTime;
				player.setLevel(countdown);
				if(countdown == 0){
					player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
					
					player.sendMessage(ChatColor.GOLD+ChatColor.BOLD.toString()+"Match begins!");
					
					for(SupplyStation station : arena.getSupplyManager().getSupplyStations()){
						station.dropSupply();
					}
				} else {
					player.playEffect(player.getLocation(), Effect.CLICK1, 0);
					player.sendMessage(ChatColor.GREEN+"Match starting in "+countdown+" seconds!");
					player.setExp(0);
					
				}				
			}
		}
		runningTime ++;

	}

}
