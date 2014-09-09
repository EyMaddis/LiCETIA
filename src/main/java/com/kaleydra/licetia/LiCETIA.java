/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.kaleydra.licetia.api.ItemManager;
import com.kaleydra.licetia.api.LiCETIAPlugin;
import com.kaleydra.licetia.listeners.CustomProjectileHelper;
import com.kaleydra.licetia.wrappers.HashableLocation;
import com.kaleydra.licetia.wrappers.IdentifiableItemStack;
import com.kaleydra.licetia.wrappers.SerializableAttribute;
import com.kaleydra.licetia.wrappers.SerializablePlayerInventory;

public class LiCETIA extends JavaPlugin implements LiCETIAPlugin {

	/** 2* Math.PI */
	public static final double PI_TWO = 2 * Math.PI;
	private static ProtocolManager protocolManager;
	private static LiCETIA instance;
	
	private ItemManager itemManager;

	static Integer[] interactiveBlocks = new Integer[]{54,64,69,77,92,96,107,143,146};
//	private static ArenaManager arenaManager;
	static {
		Arrays.sort(interactiveBlocks);
	}

	
	@Override
	public void onEnable(){
		ConfigurationSerialization.registerClass(SerializableAttribute.class, "CustomAttributeModifier");
		ConfigurationSerialization.registerClass(IdentifiableItemStack.class, "IdentifiableItemStack");
		ConfigurationSerialization.registerClass(SerializablePlayerInventory.class, "SerializablePlayerInventory");
		ConfigurationSerialization.registerClass(HashableLocation.class, "HashableLocation");
		
		instance = this;
//		arenaManager = new ArenaManager(this);
		protocolManager = ProtocolLibrary.getProtocolManager();
		itemManager = new ItemManager();
		
		getServer().getPluginManager().registerEvents(new CustomProjectileHelper(), this);
		
//		new CustomItemListener(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.isOp()){
			sender.sendMessage(ChatColor.DARK_RED+"Nope");
			return true;
		}
		sender.sendMessage(ChatColor.GREEN+""+this.getName()+": v"+this.getDescription().getVersion());
		return true;
	}
	
	

	/**
	 * @return the itemManager
	 */
	public ItemManager getItemManager() {
		return itemManager;
	}
	
	
	/*
	 * STATIC
	 */
	public static LiCETIA getInstance(){
		return instance;
	}
	
//	public ArenaManager getArenaManager() {
//		return arenaManager ;
//	}

	public static ProtocolManager getProtocolManager(){
		return protocolManager;
	}
	
	public static Integer[] getInteractiveBlocks(){
		return interactiveBlocks;
	}
	
	/**
	 * check if a block id is interactable by minecraft
	 * examples are chests, doors, etc.
	 * Works in O(lg(n)) due to a binary search on the blocks
	 * @param blockId
	 * @return
	 * @see LiCETIA#getInteractiveBlocks()
	 */
	public static boolean isInteractiveBlock(int blockId){
		return Arrays.binarySearch(interactiveBlocks, blockId) >= 0;
	}
	

	/**
	 * delete all dropped items in a chunk
	 * @param chunk the chunk to clear
	 */
	public static void clearItems(Chunk chunk){
		for(Entity entity : chunk.getEntities()){
			if(entity instanceof Item) {
				entity.remove();
			}
		}
	}
	
	/**
	 * get all nearby entities based on a location.
	 * @param location the center of the radius check
	 * @param radius
	 * @return array of all found entities
	 */
	public static Entity[] getNearbyEntities(Location location, double radius){
		Validate.notNull(location, "Location must not be null!");
		Validate.isTrue(radius <= 0, "Radius must be above 0");
		double chunkRadius = radius < 16 ? 1 : (radius - (radius % 16))/16;
		HashSet<Entity> radiusEntities = new HashSet<Entity>();
        for (double chX = 0 -chunkRadius; chX <= chunkRadius; chX ++){
            for (double chZ = 0 -chunkRadius; chZ <= chunkRadius; chZ++){
                int x=(int) location.getX(),y=(int) location.getY(),z=(int) location.getZ();
                for (Entity e : new Location(location.getWorld(),x+(chX*16),y,z+(chZ*16)).getChunk().getEntities()){
                    if (e.getLocation().distance(location) <= radius && e.getLocation().getBlock() != location.getBlock()) radiusEntities.add(e);
                }
            }
        }
        return radiusEntities.toArray(new Entity[radiusEntities.size()]);
    }
	/**
	 * Get players within a circle of another entity
	 * @param entity
	 * @param radius of circle
	 * @since 0.1.1
	 * @return
	 */
	public static List<Player> getNearbyPlayers(Entity entity, double radius){
		Validate.notNull(entity, "Can't get nearby players of a not existing entity! NullPointer");
		Validate.isTrue(radius > 0, "getNearbyPlayers radius must be above 0!");
		return getNearbyPlayers(entity, radius, radius, radius);
	}
	
	/**
	 * Get players within a ellipsis of an entity
	 * @param entity center of the ellipsis
	 * @param radiusX ellipsis width x
	 * @param radiusY ellipsis height
	 * @param radiusZ ellipsis width z
	 * @return
	 */
	public static List<Player> getNearbyPlayers(Entity entity, double radiusX, double radiusY, double radiusZ){
		Validate.isTrue(radiusX > 0, "radiusX must be above 0");
		Validate.isTrue(radiusY > 0, "radiusY must be above 0");
		Validate.isTrue(radiusZ > 0, "radiusZ must be above 0");
		List<Player> players = new ArrayList<Player>();
		for(Entity e:entity.getNearbyEntities(radiusX, radiusY, radiusZ)){
			if(e instanceof Player) players.add((Player)e);
		}
		return players;
	}
	
	/**
	 * get points on the circumference of a circle (on layer from x and z).
	 * All points are on the same height of the center
	 * @param center
	 * @param radius
	 * @param pointAmount
	 * @return
	 */
	public static List<Location> getCircleCircumferenceLocations(Location center, double radius, int pointAmount){
		Validate.isTrue(radius > 0, "radius must be above 0!");
		Validate.isTrue(pointAmount > 0, "pointAmount musst be above 0!");
		List<Location> points = new ArrayList<Location>();
		double step =  PI_TWO / pointAmount;
		
		double x = center.getX();
		double y = center.getY();
		double z = center.getZ();
		
		for (double theta = 0; theta < PI_TWO; theta += step) {
			points.add(new Location(center.getWorld(), 
					(x + radius * Math.cos(theta)), 
					y, 
					(z - radius * Math.sin(theta))));
		}
		
		return points;
		
	}
	
}
