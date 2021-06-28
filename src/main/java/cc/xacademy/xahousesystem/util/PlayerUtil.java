package cc.xacademy.xahousesystem.util;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtil {

    /**
     * Tries to add to the player's inventory. If full,
     * drop excess stuff on the ground.
     */
    public static void addToInventory(Player player, ItemStack... stack) {
        Map<Integer, ItemStack> excess = player.getInventory().addItem(stack);
        
        for (ItemStack i: excess.values()) {
            player.getWorld().dropItem(player.getLocation(), i);
        }
    }
    
    /**
     * Takes a given amount of item from a player.
     * 
     * @return the amount of items that are missing
     */
    public static int takeAwayMaterial(Player player, Material mat, int amount) {
        Map<Integer, ? extends ItemStack> items = player.getInventory().all(mat);
        
        for (Entry<Integer, ? extends ItemStack> i: items.entrySet()) {
            ItemStack stack = i.getValue();
            
            if (stack.getAmount() >= amount) {
                stack.setAmount(stack.getAmount() - amount);

                return 0;
            }
            
            amount -= stack.getAmount();
            stack.setAmount(0);
        }
        
        return amount;
    }
}
