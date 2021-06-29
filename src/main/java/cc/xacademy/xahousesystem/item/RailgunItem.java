package cc.xacademy.xahousesystem.item;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Snowman;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.persistence.PersistentDataType;

import cc.xacademy.xahousesystem.HousePlugin;
import cc.xacademy.xahousesystem.util.TagUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class RailgunItem extends SpecialItem {
    
    private static final int SHEAR_DURABILITY = 238;
    private static final int CHARGE_BAR_LENGTH = 40;

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
            meta.setDisplayName(ChatColor.GOLD + "Railgun");
            
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.AQUA + "Charge: " + formatChargeTooltip(0));
            lore.add(ChatColor.GRAY + "Fires a super-charged bolt");
            lore.add("");
            lore.add(
                    ChatColor.DARK_RED + (ChatColor.BOLD + "WARNING") +
                    ChatColor.RESET + (ChatColor.DARK_RED + ": Highly explosive")
                    );
            lore.add(ChatColor.DARK_RED + "Keep out of reach of Daniel");
            lore.add("");
            lore.add(ChatColor.DARK_GRAY + "[RIGHT CLICK] to charge up");
            lore.add(ChatColor.DARK_GRAY + "[SHIFT + RIGHT CLICK] to fire");
            
            if (meta instanceof Damageable) {
                ((Damageable) meta).setDamage(SHEAR_DURABILITY);
            }
            
            meta.setLore(lore);
            
            //meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        });
        
        TagUtil.setupSpecialItem(stack, "railgun");
        
        return stack;
    }

    @Override
    public boolean onRightClickLiving(ItemStack stack, Player player, LivingEntity living) {
        if (player.isSneaking()) {
            tryFire(stack, player);
        } else {
            charge(stack, player);
        }

        return living instanceof Sheep || living instanceof Snowman || living instanceof MushroomCow;
    }
    
    @Override
    public boolean onRightClickBlock(ItemStack stack, Player player, Block block, BlockFace face) {
        
        if (player.isSneaking()) {
            tryFire(stack, player);
        } else {
            charge(stack, player);
        }
        
        return block.getType() == Material.PUMPKIN ||
                block.getType() == Material.BEE_NEST ||
                block.getType() == Material.BEEHIVE;
    }
    
    @Override
    public void onRightClickAir(ItemStack stack, Player player) {
        if (player.isSneaking()) {
            tryFire(stack, player);
        } else {
            charge(stack, player);
        }
    }
    
    @Override
    public boolean onItemBreak(ItemStack stack, Player player) {
        stack.setAmount(2);
        
        TagUtil.editMeta(stack, meta -> {
            if (meta instanceof Damageable) {
                ((Damageable) meta).setDamage(SHEAR_DURABILITY);
            }
        });
        
        player.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                new TextComponent(
                        ChatColor.RED + "Careful! The railgun is fragile")
                );
        
        return true;
    }
    
    private static void charge(ItemStack stack, Player player) {
        AtomicInteger charge = new AtomicInteger(0);
        int maxCharge = HousePlugin.get().getConfig().getInt("railgunMaxCharge", 200);
        
        TagUtil.editTag(stack, tag -> {
            String screenMsg;
            
            charge.set(tag.get(TagUtil.namespace("Charge"), PersistentDataType.INTEGER));
            
            if (charge.get() < maxCharge) {
                charge.set(charge.get() + 1);
                screenMsg = formatChargeTooltip(charge.get());
            } else {
                screenMsg = ChatColor.GREEN + (ChatColor.BOLD + "Full");
            }
            
            player.spigot().sendMessage(
                    ChatMessageType.ACTION_BAR,
                    new TextComponent(
                            ChatColor.AQUA + "Charge: " + screenMsg)
                    );
            
            tag.set(
                    TagUtil.namespace("Charge"),
                    PersistentDataType.INTEGER,
                    Math.min(maxCharge, charge.get())
                    );
        });
        
        TagUtil.editMeta(stack, meta -> {
            List<String> lore = meta.getLore();
            lore.set(0, ChatColor.AQUA + "Charge: " + formatChargeTooltip(charge.get()));
            meta.setLore(lore);
            
            if (meta instanceof Damageable) {
                int chargeScaled = TagUtil.approxDurability(charge.get(), maxCharge, SHEAR_DURABILITY);
                ((Damageable) meta).setDamage(SHEAR_DURABILITY - chargeScaled);
            }
            
            if (charge.get() >= maxCharge) {
                meta.addEnchant(Enchantment.DURABILITY, 1, true);
            }
        });
    }
    
    private static void tryFire(ItemStack stack, Player player) {
        TagUtil.editTag(stack, tag -> {
            tag.set(TagUtil.namespace("Charge"), PersistentDataType.INTEGER, 0);
        });
        
        TagUtil.editMeta(stack, meta -> {
            List<String> lore = meta.getLore();
            lore.set(0, ChatColor.AQUA + "Charge: " + formatChargeTooltip(0));
            meta.setLore(lore);
            
            if (meta instanceof Damageable) {
                ((Damageable) meta).setDamage(SHEAR_DURABILITY);
            }
            
            meta.removeEnchant(Enchantment.DURABILITY);
        });
        
        player.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                new TextComponent(
                        ChatColor.AQUA + "Charge: " + formatChargeTooltip(0))
                );
    }
    
    private static String formatChargeTooltip(int charge) {
        int maxCharge = HousePlugin.get().getConfig().getInt("railgunMaxCharge", 200);
        
        int lit = TagUtil.approxDurability(charge, maxCharge, CHARGE_BAR_LENGTH);
        int remain = CHARGE_BAR_LENGTH - lit;
        
        String first = new String(new char[lit]).replace("\0", "|");
        String second = new String(new char[remain]).replace("\0", "|");
        
        return ChatColor.BOLD + (ChatColor.GREEN + first + ChatColor.DARK_RED + second);
    }
}
