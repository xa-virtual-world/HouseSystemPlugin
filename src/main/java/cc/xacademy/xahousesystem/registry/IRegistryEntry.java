package cc.xacademy.xahousesystem.registry;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public abstract class IRegistryEntry<T extends IRegistryEntry<T>> {

    @Getter @Setter private String registryName;
}
