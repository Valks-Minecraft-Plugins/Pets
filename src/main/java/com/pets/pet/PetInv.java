package com.pets.pet;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Ocelot.Type;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;

import com.pets.Pets;
import com.pets.config.PlayerFiles;
import com.valkcore.modules.ItemModule;

public class PetInv implements Listener {
	@EventHandler
	private void catInv(PlayerInteractEntityEvent e) {
		if (e.getRightClicked().getType() == EntityType.OCELOT) {
			if (!e.getPlayer().isSneaking()) {
				e.setCancelled(true);
				e.getPlayer().openInventory(catMenu());
			}
		}
	}
	
	@EventHandler
	private void invClick(InventoryClickEvent e) {
		if (e.getView().getTitle().toLowerCase().equals("cat styles")) {
			e.setCancelled(true);
			
			Player p = (Player) e.getWhoClicked();
			
			UUID catUUID = Pets.storedPets.get(p.getUniqueId());
			Ocelot cat = (Ocelot) Bukkit.getEntity(catUUID);
			
			PlayerFiles cm = PlayerFiles.getConfig(p);
			FileConfiguration config = cm.getConfig();
			
			if (cat != null) {
				switch (e.getSlot()) {
				case 0:
					cat.setCatType(Type.BLACK_CAT);
					break;
				case 1:
					cat.setCatType(Type.RED_CAT);
					break;
				case 2:
					cat.setCatType(Type.SIAMESE_CAT);
					break;
				default:
					break;
				}
			}
			
			config.set("cat.type", cat.getCatType().name());
			cm.saveConfig();
			
			p.closeInventory();
		}
		if (e.getView().getTitle().toLowerCase().equals("cat")) {
			e.setCancelled(true);
			
			Player p = (Player) e.getWhoClicked();
			
			switch (e.getSlot()) {
			case 0:
				p.openInventory(p.getEnderChest());
				break;
			case 1:
				p.openInventory(catStyles());
				break;
			default:
				break;
			}
		}
	}
	
	private Inventory catStyles() {
		Inventory inv = Bukkit.createInventory(null, 9, "Cat Styles");
		inv.setItem(0, ItemModule.item("Black Cat", ";-;", Material.BLACK_WOOL));
		inv.setItem(1, ItemModule.item("Red Cat", ";-;", Material.RED_WOOL));
		inv.setItem(2, ItemModule.item("Siamese Cat", ";-;", Material.WHITE_WOOL));
		return inv;
	}
	
	private Inventory catMenu() {
		Inventory inv = Bukkit.createInventory(null, 9, "Cat");
		inv.setItem(0, ItemModule.item("Inventory", "Store Items", Material.ENDER_CHEST));
		inv.setItem(1, ItemModule.item("Style", "Change the Breed", Material.OCELOT_SPAWN_EGG));
		return inv;
	}
}
