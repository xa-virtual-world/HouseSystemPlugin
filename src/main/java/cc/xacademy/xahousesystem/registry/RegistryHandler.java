package cc.xacademy.xahousesystem.registry;

import java.util.HashMap;
import java.util.Map;

public class RegistryHandler {

    private final Map<Class<? extends IRegistryEntry<?>>, RegistryType<?>> registries;
    
    public RegistryHandler() {
        this.registries = new HashMap<>();
    }
    
    @SuppressWarnings("unchecked")
    public <T extends IRegistryEntry<T>> RegistryType<T> lazyGetRegistry(Class<T> clazz) {
        this.registries.computeIfAbsent(clazz, e -> new RegistryType<>());
        
        return (RegistryType<T>) this.registries.get(clazz);
    }
}
