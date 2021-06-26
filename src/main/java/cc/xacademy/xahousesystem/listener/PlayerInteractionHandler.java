package cc.xacademy.xahousesystem.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import cc.xacademy.xahousesystem.util.TagUtil;

public class PlayerInteractionHandler implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRightClickBlock(PlayerInteractEvent event) {
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
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRightClickEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = player.getInventory().getItem(event.getHand());
        Entity entity = event.getRightClicked();
        
        if (stack == null || !(entity instanceof LivingEntity)) return;
        
        TagUtil.getSpecialFromStack(stack).ifPresent(item -> {
            item.onRightClickLiving(stack, player, (LivingEntity) entity);
        });
    }
}
