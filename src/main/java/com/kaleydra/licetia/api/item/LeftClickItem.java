package com.kaleydra.licetia.api.item;

import org.bukkit.event.player.PlayerInteractEvent;

public interface LeftClickItem extends CustomItem {

	public void onLeftClick(PlayerInteractEvent event);
}
