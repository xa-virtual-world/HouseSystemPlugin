package cc.xacademy.xahousesystem.item;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;

import cc.xacademy.xahousesystem.HousePlugin;
import cc.xacademy.xahousesystem.util.TagUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class TerraSmasherItem extends SpecialItem {

    public TerraSmasherItem(String registryName) {
        super(registryName);
    }

    @Override
    public ItemStack createDefaultStack() {
        ItemStack stack = new ItemStack(Material.DIAMOND_PICKAXE);
        
        TagUtil.editTag(stack, tag -> {
            tag.set(TagUtil.namespace("DigSize"), PersistentDataType.INTEGER, 0);
        });
        
        TagUtil.editMeta(stack, meta -> {
            meta.setDisplayName(ChatColor.GREEN + "Terra Smasher");
            
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.AQUA + "Current size: 1x1");
            lore.add(ChatColor.GRAY + "Instantly digs a large area at a time");
            lore.add("");
            lore.add(ChatColor.GRAY + "Efficiency III");
            lore.add("");
            lore.add(ChatColor.DARK_GRAY + "RIGHT CLICK to cycle dig size");
            lore.add(ChatColor.DARK_GRAY + "SHIFT + RIGHT CLICK to cycle back");
            
            meta.setLore(lore);
            
            meta.addEnchant(Enchantment.DIG_SPEED, 3, true);
            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        });
        
        TagUtil.setupSpecialItem(stack, "terra_smasher");
        
        return stack;
    }
    
    @Override
    public void onLeftClickBlock(ItemStack stack, Player player, Block block, BlockFace face) {
        int size = TagUtil.readEntry(stack, "DigSize", PersistentDataType.INTEGER);
        PluginManager pluginManager = HousePlugin.get().getServer().getPluginManager();
        
        // test main block
        BlockBreakEvent centerEvent = new BlockBreakEvent(block, player);
        pluginManager.callEvent(centerEvent);
        if (centerEvent.isCancelled() || block.getType().getHardness() < 0) return;
        
        for (int i = -size; i <= size; i++) {
            for (int j = -size; j <= size; j++) {
                int x = 0, y = 0, z = 0;
                
                switch (face) {
                case UP:
                case DOWN:
                    x = i;
                    z = j;
                    break;
                    
                case EAST:
                case WEST:
                    y = i;
                    z = j;
                    break;
                
                case NORTH:
                case SOUTH:
                default:
                    x = i;
                    y = j;
                    break;
                }
                
                Location loc = block.getLocation().add(new Vector(x, y, z));
                Block target = loc.getBlock();
                
                BlockBreakEvent event = new BlockBreakEvent(target, player);
                pluginManager.callEvent(event);
                if (!event.isCancelled()) {
                    if (player.getGameMode() == GameMode.CREATIVE) {
                        target.setType(Material.AIR);
                    } else {
                        if (target.getType().getHardness() >= 0) {
                            target.breakNaturally(stack);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void onRightClickAir(ItemStack stack, Player player) {
        this.cycle(stack, player);
    }
    
    @Override
    public void onRightClickBlock(ItemStack stack, Player player, Block block, BlockFace face) {
        this.cycle(stack, player);
    }
    
    private void cycle(ItemStack stack, Player player) {
        AtomicInteger size = new AtomicInteger(0);
        int maxSize = HousePlugin.get().getConfig().getInt("terraSmasherMaxSize", 2);
        
        TagUtil.editTag(stack, tag -> {
            size.set(tag.get(TagUtil.namespace("DigSize"), PersistentDataType.INTEGER));
           
            if (player.isSneaking()) {
                size.set(size.get() - 1);
                
                if (size.get() < 0) {
                    size.set(maxSize);
                }
            } else {
                size.set(size.get() + 1);
                
                if (size.get() > maxSize) {
                    size.set(0);
                }
            }
            
            tag.set(TagUtil.namespace("DigSize"), PersistentDataType.INTEGER, size.get());
        });
        
        int totalSize = size.get() * 2 + 1;
        String notifyMsg = String.format("Current size: %dx%d", totalSize, totalSize);
        
        TagUtil.editMeta(stack, meta -> {
            List<String> lore = meta.getLore();
            lore.set(0, ChatColor.AQUA + notifyMsg);
            meta.setLore(lore);
        });
        
        player.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                new TextComponent(ChatColor.AQUA + notifyMsg)
                );
    }
}
