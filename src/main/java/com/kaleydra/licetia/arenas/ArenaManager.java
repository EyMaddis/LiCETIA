/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.arenas;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.kaleydra.licetia.api.LiCETIAArenaPlugin;

public class ArenaManager {
	

	static Map<String, String> selectedArena = new HashMap<String, String>();
	
	public static String getSelectedArena(String playerName){
		return selectedArena.get(playerName);
	}
	
	public static void setSelectedArena(String playerName, String arenaName){
		selectedArena.put(playerName, arenaName);
	}
	
	/**
	 * gets the arena and sends notices to player if it fails
	 * @param commandSender
	 * @return null if no arena found
	 */
	public Arena getSelectedArena(CommandSender commandSender){
		if(getSelectedArena(commandSender.getName()) == null){
			commandSender.sendMessage(ChatColor.DARK_RED+"Select an arena first!");
			return null;
		}
		Arena selectedArena = plugin.getArenaManager().getArena(getSelectedArena(commandSender.getName()));
		if(selectedArena == null)
			commandSender.sendMessage(ChatColor.DARK_RED+"not existing arena selected! Select another one.");
			
		return selectedArena;
	}
	
	
	
	Map<String, Arena> arenas = new HashMap<String, Arena>();
	Map<String, Arena> arenaByPlayer = new HashMap<String, Arena>();
	
	private LiCETIAArenaPlugin plugin;
	File arenasFolder;
	
	public ArenaManager(LiCETIAArenaPlugin plugin){
		this.plugin = plugin;
		arenasFolder = new File(plugin.getDataFolder()+"/arenas/");
		arenasFolder.mkdirs();
	}
	
	public void addArena(Arena arena){
		arenas.put(arena.getName(), arena);
	}
	
	public void createArena(String arenaName){
		addArena(new Arena(plugin,new File(arenasFolder+"/"+arenaName)));
	}
	
	public boolean addPlayerToArena(String arenaName, Player player){
		if(player==null) return false;
		Arena arena = arenas.get(arenaName);
		if(arena == null) return false;
		
		Arena previousArena = arenaByPlayer.put(player.getName(), arena);
		if(previousArena != null){
			previousArena.removePlayer(player.getName());
		}
		arena.addPlayer(player);
		player.getInventory().clear();
		return true;
	}
	
	public void removePlayerFromArena(Player player){
		Arena arena = arenaByPlayer.get(player.getName());
		if(arena == null) return;
		arena.removePlayer(player.getName());
		arenaByPlayer.remove(player.getName());
		if(player != null) {
			player.getInventory().clear();
			player.setLevel(0);
			player.setExp(0.0F);
		}
	}
	
	
	/**
	 * 
	 * @param arenaName
	 * @return null if not found
	 */
	public Arena getArena(String arenaName){
		return arenas.get(arenaName);
	}
	
	public Arena getArena(Player player){
		return arenaByPlayer.get(player.getName());
	}
	
	public Set<String> getArenas(){
		return arenas.keySet();
	}
	
	public void stopAll(){
		for(Arena arena: arenas.values()){
			arena.stop();
		}
	}
	
	public void load(){
		int i = 0;
		for(File arenaFolder:arenasFolder.listFiles()){
			if(!arenaFolder.isDirectory()) continue;
			if(!(new File(arenaFolder+"/supplyStations.yml")).exists()){
				plugin.getLogger().warning("Arena "+arenaFolder.getName()+" is missing supplyStations.yml");
				continue;
			}
			plugin.getLogger().info("Loading Arena "+arenaFolder.getName()+":");
			Arena arena = new Arena(plugin, arenaFolder);
			arenas.put(arenaFolder.getName(), arena);
			arena.createSupplyManager();
			i++;
		}
		plugin.getLogger().info("Loaded "+i+" arenas!");
	}
	
	public void save(){
		for(Arena arena: arenas.values()){
			arena.save();
		}
	}
	
	public LiCETIAArenaPlugin getPlugin(){
		return plugin;
	}
	
	
}
