package cc.xacademy.xahousesystem.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import cc.xacademy.xahousesystem.HousePlugin;
import cc.xacademy.xahousesystem.util.TagUtil;

public class BuildersWandItem extends SpecialItem {

    public BuildersWandItem(String registryName) {
        super(registryName);
    }

    @Override
    public ItemStack createDefaultStack() {
        ItemStack stack = new ItemStack(Material.STICK);
        int size = HousePlugin.get().getConfig().getInt("buildersWandSize", 128);
        
        TagUtil.editMeta(stack, meta -> {
            meta.setDisplayName(ChatColor.GOLD + "Builder's Wand");
            
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.AQUA + "Max block place: " + size);
            lore.add(ChatColor.GRAY + "Extends an array of the same blocks");
            lore.add(ChatColor.GRAY + "Consumes blocks from your inventory");
            lore.add("");
            lore.add(ChatColor.DARK_GRAY + "[RIGHT CLICK] on block to extend");
            
            meta.setLore(lore);
        });
        
        TagUtil.addItemGlint(stack);
        TagUtil.setupSpecialItem(stack, "builders_wand");
        
        return stack;
    }

}
