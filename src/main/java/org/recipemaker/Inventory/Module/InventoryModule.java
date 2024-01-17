package org.recipemaker.Inventory.Module;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.recipemaker.Inventory.Enum.InventoryName;

import java.io.File;
import java.sql.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InventoryModule {
    private final ItemModule itemModule = new ItemModule();
    public static Material noneBlock = Material.BLACK_STAINED_GLASS_PANE;
    int x = 9;
    int y = 5;
    int size = x * y;
    int resultPosition = y / 2 * x - 2;
    HashMap<Integer, ItemStack> materialPosition = new HashMap<>();

    private Inventory makeInventory(String title, int size) {
        return Bukkit.createInventory(null, size, title);
    }

    public Inventory openRecipeMakerInventory() {

        Inventory inventory = makeInventory(InventoryName.RECIPE_MAKER.getName(), size);
        ItemStack itemStack = itemModule.setItem(noneBlock, 1, "");
        ItemStack materialItemStack = itemModule.setItem(Material.AIR, 1, "재료 아이템을 놓으세요!");
        ItemStack resultItemStack = itemModule.setItem(Material.AIR, 1, "만들어질 아이템을 놓으세요!");
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, itemStack);
        }
        for (int i = x; i < size - x; i += x) {
            for (int j = i + 2; j < i + 4; j++) {
                materialPosition.put(j, materialItemStack);
                inventory.setItem(j, materialItemStack);
            }
        }
        inventory.setItem(resultPosition, resultItemStack);

        return inventory;
    }

    public void getRecipeInConfig(Plugin plugin) {
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
        FileConfiguration config = plugin.getConfig();
        String main = "recipe";
        List<String> recipes = config.getStringList(main);
        for (String resultItem : recipes) {
            List<String> stringList = config.getStringList(main + "/" + resultItem);
            HashMap<Character, Material> recipeSet = new HashMap<>();
            String[] shape = null;
            Material material = Material.getMaterial(resultItem);
            assert  material != null;
            ItemStack itemStack = new ItemStack(material,1);
            for (String recipeOrData : stringList) {
                List<String> data = config.getStringList(main + "/" + resultItem + "/" + recipeOrData);
                for (String items : data) {
                    if (items.equalsIgnoreCase("recipe")) {
                        shape = items.split(",");
                    } else {
                        String recipeChar = config.getString(main + "/" + resultItem + "/" + recipeOrData + "/" + items);
                        assert recipeChar != null;
                        recipeSet.put(recipeChar.charAt(0),Material.getMaterial(items));
                    }
                }
            }
            setRecipe(plugin, itemStack, shape, recipeSet, resultItem);
        }
    }

    public void setRecipe(Plugin plugin, ItemStack itemStack, String[] shape, HashMap<Character, Material> material, String name) {
        NamespacedKey customRecipe = new NamespacedKey(plugin, "Custom" + name);
        ShapedRecipe recipe = new ShapedRecipe(customRecipe, itemStack).shape(shape);
        for (Map.Entry<Character, Material> entry : material.entrySet()) {
            recipe.setIngredient(entry.getKey(), entry.getValue());
        }
        plugin.getServer().addRecipe(recipe);
    }
}
