package cc.xacademy.xahousesystem.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import cc.xacademy.xahousesystem.HousePlugin;
import cc.xacademy.xahousesystem.item.SpecialItem;
import cc.xacademy.xahousesystem.registry.RegistryHandler;

public class CommandCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        
        if (cmd.getName().equals("givespecialitem")) {
            RegistryHandler handler = HousePlugin.get().getRegistries();
            
            return new ArrayList<>(handler.lazyGetRegistry(SpecialItem.class).getKeys());
        }
        
        return null;
    }
}
