/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
 package com.kaleydra.licetia.supplies;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import com.kaleydra.licetia.LiCETIA;
import com.kaleydra.licetia.api.LiCETIAArenaPlugin;
import com.kaleydra.licetia.arenas.Arena;
import com.kaleydra.licetia.wrappers.HashableLocation;


@SerializableAs("SupplyStation")
public class SupplyStation implements MetadataValue, ConfigurationSerializable {
	
	public static final String METADATA_NAME = "kaleydra_supplystation";

	private LiCETIAArenaPlugin plugin;
	
	HashableLocation location;
	List<Supply> supplies = new ArrayList<Supply>();
	int cooldown; // in ticks
	long lastRefillTime = 0; // time in milliseconds
	
	boolean isRefilling = false;
	
	//TODO: Save the item that is dropped to avoid multiple drops at once!
	
	Arena arena;
	
	public SupplyStation(Arena arena, HashableLocation location, List<Supply> supplies,
			int cooldown) throws Exception {
		this.arena = arena;
		this.location = location;
		this.supplies = supplies;
		this.cooldown = cooldown;
		if(arena == null) {
			plugin.getLogger().severe("ARENA == NULL (command) for "+location);
			throw new Exception();
		}
	}
	
	@SuppressWarnings("unchecked")
	public SupplyStation(Map<String, Object> config) throws Exception{
		
		// TODO: Observe!
//		if(config.get("plugin") == null) config.put("plugin", "UnrealTournament");
		
		plugin = (LiCETIAArenaPlugin) LiCETIA.getInstance();
		
		arena = plugin.getArenaManager().getArena((String) config.get("arena"));
		location = new HashableLocation(config);
		
		List<String> supplyNames = (List<String>) config.get("supplies");
		supplies = new ArrayList<Supply>();
		Supply supply;
		int i=0;
		for(String supplyName: supplyNames){
			supply = SupplyManager.getExistingSupply(supplyName);
			if(supply != null) {
				supplies.add(supply);
				i++;
				continue;
			}
			plugin.getLogger().severe("Invalid supplyname "+supplyName);
		}
		plugin.getLogger().info("SupplyStation loaded with "+i+" supplies");
		if(arena == null) {
			plugin.getLogger().severe("ARENA == NULL (config) for "+location);
			throw new Exception();
		}
				
		cooldown = (int) config.get("cooldown");
		lastRefillTime = 0;
	}
	
	public void dropSupply(){
		int i = 0;
		int size = supplies.size();
		if(size <= 0) return;
		i = (int) Math.round((size-1)*Math.random());
		
		//Math.rint(arg0) could make this easier if arg0 is a delimiter for the random
		Location loc = location.getBukkitLocation();
		loc = loc.add(0.5, 0.5, 0.5); // center of the block
		Item item = loc.getWorld().dropItem(loc,supplies.get(i).getDrop());
		
		item.setVelocity(new Vector(0.0,0.5,0.0));
		
		loc.getWorld().playEffect(loc, Effect.CLICK1, 0);
		loc.getWorld().playEffect(loc, Effect.ENDER_SIGNAL, 0);
		item.setMetadata(METADATA_NAME, this); // drops have the station they are coming from as Metadata
		lastRefillTime = System.currentTimeMillis();
	}
	
	public void refill(){
		if(isRefilling) return;
		SupplyStationRefiller refiller = new SupplyStationRefiller(this);
		isRefilling = true;
		refiller.runTaskLater(plugin, cooldown);
	}
	
	public boolean isStocked(){
		for(Entity entity:location.getBukkitLocation().getChunk().getEntities()){
			if(!(entity instanceof Item)) continue;
//			if(entity.getLocation().distance(location.getBukkitLocation()) > 2) return false;
			if(entity.hasMetadata(METADATA_NAME) 
					&& entity.getMetadata(METADATA_NAME).get(0).equals(this))
				return true; // drops have the station they are coming from as Metadata
		}
		return false;
	}
	
	public Arena getArena(){
		return arena;
	}
	
	/**
	 * @return the location
	 */
	public HashableLocation getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(HashableLocation location) {
		this.location = location;
	}
	/**
	 * @return the supplies
	 */
	public List<Supply> getSupplies() {
		return supplies;
	}
	/**
	 * @param supplies the supplies to set
	 */
	public void setSupplies(List<Supply> supplies) {
		this.supplies = supplies;
	}
	/**
	 * @return the cooldown
	 */
	public int getCooldown() {
		return cooldown;
	}
	/**
	 * @param cooldown the cooldown to set
	 */
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	/**
	 * @return the lastRefillTime
	 */
	public long getLastRefillTime() {
		return lastRefillTime;
	}

	/**
	 * @param lastRefillTime the lastRefillTime to set
	 */
	public void setLastRefillTime(long lastRefillTime) {
		this.lastRefillTime = lastRefillTime;
	}
	
	
	// Serialization

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> output = location.serialize();
		output.put("plugin", plugin.getName());
		output.put("arena", arena.getName());
		List<String> supplyNames = new LinkedList<String>();
		for(Supply supply: supplies){
			supplyNames.add(supply.getIdentifier());
		}
		output.put("supplies", supplyNames);
		output.put("cooldown", cooldown);
		
		return output;
	}
	
	
	
	// Metadata implementation

	@Override
	public boolean asBoolean() {
		return false;
	}

	@Override
	public byte asByte() {
		return 0;
	}

	@Override
	public double asDouble() {
		return 0;
	}

	@Override
	public float asFloat() {
		return 0;
	}

	@Override
	public int asInt() {
		return 0;
	}

	@Override
	public long asLong() {
		return 0;
	}

	@Override
	public short asShort() {
		return 0;
	}

	@Override
	public String asString() {
		return null;
	}

	@Override
	public Plugin getOwningPlugin() {
		return plugin;
	}

	@Override
	public void invalidate() {
		refill();		
	}

	@Override
	public Object value() {
		return (Object) this;
	}
	

	public long getNextRefillTime(){
		return lastRefillTime+cooldown*50;
	}

	public boolean refillReady(){
		long currentTime = System.currentTimeMillis();
		if(currentTime >= getNextRefillTime()){
			return true;
		}
		return false;
	}
	

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SupplyStation other = (SupplyStation) obj;
		if (arena == null) {
			if (other.arena != null)
				return false;
		} else if (!arena.getName().equals(other.arena.getName()))
			return false;
		if (cooldown != other.cooldown)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}

}
