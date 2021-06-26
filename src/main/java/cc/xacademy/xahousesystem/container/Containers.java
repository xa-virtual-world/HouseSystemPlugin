package cc.xacademy.xahousesystem.container;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import cc.xacademy.xahousesystem.HousePlugin;
import cc.xacademy.xahousesystem.container.builder.Presets;
import cc.xacademy.xahousesystem.container.menu.Menu;
import cc.xacademy.xahousesystem.container.mutable.MutableContainer;
import cc.xacademy.xahousesystem.item.SpecialItem;
import cc.xacademy.xahousesystem.listener.MenuListener;
import cc.xacademy.xahousesystem.registry.RegistryHandler;
import cc.xacademy.xahousesystem.registry.RegistryType;
import cc.xacademy.xahousesystem.util.TagUtil;

public class Containers {

    public static void register(RegistryHandler handler) {
        RegistryType<IContainer> registry = handler.lazyGetRegistry(IContainer.class);
        
        List<Menu> menus = new ArrayList<>();
        List<MutableContainer> mutables = new ArrayList<>();
        
        Menu vaultMenu = new Menu("o5_vault", 54, "O5 Vault");
        vaultMenu.editInventory(inv -> {
            Presets.BORDER_LARGE.apply(inv, Presets.RED_PANE);
            List<SpecialItem> items = new ArrayList<>(HousePlugin.get().getRegistries()
                    .lazyGetRegistry(SpecialItem.class).getValues());
            
            // avoid border
            for (int i = 10; i < Math.min(items.size() + 10, 45); i++) {
                if (i % 9 == 0 || i % 9 == 8) continue;
                
                int listIndex = i - 10; // to make the compiler happy
                
                ItemStack stack = items.get(listIndex).createDefaultStack();
                TagUtil.addClickObtainTooltip(stack);
                inv.setItem(i, stack);
                
                vaultMenu.addAction(i, player -> {
                    player.getInventory().addItem(items.get(listIndex).createDefaultStack());
                });
            }
        });
        menus.add(vaultMenu);
        
        MenuListener listener = new MenuListener();
        menus.forEach(listener::record);
        HousePlugin.get().addListener(listener);
        
        menus.forEach(registry::register);
        mutables.forEach(registry::register);
    }
}
