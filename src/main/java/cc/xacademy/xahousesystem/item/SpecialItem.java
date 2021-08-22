package cc.xacademy.xahousesystem.item;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import cc.xacademy.xahousesystem.registry.IRegistryEntry;

/**
 * A singleton representation of a custom item on the server.
 * This should also act as a factory for the represented ItemStack. 
 */
public abstract class SpecialItem extends IRegistryEntry<SpecialItem> {

    public SpecialItem(String registryName) {
        super(registryName);
    }
    
    public void onLeftClickAir(ItemStack stack, Player player) {}
    public void onRightClickAir(ItemStack stack, Player player) {}
    
    // returns whether to cancel the default event
    public boolean onLeftClickBlock(ItemStack stack, Player player, Block block, BlockFace face) {
        return false;
    }
    
    public boolean onLeftClickLiving(ItemStack stack, Player player, LivingEntity living) {
        return false;
    }
    
    public boolean onRightClickBlock(ItemStack stack, Player player, Block block, BlockFace face) {
        return false;
    }
    
    public boolean onRightClickLiving(ItemStack stack, Player player, LivingEntity living) {
        return false;
    }
    
    public boolean onItemBreak(ItemStack stack, Player player) {
        return false;
    }
    
    public abstract ItemStack createDefaultStack();
}
