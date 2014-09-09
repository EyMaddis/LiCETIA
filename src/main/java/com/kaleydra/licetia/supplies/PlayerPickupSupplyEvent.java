/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.supplies;

import org.bukkit.event.player.PlayerPickupItemEvent;

import com.kaleydra.licetia.api.supplies.LiCETIASupply;

public class PlayerPickupSupplyEvent extends PlayerPickupItemEvent {
	
	private LiCETIASupply supply;
	private AfterPickupEventState afterCancelation = AfterPickupEventState.AllowPickup;

	public PlayerPickupSupplyEvent(PlayerPickupItemEvent event, LiCETIASupply supply) {
		super(event.getPlayer(), event.getItem(), event.getRemaining());
		this.supply = supply;
	}
	
	@Override
	public String getEventName(){
		return "PlayerPickupSupplyEvent";
	}
	
	public LiCETIASupply getSupply(){
		return supply;
	}
	
	public void setAfterPickupEventState(AfterPickupEventState afterCancelation){
		if(afterCancelation == null) afterCancelation = AfterPickupEventState.DoNothing;
		 this.afterCancelation = afterCancelation;
	}
	
	public AfterPickupEventState getAfterPickupEventState(){
		return this.afterCancelation;
	}

	public enum AfterPickupEventState {
		DoNothing, DeletePickUpItem, AllowPickup
	}
}

