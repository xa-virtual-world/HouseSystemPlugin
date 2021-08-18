package cc.xacademy.xahousesystem.listener;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
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
            } else {
                out = first.clone();
                ItemMeta book = second.getItemMeta();
                
                if (book == null) return;
                
                EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book;
                for (Enchantment i: meta.getStoredEnchants().keySet()) {
                    if (TagUtil.canEnchant(out, i)) {
                        int stackLevel = out.getEnchantmentLevel(i);
                        int bookLevel = out.getEnchantmentLevel(i);
                        
                        if (bookLevel > stackLevel) {
                            out.addUnsafeEnchantment(i, bookLevel);
                        }
                    }
                }
            }
            
            event.setResult(out);
        }
    }
}
