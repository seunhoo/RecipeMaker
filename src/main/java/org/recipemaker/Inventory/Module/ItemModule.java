package org.recipemaker.Inventory.Module;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemModule {
    public ItemStack setItem(Material material, int amount, String title) {
        ItemStack prevItem = new ItemStack(material, amount);
        ItemMeta prevItemMeta = prevItem.getItemMeta();
        assert prevItemMeta != null;
        prevItemMeta.setDisplayName(title);
        prevItem.setItemMeta(prevItemMeta);
        return prevItem;
    }
}
