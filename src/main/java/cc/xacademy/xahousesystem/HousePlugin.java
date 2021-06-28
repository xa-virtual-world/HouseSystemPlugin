package cc.xacademy.xahousesystem;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import cc.xacademy.xahousesystem.command.CommandCompleter;
import cc.xacademy.xahousesystem.command.GiveSpecialItemCommand;
import cc.xacademy.xahousesystem.command.O5VaultCommand;
import cc.xacademy.xahousesystem.container.Containers;
import cc.xacademy.xahousesystem.integration.API;
import cc.xacademy.xahousesystem.item.Items;
import cc.xacademy.xahousesystem.listener.PlayerInteractionHandler;
import cc.xacademy.xahousesystem.registry.RegistryHandler;
import lombok.Getter;

public class HousePlugin extends JavaPlugin {
    
    private static HousePlugin instance;
    
    @Getter private RegistryHandler registries;
    @Getter private API api;
    
    @Override
    public void onEnable() {
        instance = this;
        
        // config file
        FileConfiguration config = this.getConfig();
        config.options().copyDefaults(true);
        config.addDefault("terraSmasherMaxSize", 2);
        config.addDefault("buildersWandRadius", 32);
        this.saveConfig();
        
        // registries
        this.registries = new RegistryHandler();
        Items.register(this.registries);
        Containers.register(this.registries);
        
        // listeners
        this.addListener(new PlayerInteractionHandler());
        
        // misc init
        this.api = new API(this);
        
        // commands
        this.getCommand("givespecialitem").setExecutor(new GiveSpecialItemCommand());
        this.getCommand("givespecialitem").setTabCompleter(new CommandCompleter());
        
        this.getCommand("o5vault").setExecutor(new O5VaultCommand());
    }
    
    @Override
    public void onDisable() {
        
    }
    
    // for ease of search
    public void addListener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
    
    // since lombok can't rename getters...
    public static HousePlugin get() {
        return instance;
    }
}
