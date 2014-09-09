package com.kaleydra.licetia.wrappers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@SerializableAs("SerializablePlayerInventory")
public class SerializablePlayerInventory implements ConfigurationSerializable {

	ItemStack[] contents;
	ItemStack[] armorContents;
	
	public SerializablePlayerInventory(PlayerInventory  playerInventory){
		contents = playerInventory.getContents();
		armorContents = playerInventory.getArmorContents();
	}
	
	@SuppressWarnings("unchecked")
	public SerializablePlayerInventory(Map<String,Object> input){
		contents = ((ArrayList<ItemStack>) input.get("contents")).toArray(new ItemStack[0]);
		armorContents = ((ArrayList<ItemStack>) input.get("armorContents")).toArray(new ItemStack[0]);
	}
	
	public void applyInventory(@Nonnull Player player){
		final PlayerInventory inventory = player.getInventory();
		if(contents != null) inventory.setContents(contents);
		if(armorContents != null) inventory.setArmorContents(armorContents);
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> output = new LinkedHashMap<String, Object>();
		output.put("contents", contents);
		output.put("armorContents", armorContents);
		return output;
	}

	/**
	 * @return the contents
	 */
	public ItemStack[] getContents() {
		return contents;
	}

	/**
	 * @return the armorContents
	 */
	public ItemStack[] getArmorContents() {
		return armorContents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(ItemStack[] contents) {
		this.contents = contents;
	}

	/**
	 * @param armorContents the armorContents to set
	 */
	public void setArmorContents(ItemStack[] armorContents) {
		this.armorContents = armorContents;
	}
}
