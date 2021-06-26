package cc.xacademy.xahousesystem.util;

import java.util.Map;

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
}
