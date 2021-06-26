package cc.xacademy.xahousesystem.container;

import org.bukkit.entity.Player;

import cc.xacademy.xahousesystem.registry.IRegistryEntry;

public abstract class IContainer extends IRegistryEntry<IContainer> {

    public IContainer(String registryName) {
        super(registryName);
    }

    public abstract void open(Player player);
}
