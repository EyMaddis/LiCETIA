package com.kaleydra.licetia.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import com.kaleydra.licetia.projectiles.CustomProjectile;

public class CustomProjectileHelper implements Listener {

	private static Map<Integer, CustomProjectile> flyingProjectiles = new HashMap<Integer, CustomProjectile>();
	
	public static Projectile launch(LivingEntity shooter, CustomProjectile projectile){
		Projectile projectileEntity = shooter.launchProjectile(projectile.getProjectile());		
		flyingProjectiles.put(projectileEntity.getEntityId(), projectile);
		return projectileEntity;
	}
	
	public static Projectile launch(LivingEntity shooter, CustomProjectile projectile, Vector velocity){
		Projectile projectileEntity = launch(shooter, projectile);
		projectileEntity.setVelocity(velocity);
		return projectileEntity;
	}
	
	public static boolean isCustomProjectile(Projectile entity){
		return flyingProjectiles.containsKey(entity.getEntityId());
	}
	
	@EventHandler(ignoreCancelled=true)
	public void onProjectileHit(ProjectileHitEvent event){
		CustomProjectile customProjectile = flyingProjectiles.get(event.getEntity().getEntityId());
		if(customProjectile == null) {
//			KRPG.getInstance().getLogger().info("nicht projectile1!");
			return;
		}
		customProjectile.onHit(event);
		flyingProjectiles.remove(event.getEntity().getEntityId());
	}
	
	@EventHandler(ignoreCancelled=true)
	public void onProjectileHit(EntityDamageByEntityEvent event){
		if(!event.getCause().equals(DamageCause.PROJECTILE)){
//			KRPG.getInstance().getLogger().info("nicht projectile2!");
			return;
		}		
		CustomProjectile customProjectile = flyingProjectiles.get(event.getEntity().getEntityId());
		if(customProjectile == null) return;
		customProjectile.onHit(event);
		flyingProjectiles.remove(event.getEntity().getEntityId());
	}
}
