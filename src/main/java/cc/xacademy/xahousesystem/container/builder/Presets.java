package cc.xacademy.xahousesystem.container.builder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Presets {

    public static final ItemStack RED_PANE = new ItemStack(Material.RED_STAINED_GLASS_PANE);
    
    public static final InvMask BORDER_LARGE = InvMask.builder()
            .pattern("111111111")
            .pattern("100000001")
            .pattern("100000001")
            .pattern("100000001")
            .pattern("100000001")
            .pattern("111111111").build();
    
    static {
        ItemMeta meta = RED_PANE.getItemMeta();
        meta.setDisplayName("");
        RED_PANE.setItemMeta(meta);
    }
}
