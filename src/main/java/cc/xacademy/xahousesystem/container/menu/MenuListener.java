package cc.xacademy.xahousesystem.container.menu;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

/**
 * All menus must be recorded here.
 */
public class MenuListener implements Listener {
    
    private final Map<Inventory, Menu> invs;
    
    public MenuListener() {
        this.invs = new HashMap<>();
    }

    public void record(Menu menu) {
        this.invs.put(menu.getInv(), menu);
    }
    
    @EventHandler
    public void onSlotDrag(InventoryDragEvent event) {
        if (this.invs.containsKey(event.getInventory())) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onSlotClick(InventoryClickEvent event) {
        if (this.invs.containsKey(event.getInventory())) {
            event.setCancelled(true);
            
            Menu menu = this.invs.get(event.getClickedInventory());
            if (menu != null) {
                int slot = event.getSlot();
                HumanEntity clicker = event.getWhoClicked();
                
                if (clicker instanceof Player) {
                    menu.onSlotClick((Player) clicker, slot);
                }
            }
        }
    }
}
