package com.pets.pet;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Ocelot.Type;
import org.bukkit.scheduler.BukkitRunnable;

import com.pets.Pets;
import com.pets.config.PlayerFiles;

public class Utils {
	public static void spawnCat(Player p) {
		// Spawn the Cat
		Ocelot cat = (Ocelot) p.getWorld().spawnEntity(p.getLocation(), EntityType.OCELOT);
		
		PlayerFiles cm = PlayerFiles.getConfig(p);
		FileConfiguration config = cm.getConfig();
		if (!config.isSet("cat.type")) {
			cat.setCatType(Type.BLACK_CAT);
		} else {
			cat.setCatType(Type.valueOf(config.getString("cat.type")));
		}
		cat.setAgeLock(true);
		cat.setBreed(false);
		cat.setOwner(p);
		cat.setTamed(true);
		cat.setInvulnerable(true);
		cat.setBaby();
		
		// Register the Cat
		Pets.storedPets.put(p.getUniqueId(), cat.getUniqueId());
		
		new BukkitRunnable() {
			int ticksLived = 0;
			
			@Override
			public void run() {
				if (ticksLived == cat.getTicksLived()) {
					despawnCat(p.getUniqueId());
					if (p.isOnline()) {
						spawnCat(p);
					}
					cancel();
				}
				ticksLived = cat.getTicksLived();
			}
		}.runTaskTimer(Pets.getPlugin(Pets.class), 20, 20 * 60);
	}
	
	public static void despawnCat(UUID playerUUID) {
		if (Pets.storedPets.containsKey(playerUUID)) {
			UUID petUUID = Pets.storedPets.get(playerUUID);
			LivingEntity pet = (LivingEntity) Bukkit.getEntity(petUUID);
			if (pet != null) {
				pet.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 10000, 3));
				pet.setHealth(0);
				pet.remove();
			}
			
			Pets.storedPets.remove(playerUUID);
		}
	}
}
