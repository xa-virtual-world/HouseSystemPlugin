package cc.xacademy.xahousesystem;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class HousePlugin extends JavaPlugin {
    
    private static HousePlugin instance;
    
    @Override
    public void onEnable() {
        instance = this;
        
        FileConfiguration config = this.getConfig();
        config.options().copyDefaults(true);
        this.saveConfig();
    }
    
    @Override
    public void onDisable() {
        
    }
    
    // since lombok can't rename getters...
    public static HousePlugin get() {
        return instance;
    }
}
