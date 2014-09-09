/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.arenas;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class ArenaInfo {
	
	Arena arena;
	
	/**
	 * stores information about the status of a arena/game
	 */
	YamlConfiguration information = new YamlConfiguration();
	
	public ArenaInfo(Arena arena){
		this.arena = arena;

	}

	public boolean contains(Player player){
		return information.contains("players."+player.getName());
	}

	public boolean contains(Player player, String path){
		return information.contains("players."+player.getName()+"."+path);
	}

	public boolean contains(String path){
		return information.contains(path);
	}
	
	public void set(String path, Object value){
		information.set(path, value);
	}
	
	public void setPlayerInfo(Player player, String path, Object value){
		information.set("players."+player.getName()+"."+path, value);
	}
	
	public void deleteNode(String path){
		information.set(path, null);
	}
	
	public void deleteArena(Arena arena){
		deleteNode(arena.getName());
	}
	
	public void deletePlayer(Player player){
		deleteNode("players."+player.getName());
	}
	
	public long getPlayerInfoAsLong(Player player, String pathIdentifier){
		return information.getLong("players."+player.getName()+"."+pathIdentifier);
	}

	public String getPlayerInfoAsString(Player player, String pathIdentifier){
		return information.getString("players."+player.getName()+"."+pathIdentifier);
	}
	
	public int getPlayerInfoAsInt(Player player, String pathIdentifier){
		return information.getInt("players."+player.getName()+"."+pathIdentifier);
	}

	public boolean getPlayerInfoAsBoolean(Player player, String pathIdentifier){
		return information.getBoolean("players."+player.getName()+"."+pathIdentifier);
	}

	public double getPlayerInfoAsDouble(Player player, String pathIdentifier){
		return information.getDouble("players."+player.getName()+"."+pathIdentifier);
	}

	public Object getPlayerInfo(Player player, String pathIdentifier){
		return information.get("players."+player.getName()+"."+pathIdentifier);
	}
}
