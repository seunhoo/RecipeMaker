package org.recipemaker.Inventory.Event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.recipemaker.Inventory.Enum.InventoryName;
import org.recipemaker.Inventory.Module.InventoryModule;

import java.io.IOException;

public class InventoryEvent implements Listener {
    private final InventoryModule inventoryModule = new InventoryModule();
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) throws IOException {

        if (event.getView().getTitle().equals(InventoryName.RECIPE_MAKER.getName())) {
            ItemStack item = event.getCurrentItem();
            if(item != null){
                switch (item.getType()){
                    case BLACK_STAINED_GLASS_PANE -> {
                        event.setCancelled(true);
                    }
                    case LIME_WOOL -> {
                        event.setCancelled(true);
                        inventoryModule.setRecipeInInventory(event.getInventory());
                        event.getView().close();
                    }
                    case RED_WOOL -> {
                        event.setCancelled(true);
                        event.getView().close();
                    }
                }
            }

        }
    }
}
