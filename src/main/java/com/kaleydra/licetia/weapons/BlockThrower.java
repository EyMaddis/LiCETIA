/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.weapons;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.kaleydra.licetia.LiCETIA;

// TODO: 
public abstract class BlockThrower  implements com.kaleydra.licetia.api.item.BlockThrower {
	
    Material blockMaterial;
	byte data;
    
	public BlockThrower(Material blockMaterial, byte data) {
		this.blockMaterial = blockMaterial;
		this.data = data;
	}
	
	
	/* (non-Javadoc)
	 * @see com.kaleydra.licetia.weapons.BlockThrower#throwBlock(org.bukkit.entity.Player, org.bukkit.util.Vector, double)
	 */
	@Override
	public void throwBlock(Player player, Vector direction, double force){
		direction.normalize();
		Location loc = player.getEyeLocation();
		FallingBlock block = loc.getWorld().spawnFallingBlock(loc, blockMaterial, data);
		block.setVelocity(direction.multiply(force));
		block.setDropItem(false);
		(new FallingBlockController(block)).runTaskTimer(LiCETIA.getInstance(), 1L, 1L);
	}
	
	/* (non-Javadoc)
	 * @see com.kaleydra.licetia.weapons.BlockThrower#onLanding(org.bukkit.Location)
	 */
	@Override
	public abstract void onLanding(Location location);

	private class FallingBlockController extends BukkitRunnable {
		
		FallingBlock fallingBlock;
		
		public FallingBlockController(FallingBlock fallingBlock){
			this.fallingBlock = fallingBlock;
		}

		@Override
		public void run() {
			if(fallingBlock.isDead()){
            	onLanding(fallingBlock.getLocation());
            	if(fallingBlock.getLocation().getBlock().getType().equals(blockMaterial))
            		fallingBlock.getLocation().getBlock().setType(Material.AIR);
            	
				cancel();
			} else if(!fallingBlock.isValid()){
				cancel();
			}
		}
	}
}
