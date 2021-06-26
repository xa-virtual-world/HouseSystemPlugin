package cc.xacademy.xahousesystem.container.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import cc.xacademy.xahousesystem.container.IContainer;
import lombok.Getter;

public class Menu extends IContainer {

    @Getter private final Inventory inv;
    private Map<Integer, Consumer<Player>> clickAction;
    
    public Menu(String registryName, int size, String title) {
        super(registryName);
        
        this.inv = Bukkit.createInventory(null, size, title);
        this.clickAction = new HashMap<>();
    }

    public Menu editInventory(Consumer<Inventory> editor) {
        editor.accept(this.inv);
        
        return this;
    }
    
    public Menu addAction(int slot, Consumer<Player> action) {
        this.clickAction.put(slot, action);
        
        return this;
    }
    
    public void onSlotClick(Player player, int slot) {
        if (this.clickAction.containsKey(slot)) {
            this.clickAction.get(slot).accept(player);
        }
    }

    @Override
    public void open(Player player) {
        player.openInventory(this.inv);
    }
}
