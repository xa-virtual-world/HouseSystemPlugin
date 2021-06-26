package cc.xacademy.xahousesystem.util;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.NamespacedKey;
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
        PersistentDataContainer tag = stack.getItemMeta().getPersistentDataContainer();
        
        String itemName = tag.get(namespace("SpecialItem"), PersistentDataType.STRING);
        if (itemName == null) return Optional.empty();
        
        SpecialItem item = HousePlugin.get().getRegistries()
                .lazyGetRegistry(SpecialItem.class).get(itemName);
        
        return Optional.ofNullable(item);
    }
}
