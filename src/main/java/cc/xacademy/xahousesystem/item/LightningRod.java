package cc.xacademy.xahousesystem.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import cc.xacademy.xahousesystem.util.TagUtil;
import net.md_5.bungee.api.ChatColor;

public class LightningRod extends SpecialItem {

    @Override
    public ItemStack createDefaultStack() {
        ItemStack stack = new ItemStack(Material.BLAZE_ROD);
        
        TagUtil.editMeta(stack, meta -> {
            meta.setDisplayName(ChatColor.GOLD + "Lightning Rod");
        });
        
        TagUtil.editTag(stack, tag -> {
            TagUtil.makeUnique(tag);
        });
        
        return stack;
    }
}
