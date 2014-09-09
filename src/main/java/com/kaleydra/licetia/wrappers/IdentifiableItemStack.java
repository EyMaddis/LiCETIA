package com.kaleydra.licetia.wrappers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;

import com.kaleydra.licetia.api.Identifiable;

@SerializableAs("IdentifiableItemStack")
public class IdentifiableItemStack implements ConfigurationSerializable, Identifiable {
	ItemStack item;
		
	public IdentifiableItemStack(ItemStack item) {
		this.item = item;
	}

	public IdentifiableItemStack(Map<String,Object> input){
		item = (ItemStack) input.get("item");
	}
	
	@Override
	public String getIdentifier() {
		String id = item.getType().name();
		if(item.hasItemMeta() && item.getItemMeta().hasDisplayName())
			id += "+"+item.getItemMeta().getDisplayName();
		return id;
	}

	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> output = new LinkedHashMap<String, Object>();
		output.put("item", item);
		return output;
	}

}
