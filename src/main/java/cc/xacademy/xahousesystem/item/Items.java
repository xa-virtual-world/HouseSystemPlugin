package cc.xacademy.xahousesystem.item;

import cc.xacademy.xahousesystem.registry.RegistryHandler;
import cc.xacademy.xahousesystem.registry.RegistryType;

public class Items {

    public static void register(RegistryHandler handler) {
        RegistryType<SpecialItem> registry = handler.lazyGetRegistry(SpecialItem.class);
        
        registry.register(new LightningRod());
    }
}
