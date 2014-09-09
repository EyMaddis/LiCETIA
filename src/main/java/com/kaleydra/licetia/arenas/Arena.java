/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.arenas;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.kaleydra.licetia.LiCETIA;
import com.kaleydra.licetia.api.LiCETIAArenaPlugin;
import com.kaleydra.licetia.supplies.SupplyManager;
import com.kaleydra.licetia.supplies.SupplyStation;

public class Arena {

	String name;
	Set<String> playersInArena = new HashSet<String>();
	SupplyManager supplyManager;
	File arenaFolder;
	private LiCETIAArenaPlugin plugin;
	
	ArenaInfo arenaInfo;
	
	GamePhase gamePhase = GamePhase.STOPPED; 
	ArenaTask arenaTask = null;
	
	/**
	 * arenaFolder.getName() will be the identifier/name for this arena
	 * @param arenaFolder must not be null!
	 */
	public Arena(LiCETIAArenaPlugin plugin, File arenaFolder){
		this.plugin = plugin;
		this.name = arenaFolder.getName();
		this.arenaFolder = arenaFolder;
		if(!arenaFolder.exists() || !arenaFolder.isDirectory()) arenaFolder.mkdirs();
		 this.arenaInfo = new ArenaInfo(this);
	}
		
	public void addPlayer(Player player){
		playersInArena.add(player.getName());
		player.sendMessage(ChatColor.GREEN+"You joined arena: "+ChatColor.GOLD+name);
	}
	
	public void removePlayer(String playerName){
		playersInArena.remove(playerName);
	}
	
	public boolean containsPlayer(String playerName){
		return playersInArena.contains(playerName);
	}

	public void sendMessageToPlayers(String[] message){
		Player player;
		for(String playerName: playersInArena){
			player = Bukkit.getPlayerExact(playerName);
			if(player == null || !player.isOnline()){
				removePlayer(playerName);
				continue;
			}
			player.sendMessage(message);
		}
	}
	public void sendMessageToPlayers(String message){
		String[] messageArray = new String[1];
		messageArray[0] = message;
		sendMessageToPlayers(messageArray);
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the playersInArena
	 */
	public Set<String> getPlayersInArena() {
		return playersInArena;
	}

	public void createSupplyManager(){
		if(supplyManager == null){
			supplyManager = new SupplyManager(plugin, this);
			supplyManager.load();
		}
	}
	
	/**
	 * @return the supplyManager
	 */
	public SupplyManager getSupplyManager() {
		createSupplyManager();
		return supplyManager;
	}
	
	/**
	 * @return the arenaFolder
	 */
	public File getArenaFolder() {
		return arenaFolder;
	}
	
	/**
	 * @return the gamePhase
	 */
	public GamePhase getGamePhase() {
		return gamePhase;
	}
	
	public ArenaInfo getArenaInfo(){
		return arenaInfo;
	}
	
	public boolean arenaTaskShouldRun(){
		if(this.getGamePhase().equals(GamePhase.STOPPED)) return false;
		return true;
	}
	
	public void start(){
		createSupplyManager();
		arenaTask = new ArenaTask(plugin, this);
		gamePhase = GamePhase.RUNNING;
		arenaTask.runTaskTimer(plugin, 0, 20L); // after start wait 5 seconds and then drop every second
		plugin.getLogger().info("Starting arena "+name);
	}
	
	public void stop(){
		if(gamePhase.equals(GamePhase.STOPPED)) return;
		gamePhase = GamePhase.STOPPED;
		if(arenaTask != null) arenaTask.cancel();
		plugin.getLogger().info("Stopping arena "+name);
		
		sendMessageToPlayers(ChatColor.RED+ChatColor.BOLD.toString()+"Stopping Arena...");
		Player p; 
		for(String playerName: playersInArena){
			p = Bukkit.getPlayerExact(playerName);
			plugin.getArenaManager().removePlayerFromArena(p);
		}
		for(SupplyStation station: supplyManager.getSupplyStations()){
			Location location = station.getLocation().getBukkitLocation();
			LiCETIA.clearItems(location.getChunk());
		}
	}
	
	public void save(){
		if(supplyManager != null){
			supplyManager.save();
			plugin.getLogger().info("Saved arena "+name+"!");
		} else {
			plugin.getLogger().info("SupplyManager for "+name+" not saved because it was never loaded");
		}
	}
	
	public LiCETIAArenaPlugin getPlugin(){
		return plugin;
	}
}
