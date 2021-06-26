package cc.xacademy.xahousesystem.container.mutable;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import cc.xacademy.xahousesystem.container.IContainer;

public abstract class MutableContainer extends IContainer {

    public MutableContainer(String registryName) {
        super(registryName);
    }
    
    @Override
    public void open(Player player) {
        player.openInventory(this.createContainer(player));
    }
    
    /**
     * ONLY CREATES the inventory. Don't bloody open it.
     */
    public abstract Inventory createContainer(Player player);
}
