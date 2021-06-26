package cc.xacademy.xahousesystem.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;

import cc.xacademy.xahousesystem.util.TagUtil;

public class SpellBlinkItem extends SpecialItem {

    public SpellBlinkItem(String registryName) {
        super(registryName);
    }

    @Override
    public ItemStack createDefaultStack() {
        ItemStack stack = new ItemStack(Material.PRISMARINE_SHARD);
        
        TagUtil.editMeta(stack, meta -> {
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Spell: Blink");
            
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Teleports to where you look");
            lore.add("");
            lore.add(ChatColor.DARK_GRAY + 
                    (ChatColor.ITALIC + "From XA Chengdu to XA Shanghai instantly!"));
            lore.add("");
            lore.add(ChatColor.DARK_GRAY + "[RIGHT CLICK] to teleport to cursor");
            
            meta.setLore(lore);            
        });
        
        TagUtil.addItemGlint(stack);
        TagUtil.setupSpecialItem(stack, "spell_blink");
        
        return stack;
    }

    @Override
    public void onRightClickBlock(ItemStack stack, Player player, Block block, BlockFace face) {        
        this.lookAt(stack, player, block, face);
    }
    
    @Override
    public void onRightClickAir(ItemStack stack, Player player) {        
        RayTraceResult result = player.getWorld().rayTraceBlocks(
                player.getEyeLocation(),
                player.getEyeLocation().getDirection(),
                128, FluidCollisionMode.NEVER, false);
        
        if (result != null && result.getHitBlock() != null) {
            this.lookAt(stack, player, result.getHitBlock(), result.getHitBlockFace());
        }
    }
    
    private void lookAt(ItemStack stack, Player player, Block block, BlockFace face) {
        Location to = block.getLocation().clone().add(face.getDirection());
        
        if (to.clone().add(0, 1, 0).getBlock().getType() != Material.AIR) {
            to.add(0, -1, 0);
        }
        
        to.add(0.5, 0, 0.5);
        to.setYaw(player.getLocation().getYaw());
        to.setPitch(player.getLocation().getPitch());
        
        player.teleport(to);
    }
}
