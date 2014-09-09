package com.kaleydra.licetia.api.item;

import org.bukkit.event.player.PlayerItemConsumeEvent;

public interface ConsumableItem extends CustomItem {

	public void onConsume(PlayerItemConsumeEvent event);
}
