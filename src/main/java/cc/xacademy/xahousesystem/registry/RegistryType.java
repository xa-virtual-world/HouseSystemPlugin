package cc.xacademy.xahousesystem.registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cc.xacademy.xahousesystem.item.SpecialItem;

public class RegistryType<T extends IRegistryEntry<T>> {

    private final Map<String, T> entries;
    
    public RegistryType() {
        this.entries = new HashMap<>();
    }
    
    public T get(String registryName) {
        return this.entries.get(registryName);
    }
    
    public void register(T elem) {
        this.entries.put(elem.getRegistryName(), elem);
    }
    
    public Set<String> getKeys() {
        return this.entries.keySet();
    }

    public Collection<T> getValues() {
        return this.entries.values();
    }
}
