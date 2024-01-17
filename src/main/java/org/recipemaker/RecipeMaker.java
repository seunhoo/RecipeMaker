package org.recipemaker;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.recipemaker.Inventory.Command.InventoryCommand;
import org.recipemaker.Inventory.Module.InventoryModule;

public final class RecipeMaker extends JavaPlugin {
    private BukkitAudiences adventure;
    private FileConfiguration config;

    private InventoryModule inventoryModule = new InventoryModule();
    public @NonNull BukkitAudiences adventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.adventure = BukkitAudiences.create(this);
        inventoryModule.getRecipeInConfig(this);

        InventoryCommand inventoryCommand = new InventoryCommand(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }
}
