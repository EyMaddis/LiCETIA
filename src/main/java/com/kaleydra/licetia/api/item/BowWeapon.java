/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.api.item;

import org.bukkit.event.entity.EntityShootBowEvent;


public interface BowWeapon extends ProjectileShooter {

	public void onBowShoot(EntityShootBowEvent event);
	
}
