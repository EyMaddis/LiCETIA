/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.supplies;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import com.kaleydra.licetia.api.LiCETIAArenaPlugin;
import com.kaleydra.licetia.api.supplies.LiCETIASupply;
import com.kaleydra.licetia.arenas.Arena;
import com.kaleydra.licetia.wrappers.HashableLocation;

public class SupplyManager {
	
	LiCETIAArenaPlugin plugin;
	
	static Map<String, Supply> existingSupplies = new HashMap<String,Supply>();
	
	public static void addExistingSupply(Supply supply){
		existingSupplies.put(supply.getIdentifier().toLowerCase(),supply);
	}
	
	public static Supply getExistingSupply(String identifier){
		return existingSupplies.get(identifier.toLowerCase());
	}
	
	public static List<Supply> getExistingSupplies(){
		return new ArrayList<Supply>(existingSupplies.values());
	}
	
	
	/** all possible supplies with identifier as key */
	Map<String, Supply> supplies = new HashMap<String, Supply>();
	
	// stations
	List<SupplyStation> supplyStations = new ArrayList<SupplyStation>();
	Map<HashableLocation, SupplyStation> supplyStationByLocation = new HashMap<HashableLocation, SupplyStation>();
	
	// serialization
	File supplyStationsFile;
	YamlConfiguration suppliesYAML = new YamlConfiguration();
	YamlConfiguration supplyStationYAML = new YamlConfiguration();
	Arena arena;
	
	public SupplyManager(LiCETIAArenaPlugin plugin, Arena arena) {
		this.plugin = plugin;
		this.arena = arena;
		supplyStationsFile = new File(arena.getArenaFolder() + "/supplyStations.yml");
		
		if(!supplyStationsFile.exists()){
			try {
				supplyStationsFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void addSupplyStation(SupplyStation supplyStation) {
		for(Supply supply: supplyStation.getSupplies()){
			if(!supplies.containsKey(supply.getIdentifier()))
				addSupply(supply);
		}
		supplyStations.add(supplyStation);
		supplyStationByLocation.put(supplyStation.getLocation(), supplyStation);
	}
	
	public SupplyStation getSupplyStation(Location location) {
		return supplyStationByLocation.get(new HashableLocation(location));
	}
	
	public List<SupplyStation> getSupplyStations(){
		return supplyStations;
	}
	
	public void addSupply(Supply supply) {
		if (supply == null)
			return;
		supplies.put(supply.getIdentifier().toLowerCase(), supply);
	}
	
	/** 
	 * @param identifier mostly the display name
	 * @return
	 */
	public LiCETIASupply getSupply(String identifier){
		return supplies.get(identifier.toLowerCase());
	}
		
	@SuppressWarnings("unchecked")
	public void load() {
		try {
			supplyStationYAML.load(supplyStationsFile);
			supplyStations = new ArrayList<SupplyStation>();
			supplyStationByLocation.clear();
			
			supplies.clear();
			
			List<SupplyStation>ymlSupplyStations = (List<SupplyStation>) supplyStationYAML.getList("supplyStations");
			if(ymlSupplyStations == null) ymlSupplyStations = new ArrayList<SupplyStation>();
			
			for (SupplyStation supplyStation : ymlSupplyStations) {
				addSupplyStation(supplyStation);
			}
			plugin.getLogger().info("Loaded " + supplyStations.size() + " supplyStations");
			
		} catch (Exception e) {
			plugin.getLogger().severe("SupplyStations could not get loaded!");
			e.printStackTrace();
			return;
		}
		
	}
	
	public void save() {
		try {
			supplyStationYAML.set("supplyStations", supplyStations);
			supplyStationYAML.save(supplyStationsFile);
		} catch (Exception e) {
			plugin.getLogger().severe("Supplies could not get saved!");
			e.printStackTrace();
			return;
		}
	}
	
}
