package cc.xacademy.xahousesystem.listener;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import cc.xacademy.xahousesystem.HousePlugin;
import cc.xacademy.xahousesystem.util.TagUtil;

public class AnvilListener implements Listener {

    @EventHandler
    public void onAnvilPrepare(PrepareAnvilEvent event) {
        FileConfiguration config = HousePlugin.get().getConfig();
        
        if (!config.getBoolean("removeMaxAnvilLevel")) return;
        
        ItemStack first = event.getInventory().getItem(0);
        ItemStack second = event.getInventory().getItem(1);
        
        if (first == null || second == null) return;
        
        if (second.getType() == Material.ENCHANTED_BOOK) {
            ItemStack out;
            
            if (first.getType() == Material.ENCHANTED_BOOK) {
                out = new ItemStack(Material.ENCHANTED_BOOK);
                
                ItemMeta firstMeta = first.getItemMeta();
                ItemMeta secondMeta = second.getItemMeta();
                ItemMeta outMeta = out.getItemMeta();
                
                if (firstMeta == null || secondMeta == null) return;
                
                TagUtil.coerceEnchantedBooks(outMeta, firstMeta);
                TagUtil.coerceEnchantedBooks(outMeta, secondMeta);
                
                out.setItemMeta(outMeta);
            } else {
                out = first.clone();
                ItemMeta book = second.getItemMeta();
                
                if (book == null) return;
                
                EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book;                
                meta.getStoredEnchants().forEach((ench, lvl) -> {
                    if (TagUtil.canEnchant(out, ench)) {
                        int stackLevel = out.getEnchantmentLevel(ench);
                        
                        if (lvl > stackLevel) {
                            out.addUnsafeEnchantment(ench, lvl);
                        }
                    }
                });
            }
            
            event.setResult(out);
        }
    }
}
