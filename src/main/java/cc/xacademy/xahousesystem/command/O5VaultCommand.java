package cc.xacademy.xahousesystem.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cc.xacademy.xahousesystem.HousePlugin;
import cc.xacademy.xahousesystem.container.IContainer;

public class O5VaultCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 0) return false;
        
        if (sender instanceof Player) {
            HousePlugin.get().getRegistries().lazyGetRegistry(IContainer.class)
                    .get("o5_vault").open((Player) sender);
        }
        
        return true;
    }
}
