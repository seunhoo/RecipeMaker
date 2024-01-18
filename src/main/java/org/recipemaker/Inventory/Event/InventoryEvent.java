package org.recipemaker.Inventory.Event;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.recipemaker.Inventory.Enum.InventoryName;
import org.recipemaker.Inventory.Module.InventoryModule;
import org.recipemaker.Inventory.Module.RecipeModule;

import java.io.IOException;

public class InventoryEvent implements Listener {
    private final InventoryModule inventoryModule = new InventoryModule();
    private final RecipeModule recipeModule = new RecipeModule();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) throws IOException {
        String title = event.getView().getTitle();
        ItemStack item = event.getCurrentItem();
        if (item != null) {
            if (title.equals(InventoryName.RECIPE_MAKER.getName())) {
                switch (item.getType()) {
                    case BLACK_STAINED_GLASS_PANE -> {
                        event.setCancelled(true);
                    }
                    case LIME_WOOL -> {
                        event.setCancelled(true);
                        recipeModule.makeRecipe(event.getInventory());
                        event.getView().close();
                        HumanEntity player = event.getView().getPlayer();
                        player.sendMessage(ChatColor.AQUA + "새로운 레시피가 등록되었습니다.");
                    }
                    case RED_WOOL -> {
                        event.setCancelled(true);
                        event.getView().close();
                    }
                }
            } else if (title.equals(InventoryName.RECIPE_LIST.getName())) {
                event.setCancelled(true);
                Inventory inventory = inventoryModule.openItemRecipe(item.getType());
                HumanEntity player = event.getView().getPlayer();
                player.openInventory(inventory);
            } else if (title.equals(InventoryName.RECIPE_DETAIL.getName())) {
                event.setCancelled(true);
                if (item.getType() == InventoryModule.cancelBlock){
                    event.getView().close();
                    HumanEntity player = event.getView().getPlayer();
                    player.openInventory(inventoryModule.openRecipeListInventory());
                }
            }
        }
    }
}
