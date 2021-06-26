package cc.xacademy.xahousesystem;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import cc.xacademy.xahousesystem.item.Items;
import cc.xacademy.xahousesystem.registry.RegistryHandler;
import lombok.Getter;

public class HousePlugin extends JavaPlugin {
    
    private static HousePlugin instance;
    
    @Getter private RegistryHandler registries;
    
    @Override
    public void onEnable() {
        instance = this;
        
        FileConfiguration config = this.getConfig();
        config.options().copyDefaults(true);
        this.saveConfig();
        
        this.registries = new RegistryHandler();
        Items.register(this.registries);
    }
    
    @Override
    public void onDisable() {
        
    }
    
    // since lombok can't rename getters...
    public static HousePlugin get() {
        return instance;
    }
}
