package com.kaleydra.licetia.projectiles;

import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public interface CustomProjectile  {//extends ConfigurationSerializable {
	
	/**
	 * @param event canceled by default
	 * @return
	 */
	public void onHit(ProjectileHitEvent event);
	
	public void onHit(EntityDamageByEntityEvent entity);
	
	public Class<? extends Projectile> getProjectile();
	
}
