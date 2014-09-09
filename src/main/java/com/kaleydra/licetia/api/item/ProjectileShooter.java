package com.kaleydra.licetia.api.item;

import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public interface ProjectileShooter {
	
	/**
	 * get the projectile this weapon should shoot with
	 * @return
	 * @see Projectile
	 */
	public Projectile getProjectile();
	
	/**
	 * what happens if a projectile of this weapon hits something
	 * @param event
	 */
	public void onProjectileHit(ProjectileHitEvent event);
	
	/**
	 * what happens if the projectile of this weapon hits an entity
	 * @param event
	 */
	public void onProjectileHit(EntityDamageEvent event);
	
}
