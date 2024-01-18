package org.recipemaker.Inventory.Event;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.recipemaker.Inventory.Enum.InventoryName;
import org.recipemaker.Inventory.Module.InventoryModule;

import java.util.Objects;

public class InventoryEvent implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(Objects.requireNonNull(event.getCurrentItem()).getType() == InventoryModule.noneBlock) {
            event.setCancelled(true);
        }
        if (event.getView().getTitle().equals(InventoryName.RECIPE_MAKER.getName())) {

        }
    }
}
