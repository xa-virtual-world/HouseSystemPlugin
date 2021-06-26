package cc.xacademy.xahousesystem.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import cc.xacademy.xahousesystem.util.TagUtil;

public class LightningRodItem extends SpecialItem {

    public LightningRodItem(String registryName) {
        super(registryName);
    }

    @Override
    public ItemStack createDefaultStack() {
        ItemStack stack = new ItemStack(Material.BLAZE_ROD);
        
        TagUtil.editMeta(stack, meta -> {
            meta.setDisplayName(ChatColor.GOLD + "Lightning Rod");
            
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "“谁在用闪电劈我？”");
            lore.add(ChatColor.DARK_GRAY + (ChatColor.ITALIC + "- Derek Nee, 2021"));
            
            meta.setLore(lore);
        });
        
        TagUtil.setupSpecialItem(stack, "lightning_rod");
        TagUtil.addItemGlint(stack);
        
        return stack;
    }
    
    @Override
    public void onLeftClickBlock(ItemStack stack, Player player, Block block, BlockFace face) {
        player.sendMessage("A");
    }
    
    @Override
    public void onLeftClickAir(ItemStack stack, Player player) {
        player.sendMessage("B");
    }
}
