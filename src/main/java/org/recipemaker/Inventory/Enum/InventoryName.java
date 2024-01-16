package org.recipemaker.Inventory.Enum;

public enum InventoryName {
    RECIPE_MAKER("레시피 메이커"),
    ;

    private final String name;
    InventoryName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
