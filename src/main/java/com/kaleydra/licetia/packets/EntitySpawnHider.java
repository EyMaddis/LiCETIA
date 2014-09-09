/*******************************************************************************
 * @author Mathis Neumann
 * @author Kaleydra.com Development Staff
 ******************************************************************************/
package com.kaleydra.licetia.packets;

import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

public class EntitySpawnHider extends PacketAdapter {

	boolean hideNext = false;
	public EntitySpawnHider(Plugin plugin) {
		super(plugin, ConnectionSide.SERVER_SIDE, new Integer[] {0x17});
		ProtocolLibrary.getProtocolManager().addPacketListener(this);
	}
	@Override
    public void onPacketSending(PacketEvent event) {                        
        // See if this packet should be cancelled
        if (hideNext) {
            event.setCancelled(true);
        }
    }
	
	public void hide(){
		hideNext = true;
	}
	
	
	public void show(){
		hideNext = false;
	}
}

