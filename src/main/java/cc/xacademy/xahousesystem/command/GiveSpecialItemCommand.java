package cc.xacademy.xahousesystem.command;

import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cc.xacademy.xahousesystem.item.SpecialItem;
import cc.xacademy.xahousesystem.util.PlayerUtil;
import cc.xacademy.xahousesystem.util.TagUtil;

public class GiveSpecialItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) return false;
        
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String itemName = args[0];
            Optional<SpecialItem> container = TagUtil.getSpecialFromName(itemName);
            
            container.ifPresent(item -> PlayerUtil.addToInventory(player, item.createDefaultStack()));
            
            return container.isPresent();
        }
        
        return true;
    }
}
