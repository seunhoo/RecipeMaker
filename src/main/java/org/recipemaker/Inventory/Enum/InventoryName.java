package org.recipemaker.Inventory.Enum;

public enum InventoryName {
    RECIPE_MAKER("레시피 메이커"),
    RECIPE_LIST("레시피 리스트"),
    RECIPE_DETAIL("아이템 레시피"),
    ;

    private final String name;
    InventoryName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
