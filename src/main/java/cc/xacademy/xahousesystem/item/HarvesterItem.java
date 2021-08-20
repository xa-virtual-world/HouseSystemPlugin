package cc.xacademy.xahousesystem.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import cc.xacademy.xahousesystem.HousePlugin;
import cc.xacademy.xahousesystem.util.PlayerUtil;
import cc.xacademy.xahousesystem.util.TagUtil;

public class HarvesterItem extends SpecialItem {

    public HarvesterItem(String registryName) {
        super(registryName);
    }

    @Override
    public ItemStack createDefaultStack() {
        int size = HousePlugin.get().getConfig().getInt("harvesterRadius", 4);
        size = size * 2 + 1;
        
        String tip = String.format("Harvests crops in a %dx%d area", size, size);
        
        ItemStack stack = new ItemStack(Material.QUARTZ);
        
        TagUtil.editMeta(stack, meta -> {
            meta.setDisplayName(ChatColor.AQUA + "Harvester");
            
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + tip);
            lore.add("");
            lore.add(ChatColor.DARK_GRAY + "[RIGHT CLICK] to harvest");
            
            meta.setLore(lore);            
        });
        
        TagUtil.addItemGlint(stack);
        TagUtil.setupSpecialItem(stack, this.getRegistryName());
        
        return stack;
    }
    
    @Override
    public boolean onRightClickBlock(ItemStack stack, Player player, Block block, BlockFace face) {
        // since this might change during reload
        int size = HousePlugin.get().getConfig().getInt("harvesterRadius", 4);
        
        Location pos = block.getLocation();
        World world = block.getWorld();
        
        for (int i = -size; i <= size; i++) {
            for (int j = -size; j <= size; j++) {
                Location at = new Location(
                    world,
                    pos.getBlockX() + i,
                    pos.getBlockY(),
                    pos.getBlockZ() + j
                );
                
                harvestBlock(player, pos);
            }
        }
        
        return true;
    }
    
    public static void harvestBlock(Player player, Location location) {
        PluginManager manager = HousePlugin.get().getServer().getPluginManager();
        Block block = location.getBlock();
        
        if (!shouldHarvest(block)) return;
        
        BlockBreakEvent breakEvent = new BlockBreakEvent(block, player);
        manager.callEvent(breakEvent);
        if (breakEvent.isCancelled()) return;
        
        ItemStack hand = player.getInventory().getItemInMainHand();
        Collection<ItemStack> drops = block.getDrops(hand, player);
        Material seed = getSeed(block.getType());
        
        boolean hasSeed = false;
        for (ItemStack i: drops) {
            if (i.getType() == seed && !hasSeed) {
                hasSeed = true;
                
                int amount = i.getAmount() - 1;
                
                if (amount > 0) i.setAmount(amount);
                else continue;
            }
            
            PlayerUtil.addToInventory(player, i);
        }
        
        if (hasSeed) {
            Ageable ageable = (Ageable) block.getBlockData();
            ageable.setAge(0);
            block.setBlockData(ageable);
        } else {
            block.setType(Material.AIR);
        }
    }
    
    public static boolean shouldHarvest(Block block) {
        switch (block.getType()) {
        case WHEAT:
        case COCOA:
        case CARROT:
        case POTATO:
        case BEETROOT:
        case NETHER_WART:
            Ageable ageable = (Ageable) block.getBlockData();            
            return ageable.getAge() == ageable.getMaximumAge();
            
        default: return false;
        }
    }
    
    public static Material getSeed(Material material) {
        switch (material) {
            case WHEAT: return Material.WHEAT_SEEDS;
            case COCOA: return Material.COCOA_BEANS;
            case CARROT: return Material.CARROT;
            case POTATO: return Material.POTATO;
            case BEETROOT: return Material.BEETROOT_SEEDS;
            case NETHER_WART: return Material.NETHER_WART;
            default: return Material.AIR;
        }
    }
}
