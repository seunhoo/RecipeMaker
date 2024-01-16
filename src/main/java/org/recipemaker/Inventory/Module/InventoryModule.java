package org.recipemaker.Inventory.Module;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.recipemaker.Inventory.Enum.InventoryName;

import java.sql.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        FileConfiguration config = plugin.getConfig();
        String main = "recipe";
        List<String> recipes = config.getStringList(main);
        for (String recipe : recipes) {
            HashMap<Character, Material> recipeSet = new HashMap<>();
            List<String> stringList = config.getStringList(main + "/" + recipe);
            for (String string : stringList) {
                List<String> data = config.getStringList(main + "/" + recipe + "/" + string);
                String[] qqq = null;
                for (String sss : data) {
                    if (sss.equals("recipe")) {
                        qqq = sss.split(",");
                    } else {
                        String www = config.getString(main + "/" + recipe + "/" + string + "/" + sss);
                        assert www != null;
                        recipeSet.put(www.charAt(0), Material.getMaterial(sss));
                    }
                }
            }

        }
    }

    public void setRecipe(Plugin plugin, ItemStack itemStack, String[] shape, HashMap<Character, Material> material, String name) {
        NamespacedKey customBlazeRodRecipe = new NamespacedKey(plugin, "Custom" + name);
        ShapedRecipe recipe = new ShapedRecipe(customBlazeRodRecipe, itemStack).shape(shape);
        for (Map.Entry<Character, Material> entry : material.entrySet()) {
            recipe.setIngredient(entry.getKey(), entry.getValue());
        }
        plugin.getServer().addRecipe(recipe);
    }
}
