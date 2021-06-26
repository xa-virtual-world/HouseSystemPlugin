package cc.xacademy.xahousesystem.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import cc.xacademy.xahousesystem.util.TagUtil;

public class PlayerInteractionHandler implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRightClick(PlayerInteractEvent event) {
        if (!event.hasItem()) return;
        
        Player player = event.getPlayer();
        ItemStack stack = event.getItem();
        
        TagUtil.getSpecialFromStack(stack).ifPresent(item -> {
            switch (event.getAction()) {
            case LEFT_CLICK_AIR:
                item.onLeftClickAir(stack, player);
                break;
            case LEFT_CLICK_BLOCK:
                item.onLeftClickBlock(stack, player, event.getClickedBlock(), event.getBlockFace());
                break;
            case RIGHT_CLICK_AIR:
                item.onRightClickAir(stack, player);
                break;
            case RIGHT_CLICK_BLOCK:
                item.onRightClickBlock(stack, player, event.getClickedBlock(), event.getBlockFace());
                break;
            default:
                break;
            }
        });
    }
}
