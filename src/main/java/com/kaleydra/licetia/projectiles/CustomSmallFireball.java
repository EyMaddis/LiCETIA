package com.kaleydra.licetia.projectiles;

import java.lang.reflect.Constructor;

import net.minecraft.server.v1_6_R3.Entity;
import net.minecraft.server.v1_6_R3.EntityLiving;
import net.minecraft.server.v1_6_R3.EntitySmallFireball;
import net.minecraft.server.v1_6_R3.EnumMovingObjectType;
import net.minecraft.server.v1_6_R3.MovingObjectPosition;
import net.minecraft.server.v1_6_R3.World;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_6_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
//TODO: Unnötig?
public class CustomSmallFireball extends EntitySmallFireball{


//	public CustomSmallFireball(World world, double d0, double d1, double d2, double d3, double d4, double d5) {
//		super(world, d0, d1, d2, d3, d4, d5);
//	}
//
//	public CustomSmallFireball(World world, EntityLiving entityliving, double d0, double d1, double d2) {
//		super(world, entityliving, d0, d1, d2);
//	}
//
//	public CustomSmallFireball(World world) {
//		super(world);
//	}
 
	public CustomSmallFireball(org.bukkit.World world, LivingEntity shooter, double x, double y, double z) {
		super(((CraftWorld)world).getHandle(), ((CraftLivingEntity)shooter).getHandle(), x, y, z);
	}

	@Override
	protected void a(MovingObjectPosition pos) {
		Location hitLocation = new Location(this.world.getWorld(), pos.pos.c, pos.pos.d, pos.pos.e);
		
		org.bukkit.entity.Entity hitEntity = null;
		if(pos.type.equals(EnumMovingObjectType.ENTITY)){
			hitEntity = (org.bukkit.entity.Entity) pos.entity.getBukkitEntity();			
		}
		
		if(!onHit(hitLocation, hitEntity)) return;
		super.a(pos);
	}
	
	/**
	 * Override for custom stuff.
	 * @param hitLocation
	 * @param hitEntity null if nothing hitted
	 * @return true if the default mc behavior should get applied.
	 */
	public boolean onHit(Location hitLocation, org.bukkit.entity.Entity hitEntity){
		return true;
	}
	
	public static boolean launch(Player player, Class<? extends CustomSmallFireball> clazz){
		
		CraftPlayer craftPlayer = (CraftPlayer) player;

        World world = ((CraftWorld) craftPlayer.getWorld()).getHandle();
        Vector direction = player.getLocation().getDirection().multiply(1);
        try {
        	Constructor<? extends CustomSmallFireball> constructor = 
        			clazz.getConstructor(World.class, EntityLiving.class, Double.TYPE, Double.TYPE, Double.TYPE);
        	
			Entity launch = constructor.newInstance(world, craftPlayer.getHandle(), direction.getX(), direction.getY(), direction.getZ());

            Location location = player.getEyeLocation();
			launch.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
			world.addEntity(launch);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return false;
	}

}
