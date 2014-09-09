/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.effects;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.injector.PacketConstructor;
import com.kaleydra.licetia.LiCETIA;
@Deprecated
public class DEPRECATEDParticleEffect {
	String type; 
	float offsetX; 
	float offsetY; 
	float offsetZ; 
	float speed;
	int count;
	private static PacketConstructor packetConstructor;
	static {
		// constructor parameters: 
		// String name, float x, float y, float z, float offsetX, float offsetY, float offsetZ, 
		// float speed and int count
		Object[] parameters = new Object[]{ "particleName", 0F, 0F, 0F, 0F, 0F, 0F, 0F , 1};
		
		packetConstructor = PacketConstructor.DEFAULT.withPacket(63, parameters);
	}
	
	public DEPRECATEDParticleEffect(ParticleType type, float offsetX, float offsetY, float offsetZ,
			float speed, int count) {
		this(type.getParticleName(), offsetX, offsetY, offsetZ, speed, count);
	}
	
	protected DEPRECATEDParticleEffect(String type, float offsetX, float offsetY, float offsetZ,
			float speed, int count){
		this.type = type;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.speed = speed;
		this.count = count;	
	}
	
	/**
	 * sends a particle effect packet to the client at given coordinates
	 * @param player Player that should see the effect
	 * @param x 
	 * @param y
	 * @param z
	 */
	public void send(Player player, float x, float y, float z){
		PacketContainer particleEffect = packetConstructor.createPacket(type,
				x, y, z, offsetX, offsetY, offsetZ, speed, count);
		try {
			LiCETIA.getProtocolManager().sendServerPacket(player, particleEffect);
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * sends a particle effect packet to the client
	 * @param player Player that should see the effect
	 * @param location where should the effect happen?
	 */
	public void send (Player player, Location location){
		send(player, (float)location.getX(), (float)location.getY(), (float)location.getZ());
	}

	/**
	 * plays an effect at a location
	 * @param location where should the particles appear?
	 * @param radius 
	 */
	public void send(Location location, double radius){
		double squaredRadius = Math.pow(radius, 2.0); 
		
		for(Player player: location.getWorld().getPlayers()){
			if(location.distanceSquared(player.getLocation()) > squaredRadius) continue;
			send(player, location);
		}
	}
	
	/**
	 * 
	 * @param location
	 */
	public void send(Location location){
		send(location, 16);
	}

	/**
	 * @return the type
	 */
	public ParticleType getType() {
		return ParticleType.valueOf(type);
	}


	/**
	 * @return the offsetX
	 */
	public float getOffsetX() {
		return offsetX;
	}


	/**
	 * @return the offsetY
	 */
	public float getOffsetY() {
		return offsetY;
	}


	/**
	 * @return the offsetZ
	 */
	public float getOffsetZ() {
		return offsetZ;
	}


	/**
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}


	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	
	
}
