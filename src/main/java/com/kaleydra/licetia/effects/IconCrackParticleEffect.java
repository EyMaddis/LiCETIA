/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.effects;

import org.bukkit.Material;

public class IconCrackParticleEffect extends DEPRECATEDParticleEffect {
	
	public IconCrackParticleEffect(Material material, float offsetX, float offsetY, float offsetZ,
			float speed, int count){
		super("iconcrack_"+material.getId(), offsetX, offsetY, offsetZ, speed, count);
	}
}
