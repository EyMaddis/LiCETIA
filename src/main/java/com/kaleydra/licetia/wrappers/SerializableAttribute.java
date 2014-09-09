package com.kaleydra.licetia.wrappers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import com.kaleydra.licetia.nbt.Attributes.Attribute;
import com.kaleydra.licetia.nbt.Attributes.AttributeType;
import com.kaleydra.licetia.nbt.Attributes.Operation;

@SerializableAs("CustomAttributeModifier")
public class SerializableAttribute implements ConfigurationSerializable {
	
	String name;
	String minecraftId;
	double amount;
	Operation operation;
	
	public SerializableAttribute(Attribute attribute){
		name = attribute.getName();
		minecraftId = attribute.getAttributeType().getMinecraftId();
		amount = attribute.getAmount();
		operation = attribute.getOperation();
	}
	
	public SerializableAttribute(Map<String, Object> input){
		this.name = input.get("name").toString();
		this.minecraftId = input.get("minecraftId").toString();
		this.amount = (Double)input.get("amount");
		this.operation = Operation.fromId((int) input.get("operation"));
	}
	
	public Attribute getAttribute(){
		return Attribute.newBuilder().name(name).amount(amount)
				.type(AttributeType.fromId(minecraftId)).operation(operation).build();
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String,Object> output = new LinkedHashMap<String,Object>();
		output.put("name", name);
		output.put("minecraftId", minecraftId);
		output.put("amount", amount);
		output.put("operation", operation.getId());
		return output;
	}

}
