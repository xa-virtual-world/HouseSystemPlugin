package cc.xacademy.xahousesystem.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;

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
        
        TagUtil.setupSpecialItem(stack, this.getRegistryName());
        TagUtil.addItemGlint(stack);
        
        return stack;
    }
    
    @Override
    public boolean onRightClickBlock(ItemStack stack, Player player, Block block, BlockFace face) {
        player.getWorld().strikeLightning(block.getLocation().add(face.getDirection()));
        
        return false;
    }
    
    @Override
    public boolean onRightClickLiving(ItemStack stack, Player player, LivingEntity living) {
        living.getWorld().strikeLightning(living.getLocation());
        
        return true;
    }
    
    @Override
    public void onRightClickAir(ItemStack stack, Player player) {
        RayTraceResult result = player.getWorld().rayTrace(
                player.getEyeLocation(),
                player.getEyeLocation().getDirection(),
                128, FluidCollisionMode.NEVER,
                true, 0, e -> e != player);
        
        if (result != null) {
            Location loc = result.getHitPosition().toLocation(player.getWorld());
            player.getWorld().strikeLightning(loc);
        }
    }
}
