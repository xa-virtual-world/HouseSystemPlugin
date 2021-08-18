package cc.xacademy.xahousesystem.util;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import cc.xacademy.xahousesystem.HousePlugin;
import cc.xacademy.xahousesystem.item.SpecialItem;

public class TagUtil {
    
    /**
     *  Name your stuff bruh.
     */
    public static NamespacedKey namespace(String name) {
        return new NamespacedKey(HousePlugin.get(), name);
    }
    
    public static void editMeta(ItemStack stack, Consumer<ItemMeta> editor) {
        ItemMeta meta = stack.getItemMeta();
        
        editor.accept(meta);
        
        stack.setItemMeta(meta);
    }
    
    public static void editTag(ItemStack stack, Consumer<PersistentDataContainer> editor) {
        editMeta(stack, meta -> {
            PersistentDataContainer tag = meta.getPersistentDataContainer();
            
            editor.accept(tag);
        });
    }
    
    public static void makeUnique(PersistentDataContainer tag) {
        tag.set(namespace("UniqueId"), PersistentDataType.STRING, UUID.randomUUID().toString());
    }
    
    public static Optional<SpecialItem> getSpecialFromStack(ItemStack stack) {
        if (stack == null || !stack.hasItemMeta()) return Optional.empty();
        
        PersistentDataContainer tag = stack.getItemMeta().getPersistentDataContainer();
        
        String itemName = tag.get(namespace("SpecialItem"), PersistentDataType.STRING);
        if (itemName == null) return Optional.empty();
        
        return getSpecialFromName(itemName);
    }
    
    public static Optional<SpecialItem> getSpecialFromName(String itemName) {
        SpecialItem item = HousePlugin.get().getRegistries()
                .lazyGetRegistry(SpecialItem.class).get(itemName);
        
        return Optional.ofNullable(item);
    }

    public static void addSpecialItemName(PersistentDataContainer tag, String name) {
        tag.set(namespace("SpecialItem"), PersistentDataType.STRING, name);
    }
    
    public static void setupSpecialItem(ItemStack stack, String name) {
        editTag(stack, tag -> {
            TagUtil.makeUnique(tag);
            TagUtil.addSpecialItemName(tag, name);
        });
    }
    
    public static void addItemGlint(ItemStack stack) {        
        editMeta(stack, meta -> {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        });
    }
    
    // for menu purposes
    public static void addClickObtainTooltip(ItemStack stack) {
        editMeta(stack, meta -> {
            List<String> lore = meta.getLore();
            
            lore.add("");
            lore.add(ChatColor.GREEN + "CLICK TO OBTAIN");
            
            meta.setLore(lore);
        });
    }
    
    // only use for single read to minimize overhead
    public static <T, Z> Z readEntry(ItemStack stack, String key, PersistentDataType<T, Z> type) {
        PersistentDataContainer tag = stack.getItemMeta().getPersistentDataContainer();
        
        return tag.get(namespace(key), type);
    }
    
    public static int approxDurability(double value, double maxValue, int maxDurability) {
        return (int) (value / maxValue * maxDurability);
    }
    
    public static boolean canEnchant(ItemStack stack, Enchantment ench) {
        ItemMeta meta = stack.getItemMeta();
        boolean result = ench.canEnchantItem(stack);
        
        if (meta == null) return result;
        
        for (Enchantment i: meta.getEnchants().keySet()) {
            if (i != ench && i.conflictsWith(ench)) result = false;
        }
        
        return result;
    }
}
