package com.pets.pet;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class UpdatePet implements Listener {
	@EventHandler
	private void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		Utils.spawnCat(p);
	}
	
	@EventHandler
	private void onPlayerLeave(PlayerQuitEvent e) {
		Utils.despawnCat(e.getPlayer().getUniqueId());
	}
}
