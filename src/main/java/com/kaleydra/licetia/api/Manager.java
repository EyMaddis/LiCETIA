package com.kaleydra.licetia.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;


public class Manager<Type extends Identifiable> {
	
	Map<String,Type> data = new HashMap<String, Type>();
	
	public void add(@Nonnull Type data){
		if(data == null) return;
		this.data.put(data.getIdentifier(), data);
	}

	public boolean contains(@Nonnull String identifier){
		return this.data.containsKey(identifier);
	}
	
	public Type get(@Nonnull String identifier){
		return this.data.get(identifier);
	}
	
	public Type remove(@Nonnull String identifier){
		return this.data.remove(identifier);
	}
	
	public Collection<Type> getData(){
		return data.values();
	}
	
	public void clear(){
		data.clear();
	}
	
}
