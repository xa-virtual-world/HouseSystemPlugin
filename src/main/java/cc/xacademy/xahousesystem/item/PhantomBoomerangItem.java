package cc.xacademy.xahousesystem.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.inventory.ItemStack;

import cc.xacademy.xahousesystem.util.TagUtil;

public class PhantomBoomerangItem extends SpecialItem {

    public PhantomBoomerangItem(String registryName) {
        super(registryName);
    }

    @Override
    public ItemStack createDefaultStack() {
        ItemStack stack = new ItemStack(Material.NAUTILUS_SHELL);
        
        TagUtil.editMeta(stack, meta -> {
            meta.setDisplayName(ChatColor.GOLD + "Spell: Blink");
            
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Smite your enemies");
            lore.add("");
            lore.add(ChatColor.DARK_GRAY + "[RIGHT CLICK] to summon trident");
            
            meta.setLore(lore);            
        });
        
        TagUtil.setupSpecialItem(stack, this.getRegistryName());
        TagUtil.addItemGlint(stack);
        
        return stack;
    }
    
    @Override
    public void onRightClickAir(ItemStack stack, Player player) {
        World world = player.getWorld();
        Entity entity = world.spawnEntity(player.getEyeLocation(), EntityType.TRIDENT);
        
        // sanity check in case other plugins hitchhike spawning
        if (entity instanceof Trident) { 
            Trident trident = (Trident) entity;
            
        }
    }
}
