package cc.xacademy.xahousesystem.registry;

import lombok.Data;

@Data
public abstract class IRegistryEntry<T extends IRegistryEntry<T>> {

    private String registryName;
}
