/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.supplies.special;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.kaleydra.licetia.supplies.PlayerPickupSupplyEvent;
import com.kaleydra.licetia.supplies.PlayerPickupSupplyEvent.AfterPickupEventState;

public class Healthpack extends PotionSupply {

	public Healthpack(String identifier, ItemStack drop, int amplifier){
		super(identifier, drop,new PotionEffect(PotionEffectType.HEAL, 1, amplifier));
	}
	
	@Override
	public void onPickup(PlayerPickupSupplyEvent e) {
		if(e.getPlayer().getHealth() < e.getPlayer().getMaxHealth()) {
			super.onPickup(e);
		} else {
			e.setCancelled(true);
			e.setAfterPickupEventState(AfterPickupEventState.DoNothing);
		}
	}
}
