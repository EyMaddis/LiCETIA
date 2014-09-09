/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.effects;

import org.bukkit.Material;

public class TileCrackParticleEffect extends DEPRECATEDParticleEffect {
	
	public TileCrackParticleEffect(Material material, byte data, float offsetX, float offsetY, float offsetZ,
			float speed, int count){
		super("tilecrack_"+material.getId()+"_"+data, offsetX, offsetY, offsetZ, speed, count);
	}
}
