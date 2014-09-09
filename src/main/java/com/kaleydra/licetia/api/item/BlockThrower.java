package com.kaleydra.licetia.api.item;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public interface BlockThrower {
	/**
	 * Get the {@link Material} of the Block that will get thrown
	 * @return
	 */
	public Material getBlock();
	
	/**
	 * the data value of the block
	 * @return
	 */
	public byte getData();
	
	/**
	 * Will throw a block and start a {@link BukkitRunnable} to observe the flying entity
	 * @param player
	 * @param direction will get normalized
	 * @param force multiplier for the normalized direction
	 */
	public void throwBlock(Player player, Vector direction, double force);
	
	/**
	 * gets called once the block touches the ground
	 * @param location the Location the block landed on
	 */
	public void onLanding(Location location);
	
}