package cc.xacademy.xahousesystem.integration;

import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import lombok.Getter;

public class API {

    @Getter private final WorldGuardPlugin worldGuard;
    
    public API(Plugin main) {
        Plugin plugin;
        
        plugin = main.getServer().getPluginManager().getPlugin("WorldGuard");
        this.worldGuard = plugin instanceof WorldGuardPlugin ? (WorldGuardPlugin) plugin : null;
    }
    
    public boolean hasWorldGuard() {
        return this.worldGuard != null;
    }
}
