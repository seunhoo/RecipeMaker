package org.recipemaker.Inventory.Module;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.recipemaker.Inventory.Enum.InventoryName;
import org.recipemaker.RecipeMaker;

import java.io.IOException;
import java.util.*;

public class InventoryModule {
    private final ItemModule itemModule = new ItemModule();
    public static Material noneBlock = Material.BLACK_STAINED_GLASS_PANE;
    public static Material acceptBlock = Material.LIME_WOOL;
    public static Material cancelBlock = Material.RED_WOOL;
    int x = 9;
    int y = 6;
    int size = x * y;
    int resultPosition = (int) Math.ceil((double) y / 2) * x - 3;
    String mainYmlRecipe = "recipe";
    HashMap<Integer, ItemStack> materialPosition = new HashMap<>();

    private Inventory makeInventory(String title, int size) {
        return Bukkit.createInventory(null, size, title);
    }

    public Inventory openRecipeMakerInventory() {
        Inventory inventory = makeInventory(InventoryName.RECIPE_MAKER.getName(), size);
        ItemStack itemStack = itemModule.setItem(noneBlock, 1, " ");
        ItemStack materialItemStack = itemModule.setItem(Material.AIR, 1, "재료 아이템을 놓으세요!");
        ItemStack resultItemStack = itemModule.setItem(Material.AIR, 1, "만들어질 아이템을 놓으세요!");
        ItemStack acceptItemStack = itemModule.setItem(acceptBlock, 1, "확인");
        ItemStack cancelItemStack = itemModule.setItem(cancelBlock, 1, "취소");
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, itemStack);
        }
        for (int i = x; i < size - x * 2; i += x) {
            for (int j = i + 2; j < i + 5; j++) {
                materialPosition.put(j, materialItemStack);
                inventory.setItem(j, materialItemStack);
            }
        }
        inventory.setItem(resultPosition, resultItemStack);
        inventory.setItem(size - 4, acceptItemStack);
        inventory.setItem(size - 6, cancelItemStack);

        return inventory;
    }

    public Inventory openRecipeListInventory() {
        Inventory inventory = makeInventory(InventoryName.RECIPE_LIST.getName(), size);
        FileConfiguration config = RecipeMaker.getPlugin().getConfig();
        int temp = 0;
        for (String resultItem : Objects.requireNonNull(config.getConfigurationSection(mainYmlRecipe)).getKeys(false)) {
            Material material = Material.getMaterial(resultItem);
            if (material == null)
                continue;
            inventory.setItem(temp, new ItemStack(material, 1));
            temp++;
        }
        return inventory;
    }

    public Inventory openItemRecipe(Material material) {
        Inventory inventory = makeInventory(InventoryName.RECIPE_LIST.getName(), size);
        FileConfiguration config = RecipeMaker.getPlugin().getConfig();
        Objects.requireNonNull(config.getConfigurationSection(mainYmlRecipe + material.toString())).getKeys(false);
        ItemStack itemStack = itemModule.setItem(noneBlock, 1, " ");
        ItemStack materialItemStack = itemModule.setItem(Material.AIR, 1, "재료 아이템을 놓으세요!");
        ItemStack resultItemStack = itemModule.setItem(Material.AIR, 1, "만들어질 아이템을 놓으세요!");
        ItemStack returnItemStack = itemModule.setItem(cancelBlock, 1, "뒤로가기");
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, itemStack);
        }
        for (int i = x; i < size - x * 2; i += x) {
            for (int j = i + 2; j < i + 5; j++) {
                materialPosition.put(j, materialItemStack);
                inventory.setItem(j, materialItemStack);
            }
        }
        inventory.setItem(resultPosition, resultItemStack);
        inventory.setItem(size - 5, returnItemStack);
        return inventory;
    }

}
