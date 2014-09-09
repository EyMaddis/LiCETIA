/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.supplies.special;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.kaleydra.licetia.supplies.PlayerPickupSupplyEvent;
import com.kaleydra.licetia.supplies.Supply;
import com.kaleydra.licetia.supplies.PlayerPickupSupplyEvent.AfterPickupEventState;


public class PotionSupply extends Supply {

	PotionEffect effect;
	
	public PotionSupply(String identifier, ItemStack drop, PotionEffect effect) {		
		super(identifier, drop);
		this.effect = effect;
	}

	@Override
	public void onPickup(PlayerPickupSupplyEvent e) {
		e.getPlayer().addPotionEffect(effect, false);
		e.setCancelled(true);
		e.setAfterPickupEventState(AfterPickupEventState.DeletePickUpItem);
	}
	

}
