package cc.xacademy.xahousesystem.item;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import cc.xacademy.xahousesystem.HousePlugin;
import cc.xacademy.xahousesystem.util.TagUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class BuildersWandItem extends SpecialItem {

    public BuildersWandItem(String registryName) {
        super(registryName);
    }

    @Override
    public ItemStack createDefaultStack() {
        ItemStack stack = new ItemStack(Material.STICK);
        int size = HousePlugin.get().getConfig().getInt("buildersWandSize", 128);
        
        TagUtil.editTag(stack, tag -> {
            // 0: horizontal; 1: vertical
            tag.set(TagUtil.namespace("Mode"), PersistentDataType.INTEGER, 0);
        });
        
        TagUtil.editMeta(stack, meta -> {
            meta.setDisplayName(ChatColor.GOLD + "Builder's Wand");
            
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.AQUA + "Mode: " + ChatColor.BOLD + "Horizontal");
            lore.add(ChatColor.GRAY + "Extends an array of the same blocks");
            lore.add(ChatColor.GRAY + "Consumes blocks from your inventory");
            lore.add("");
            lore.add(ChatColor.DARK_GRAY + "[RIGHT CLICK] on block to extend");
            lore.add(ChatColor.DARK_GRAY + "[SHIFT + RIGHT CLICK] to switch mode");
            
            meta.setLore(lore);
        });
        
        TagUtil.addItemGlint(stack);
        TagUtil.setupSpecialItem(stack, "builders_wand");
        
        return stack;
    }

    @Override
    public void onRightClickAir(ItemStack stack, Player player) {
        if (player.isSneaking()) {
            this.cycle(stack, player);
        }
    }
    
    @Override
    public boolean onRightClickBlock(ItemStack stack, Player player, Block block, BlockFace face) {
        if (player.isSneaking()) {
            this.cycle(stack, player);
        } else {
            
        }
        
        return false;
    }
    
    private void cycle(ItemStack stack, Player player) {
        AtomicInteger mode = new AtomicInteger(0);
        String modeStr = mode.get() == 1 ? "Horizontal" : "Vertical";
        
        TagUtil.editTag(stack, tag -> {
            mode.set(tag.get(TagUtil.namespace("Mode"), PersistentDataType.INTEGER));
           
            mode.set(mode.get() ^ 1);
            
            tag.set(TagUtil.namespace("Mode"), PersistentDataType.INTEGER, mode.get());
        });
        
        TagUtil.editMeta(stack, meta -> {
            List<String> lore = meta.getLore();
            lore.set(0, ChatColor.AQUA + "Mode: " + ChatColor.BOLD + modeStr);
            meta.setLore(lore);
        });
        
        player.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                new TextComponent(ChatColor.AQUA + "Mode: " + ChatColor.BOLD + modeStr)
                );
    }
}
