package cc.xacademy.xahousesystem.container;

import java.util.ArrayList;
import java.util.List;

import cc.xacademy.xahousesystem.container.menu.Menu;
import cc.xacademy.xahousesystem.container.mutable.MutableContainer;
import cc.xacademy.xahousesystem.registry.RegistryHandler;
import cc.xacademy.xahousesystem.registry.RegistryType;

public class Containers {

    public static void register(RegistryHandler handler) {
        RegistryType<IContainer> registry = handler.lazyGetRegistry(IContainer.class);
        
        List<Menu> menus = new ArrayList<>();
        List<MutableContainer> mutables = new ArrayList<>();
        
        menus.add(
                new Menu("o5_vault", 54, "O5 Vault")
                );
        
        menus.forEach(registry::register);
        mutables.forEach(registry::register);
    }
}
