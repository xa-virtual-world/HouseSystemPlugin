package cc.xacademy.xahousesystem.container.menu;

import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import cc.xacademy.xahousesystem.container.IContainer;
import lombok.Getter;

public class Menu extends IContainer {

    @Getter private final Inventory inv;
    
    public Menu(String registryName, int size, String title) {
        super(registryName);
        
        this.inv = Bukkit.createInventory(null, size, title);
    }

    public Menu editInventory(Consumer<Inventory> editor) {
        editor.accept(this.inv);
        
        return this;
    }

    @Override
    public void open(Player player) {
        player.openInventory(this.inv);
    }
}
