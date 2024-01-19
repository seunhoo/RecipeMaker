package org.recipemaker.Inventory.Enum;

import java.util.HashMap;

public enum InventoryName {
    RECIPE_MAKER("레시피 메이커"),
    RECIPE_LIST("레시피 리스트"),
    RECIPE_DETAIL("아이템 레시피"),
    ;

    private final String name;

    InventoryName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    private static final HashMap<String, InventoryName> inventoryNameMap = new HashMap<>();

    static {
        for (InventoryName inventoryName : InventoryName.values()) {
            inventoryNameMap.put(inventoryName.getName(), inventoryName);
        }
    }

    public static InventoryName getInventoryName(String name) {
        return inventoryNameMap.getOrDefault(name, null);
    }
}
