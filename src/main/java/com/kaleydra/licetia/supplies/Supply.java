/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.supplies;

import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.kaleydra.licetia.api.supplies.LiCETIASupply;

@SerializableAs("Supply")
public abstract class Supply implements LiCETIASupply {
	
	ItemStack drop;
	String identifier;
	
	public Supply(String identifier, ItemStack drop) {
		
		this.identifier = identifier;
		
		ItemMeta itemMeta = drop.getItemMeta();
		itemMeta.setDisplayName(identifier);
		drop.setItemMeta(itemMeta);
		this.drop = drop;
		
	}
	
	/* (non-Javadoc)
	 * @see com.kaleydra.licetia.supplies.LiCETIASupply#onPickup(com.kaleydra.licetia.supplies.PlayerPickupSupplyEvent)
	 */
	@Override
	public abstract void onPickup(PlayerPickupSupplyEvent e);

	/* (non-Javadoc)
	 * @see com.kaleydra.licetia.supplies.LiCETIASupply#getIdentifier()
	 */
	@Override
	public String getIdentifier(){
		return identifier;
	}
	
	/* (non-Javadoc)
	 * @see com.kaleydra.licetia.supplies.LiCETIASupply#getDrop()
	 */
	@Override
	public ItemStack getDrop() {
		return drop.clone();
	}
		
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((drop == null) ? 0 : drop.hashCode());
		return result;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Supply other = (Supply) obj;
		return isDrop(other.drop);
	}
	
	/* (non-Javadoc)
	 * @see com.kaleydra.licetia.supplies.LiCETIASupply#isDrop(org.bukkit.inventory.ItemStack)
	 */
	@Override
	public boolean isDrop(ItemStack item) {
		if (item == null)
			return false;
		
		if (drop == null)
			return false;
		
		if (drop.getTypeId() != item.getTypeId())
			return false;
		if (!item.hasItemMeta())
			return false;
		if (!drop.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName()))
			return false;
		
		return true;
	}

}
