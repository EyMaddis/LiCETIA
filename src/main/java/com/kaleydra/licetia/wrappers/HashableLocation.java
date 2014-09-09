/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.wrappers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("Location")
public class HashableLocation implements ConfigurationSerializable {
	
	private int x;
	private int y;
	private int z;
	private String world;	
	
	public HashableLocation(String world, int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
	}

	public HashableLocation(Location loc) {
		this.x = loc.getBlockX();
		this.y = loc.getBlockY();
		this.z = loc.getBlockZ();
		this.world = loc.getWorld().getName();
	}
	
	public HashableLocation(Map<String, Object> config){
		this.world = (String) config.get("world");
		this.x = (int) config.get("x");
		this.y = (int) config.get("y");
		this.z = (int) config.get("z");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((world == null) ? 0 : world.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HashableLocation other = (HashableLocation) obj;
		if (world == null) {
			if (other.world != null)
				return false;
		} else if (!world.equals(other.world))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Location [x=" + x + ", y=" + y + ", z=" + z + ", world=" + world + "]";
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}


	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the z
	 */
	public int getZ() {
		return z;
	}


	/**
	 * @return the world
	 */
	public String getWorld() {
		return world;
	}
	
	public Location getBukkitLocation(){
		return new Location(Bukkit.getWorld(world),(double)x,(double)y,(double)z);
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("world", world);
		output.put("x", x);
		output.put("y", y);
		output.put("z", z);
		return output;
	}
	
}
