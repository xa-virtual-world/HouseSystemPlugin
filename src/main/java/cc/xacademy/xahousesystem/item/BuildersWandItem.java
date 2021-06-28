package cc.xacademy.xahousesystem.item;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

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
            extendBlocks(stack, player, block, face);
        }
        
        return false;
    }
    
    private static void extendBlocks(ItemStack stack, Player player, Block block, BlockFace face) {
        if (player.getCooldown(Material.STICK) != 0) return;
        
        int size = HousePlugin.get().getConfig().getInt("buildersWandRadius", 32);
        
        Vector from = face.getOppositeFace().getDirection();
        Material mat = block.getType();
        List<Location> newBlocks = new ArrayList<>();
        Location loc = block.getLocation().clone().add(face.getDirection());;
        
        boolean horizontal = TagUtil.readEntry(stack, "Mode", PersistentDataType.INTEGER) == 0;
        
        for (int i = 0; i <= size; i++) {
            if (!checkPos(loc, i, player, mat, horizontal, face, from, newBlocks)) break;
        }
        
        for (int i = -1; i >= -size; i--) {
            if (!checkPos(loc, i, player, mat, horizontal, face, from, newBlocks)) break;
        }
        
        for (Location i: newBlocks) {
            i.getBlock().setType(Material.TNT);
        }
        
        if (newBlocks.size() != 0) {
            player.setCooldown(Material.STICK, 40);
        }
    }
    
    /**
     * If the given position is a part of the extension, add
     * it to list and return true; otherwise, return false.
     */
    private static boolean checkPos(Location origin, int offset, Player player,
            Material ref, boolean horizontal, BlockFace face, Vector from, List<Location> addTo) {
        
        Location target = origin.clone();
        BlockFace playerDir = player.getFacing();
        
        if (horizontal) {
            switch (face) {
            case EAST:
            case WEST:
                target.add(0, 0, offset);
                break;
                
            case NORTH:
            case SOUTH:
                target.add(offset, 0, 0);
                break;
                
            default: // UP or DOWN
                
                switch (playerDir) {
                case EAST:
                case WEST:
                    target.add(0, 0, offset);
                    break;
                default:
                    target.add(offset, 0, 0);
                    break;
                }
                
                break;
            }
        } else {
            switch (face) {
            case UP:
            case DOWN:
                
                switch (playerDir) {
                case EAST:
                case WEST:
                    target.add(offset, 0, 0);
                    break;
                default:
                    target.add(0, 0, offset);
                    break;
                }
                
                break;
                
            default:
                target.add(0, offset, 0);
                break;
            }
        }
        
        Material mat = target.clone().add(from).getBlock().getType();
        if (mat == Material.AIR) return false; // sanity check?
        if (target.getBlock().getType() != Material.AIR) return false;
        if (ref != mat) return false;
        
        addTo.add(target.clone());
        
        return true;
    }
    
    private void cycle(ItemStack stack, Player player) {
        AtomicInteger mode = new AtomicInteger(0);
        
        TagUtil.editTag(stack, tag -> {
            mode.set(tag.get(TagUtil.namespace("Mode"), PersistentDataType.INTEGER) ^ 1);
                                   
            tag.set(TagUtil.namespace("Mode"), PersistentDataType.INTEGER, mode.get());
        });
        
        String modeStr = mode.get() == 1 ? "Vertical" : "Horizontal";
        
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
