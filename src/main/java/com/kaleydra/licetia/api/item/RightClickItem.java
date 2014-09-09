package com.kaleydra.licetia.api.item;

import org.bukkit.event.player.PlayerInteractEvent;

public interface RightClickItem extends CustomItem {

	public void onRightClick(PlayerInteractEvent event);
}
