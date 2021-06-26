package cc.xacademy.xahousesystem.registry;

import java.util.HashMap;
import java.util.Map;

public class RegistryType<T extends IRegistryEntry<T>> {

    private Map<String, T> entries;
    
    public RegistryType() {
        this.entries = new HashMap<>();
    }
    
    public T get(String registryName) {
        return this.entries.get(registryName);
    }
    
    public void register(T elem) {
        this.entries.put(elem.getRegistryName(), elem);
    }
}
