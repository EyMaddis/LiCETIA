package com.kaleydra.licetia.api;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class SerializableManager<Type extends Identifiable & ConfigurationSerializable> extends
		Manager<Type> {
	public final String YAML_KEY = "storage";

	/**
	 * loads data from yaml file, if ymlFile is a directory it will scan all .yml files
	 * @param ymlFile the file to load from
	 * @param ymlKey entry points in yml file
	 * @return false if there was an error
	 */
	public boolean load(@Nonnull File ymlFile){
		FileFilter filter = new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".yml");
			}
		};
		
		return load(ymlFile, filter);
	}

	/**
	 * loads data from yaml file, if ymlFile is a directory it will scan all files the filter accepts
	 * @param ymlFile the file to load from
	 * @param filter the filter that will get applied if it scans in a directory
	 * @return false if there was an error
	 */
	@SuppressWarnings("unchecked")
	public boolean load(@Nonnull File ymlFile, FileFilter filter){
		super.clear();
		if(!ymlFile.exists()) return false;
		
		boolean error = false;
		File[] files = new File[]{ ymlFile };
		if(ymlFile.isDirectory()){
			files = ymlFile.listFiles(filter);
		}
		YamlConfiguration yml = new YamlConfiguration();
		for(File file: files){
			try {
				yml.load(file);
				
				if(yml.contains(YAML_KEY)){
					for(Type data: (List<Type>)yml.getList(YAML_KEY)){
						add(data);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				error = true;
			}	
		}
		 
		return !error;
	}

	/**
	 * save data to yaml file, if ymlFile is a directory, it will store everything with identifier.yml
	 * @see Identifiable
	 * @param ymlFile the file to save to
	 * @return false if error occurred
	 */
	public boolean save(@Nonnull File ymlFile){
		return save(ymlFile, new FileNameGenerator<Type>() {
			
			@Override
			public String getName(Type obj) {
				return obj.getIdentifier()+".yml";
			}
		});
	}
	
	/**
	 * save data to yaml file, if ymlFile is a directory
	 * @see Identifiable
	 * @param ymlFile the file to save to
	 * @param namer a object of {@link FileNameGenerator} that will generate a name for the result file(s).
	 * Only used if ymlFile is a directory!
	 * @return false if error occurred
	 */
	public boolean save(@Nonnull File ymlFile, FileNameGenerator<Type> namer){
		File[] files = new File[]{ymlFile};
		if(ymlFile.isDirectory()){
			files = new File[data.size()];
			int i=0;
			for(String identifier : data.keySet()){
				files[i] = new File(ymlFile,namer.getName(data.get(identifier)));
				i++;
			}
		}
		boolean error = false;
		boolean first = true;
		for(File file:files){
			if(!ymlFile.exists()){
				try {
					if(first) file.getParentFile().mkdirs();
					file.createNewFile();
					first = false;
				} catch (IOException e) {
					e.printStackTrace();
					error = true;
				}
			}
			
			YamlConfiguration yml = new YamlConfiguration();
			try {
				yml.set(YAML_KEY, data.values().toArray());
				yml.save(file);
			} catch (Exception e) {
				error = true;
				e.printStackTrace();
			}	
		}
		return !error;
	}
	
	public interface FileNameGenerator<Type> {
		
		/**
		 * returns a file name for an object 
		 * @param obj
		 * @return the name
		 */
		public String getName(Type obj);
	}
}
