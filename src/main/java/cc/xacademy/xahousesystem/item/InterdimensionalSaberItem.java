package cc.xacademy.xahousesystem.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import cc.xacademy.xahousesystem.util.TagUtil;

public class InterdimensionalSaberItem extends SpecialItem {

    public InterdimensionalSaberItem(String registryName) {
        super(registryName);
    }

    @Override
    public ItemStack createDefaultStack() {
        ItemStack stack = new ItemStack(Material.DIAMOND_SWORD);
        
        TagUtil.editMeta(stack, meta -> {
            meta.setDisplayName(ChatColor.GOLD + "Interdimensional Saber");
            
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.AQUA + "Attacks ALL enemy of the same kind!");
            lore.add(ChatColor.AQUA + "Also has infinite attack range");
            lore.add("");
            lore.add(ChatColor.DARK_GRAY + "[Left CLICK] on enemy to massacre its race");
            meta.setLore(lore);            
        });
        
        TagUtil.setupSpecialItem(stack, this.getRegistryName());
        
        return stack;
    }
    
    @Override
    public boolean onLeftClickLiving(ItemStack stack, Player player, LivingEntity living) {
        World world = player.getWorld();
        EntityType type = living.getType();
        
        world.getLivingEntities().forEach(i -> {
            if (i.getType() != type) return;
            
            if (type != EntityType.PLAYER) {
                System.out.println(i.getName());
            }
        });
        
        return false;
    }
}
