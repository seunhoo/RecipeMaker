package org.recipemaker.Inventory.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.recipemaker.Inventory.Module.InventoryModule;
import org.recipemaker.RecipeMaker;

import java.util.Objects;

public class InventoryCommand implements CommandExecutor {
    private final InventoryModule inventoryModule = new InventoryModule();
    public InventoryCommand(RecipeMaker recipeMaker) {
        Objects.requireNonNull(recipeMaker.getCommand("rm")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equals("rm")) {
            if (args.length == 0) {
                sender.sendMessage("/rm make        " + ChatColor.AQUA + "레시피를 만듭니다!");
                sender.sendMessage("/rm list        " + ChatColor.BLUE + "레시피를 확인합니다!");
            } else {
                switch (args[0]) {
                    case "make" -> recipeMake(sender);
                    case "list" -> recipeList(sender);
                }
            }
        }
        return true;
    }

    public void recipeMake(CommandSender sender) {
        Player player = (Player)sender;
        player.openInventory(inventoryModule.openRecipeMakerInventory());
    }

    public void recipeList(CommandSender sender) {
        Player player = (Player)sender;
        player.openInventory(inventoryModule.openRecipeListInventory());
    }
}
