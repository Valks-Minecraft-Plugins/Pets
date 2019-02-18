package com.pets;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.pets.config.LoadPlayerFiles;
import com.pets.pet.PetInv;
import com.pets.pet.UpdatePet;
import com.pets.pet.Utils;

public class Pets extends JavaPlugin {
	public static Map<UUID, UUID> storedPets = new HashMap<UUID, UUID>();
	
	@Override
	public void onEnable() {
		Server server = getServer();
		registerListeners(server);
		
		removeAllCats();
		spawnAllCats();
	}
	
	private void registerListeners(Server server) {
		PluginManager pm = server.getPluginManager();
		pm.registerEvents(new UpdatePet(), this);
		pm.registerEvents(new PetInv(), this);
		pm.registerEvents(new LoadPlayerFiles(), this);
	}
	
	private void removeAllCats() {
		for (World world : Bukkit.getWorlds()) {
			for (LivingEntity entity : world.getLivingEntities()) {
				if (entity.getType() == EntityType.OCELOT) {
					entity.remove();
				}
			}
		}
	}
	
	private void spawnAllCats() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			Utils.spawnCat(player);
		}
	}
}
