package org.recipemaker.Inventory.Module;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.recipemaker.RecipeMaker;

import javax.xml.stream.events.Namespace;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RecipeModule {
    int x = 9;
    int y = 6;
    int size = x * y;
    int resultPosition = (int) Math.ceil((double) y / 2) * x - 3;
    public final String mainYmlRecipe = "recipe";
    private final static HashMap<String, NamespacedKey> recipeNamespaceKey = new HashMap<>();
    HashMap<Integer, String> data = new HashMap<>() {{
        put(11, "A");
        put(12, "B");
        put(13, "C");
        put(20, "D");
        put(21, "E");
        put(22, "F");
        put(29, "G");
        put(30, "H");
        put(31, "I");
    }};


    public void makeRecipe(Inventory inventory) throws IOException {
        ItemStack resultItem = inventory.getItem(resultPosition);
        assert resultItem != null;
        FileConfiguration config = RecipeMaker.getPlugin().getConfig();

        HashMap<Material, Character> recipe = new HashMap<>();
        String[] recipeShape = new String[3];

        for (int i = x; i < size - x * 2; i += x) {
            StringBuilder lineRecipe = new StringBuilder();
            for (int j = i + 2; j < i + 5; j++) {
                ItemStack inventoryItem = inventory.getItem(j);
                if (inventoryItem != null) {
                    Material type = inventoryItem.getType();
                    if (!recipe.containsKey(type)) {
                        recipe.put(type, data.get(j).charAt(0));
                    }
                    lineRecipe.append(recipe.get(type));
                } else {
                    lineRecipe.append(" ");
                }
            }
            recipeShape[i / x - 1] = "\"" + String.valueOf(lineRecipe) + "\",";
        }
        recipeShape[2] = recipeShape[2].substring(0, recipeShape[2].length() - 1);

        // Material 등록
        for (Map.Entry<Material, Character> stringEntry : recipe.entrySet()) {
            Material key = stringEntry.getKey();
            String value = stringEntry.getValue().toString();
            config.set(mainYmlRecipe + "." + resultItem.getType().toString() + "." + key, value);
        }
        // recipe 쓰기
        StringBuilder writingRecipe = new StringBuilder();
        for (String value : recipeShape) {
            writingRecipe.append(value);
        }
        config.set(mainYmlRecipe + "." + resultItem.getType().toString() + ".recipe", writingRecipe.toString());
        config.save("plugins/RecipeMaker/config.yml");

        // recipe shape 만들기
        String[] shape = writingRecipe.toString().replace("\"","").split(",");

        setRecipe( resultItem, shape, recipe, resultItem.getType().toString());
    }
    public void getRecipeInConfig() {
        RecipeMaker.getPlugin().getConfig().options().copyDefaults(true);
        RecipeMaker.getPlugin().saveConfig();
        FileConfiguration config = RecipeMaker.getPlugin().getConfig();
        for (String resultItem : Objects.requireNonNull(config.getConfigurationSection(mainYmlRecipe)).getKeys(false)) {
            HashMap<Material, Character> recipeSet = new HashMap<>();
            String[] shape = null;
            Material material = Material.getMaterial(resultItem);
            assert material != null;
            ItemStack itemStack = new ItemStack(material, 1);
            for (String recipeOrData : Objects.requireNonNull(config.getConfigurationSection(mainYmlRecipe + "." + resultItem)).getKeys(false)) {
                if (recipeOrData.equalsIgnoreCase("recipe")) {
                    shape = Objects.requireNonNull(config.getString(mainYmlRecipe + "." + resultItem + "." + recipeOrData)).replace("\"", "").split(",");
                } else {
                    String recipeChar = config.getString(mainYmlRecipe + "." + resultItem + "." + recipeOrData);
                    assert recipeChar != null;
                    recipeSet.put(Material.getMaterial(recipeOrData), recipeChar.charAt(0));
                }
            }
            setRecipe(itemStack, shape, recipeSet, resultItem);
        }
    }

    public void setRecipe(ItemStack itemStack, String[] shape, HashMap<Material, Character> material, String name) {
        NamespacedKey customRecipe = new NamespacedKey(RecipeMaker.getPlugin(), "Custom" + name);
        ShapedRecipe recipe = new ShapedRecipe(customRecipe, itemStack).shape(shape);
        for (Map.Entry<Material, Character> entry : material.entrySet()) {
            recipe.setIngredient(entry.getValue(), entry.getKey());
        }
        RecipeMaker.getPlugin().getServer().addRecipe(recipe);
        recipeNamespaceKey.put(name, customRecipe);
    }

    public void deleteRecipe(Inventory inventory){
        FileConfiguration config = RecipeMaker.getPlugin().getConfig();
        String string = Objects.requireNonNull(inventory.getItem(resultPosition)).getType().toString();
        config.set(mainYmlRecipe + "." + string, null);
        NamespacedKey namespacedKey = recipeNamespaceKey.get(string);
        RecipeMaker.getPlugin().getServer().removeRecipe(namespacedKey);
    }
}
