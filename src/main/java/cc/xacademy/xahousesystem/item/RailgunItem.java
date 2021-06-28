package cc.xacademy.xahousesystem.item;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Snowman;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.persistence.PersistentDataType;

import cc.xacademy.xahousesystem.util.TagUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class RailgunItem extends SpecialItem {
    
    private static final int SHEAR_DURABILITY = 238;
    private static final int MAX_CHARGE = 200;

    public RailgunItem(String registryName) {
        super(registryName);
    }

    @Override
    public ItemStack createDefaultStack() {
        ItemStack stack = new ItemStack(Material.SHEARS);
        
        TagUtil.editTag(stack, tag -> {
            tag.set(TagUtil.namespace("Charge"), PersistentDataType.INTEGER, 0);
        });
        
        TagUtil.editMeta(stack, meta -> {
            meta.setDisplayName(ChatColor.RED + "Railgun");
            
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.AQUA + String.format("Charge: %d/%d Xoules", 0, MAX_CHARGE));
            lore.add(ChatColor.GRAY + "Fires a super-charged bolt");
            lore.add("");
            lore.add(
                    ChatColor.DARK_RED + (ChatColor.BOLD + "WARNING") +
                    ChatColor.RESET + (ChatColor.DARK_RED + ": Very explosive")
                    );
            lore.add(ChatColor.DARK_RED + "Keep out of reach from Daniel");
            lore.add("");
            lore.add(ChatColor.DARK_GRAY + "[RIGHT CLICK] to charge up");
            lore.add(ChatColor.DARK_GRAY + "[SHIFT + RIGHT CLICK] to fire");
            
            if (meta instanceof Damageable) {
                ((Damageable) meta).setDamage(SHEAR_DURABILITY);
            }
            
            meta.setLore(lore);
            
            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        });
        
        TagUtil.setupSpecialItem(stack, "railgun");
        
        return stack;
    }

    @Override
    public boolean onRightClickLiving(ItemStack stack, Player player, LivingEntity living) {
        if (living instanceof Sheep || living instanceof Snowman || living instanceof MushroomCow) {
            return true;
        }
        
        return false;
    }
    
    @Override
    public boolean onRightClickBlock(ItemStack stack, Player player, Block block, BlockFace face) {
        
        if (player.isSneaking()) {
            this.tryFire(stack, player);
        } else {
            this.charge(stack, player);
        }
        
        return block.getType() == Material.PUMPKIN ||
                block.getType() == Material.BEE_NEST ||
                block.getType() == Material.BEEHIVE;
    }
    
    @Override
    public void onRightClickAir(ItemStack stack, Player player) {
        if (player.isSneaking()) {
            this.tryFire(stack, player);
        } else {
            this.charge(stack, player);
        }
    }
    
    private void charge(ItemStack stack, Player player) {
        AtomicInteger charge = new AtomicInteger(0);
        
        TagUtil.editTag(stack, tag -> {
            charge.set(tag.get(TagUtil.namespace("Charge"), PersistentDataType.INTEGER));
            
            if (charge.get() >= MAX_CHARGE) {
                player.spigot().sendMessage(
                        ChatMessageType.ACTION_BAR,
                        new TextComponent(ChatColor.AQUA + "Fully charged!")
                        );
            } else {
                charge.set(charge.get() + 1);
            }
            
            tag.set(
                    TagUtil.namespace("Charge"),
                    PersistentDataType.INTEGER,
                    Math.min(MAX_CHARGE, charge.get())
                    );
        });
        
        TagUtil.editMeta(stack, meta -> {
            List<String> lore = meta.getLore();
            lore.set(0, ChatColor.AQUA + String.format("Charge: %d/%d Xoules", 0, MAX_CHARGE));
            meta.setLore(lore);
        });
    }
    
    private void tryFire(ItemStack stack, Player player) {
        TagUtil.editTag(stack, tag -> {
            tag.set(TagUtil.namespace("Charge"), PersistentDataType.INTEGER, 0);
        });
        
        TagUtil.editMeta(stack, meta -> {
            List<String> lore = meta.getLore();
            lore.set(0, ChatColor.AQUA + "Charge: " + 0 + " Xoules");
            meta.setLore(lore);
        });
    }
}
