package cc.xacademy.xahousesystem.item;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import cc.xacademy.xahousesystem.registry.IRegistryEntry;

/**
 * A singleton representation of a custom item on the server.
 * This should also act as a factory for the represented ItemStack. 
 */
public class SpecialItem extends IRegistryEntry<SpecialItem> {

    public void onRightClickAir(ItemStack stack, Player player) {}
    
    public void onRightClickBlock(ItemStack stack, Player player, Block block) {}
    
    public void onRightClickLiving(ItemStack stack, Player player, LivingEntity living) {}
}
