package cc.xacademy.xahousesystem.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

import cc.xacademy.xahousesystem.util.TagUtil;

public class PlayerInteractionHandler implements Listener {
    
    @EventHandler
    public void onItemBreak(PlayerItemBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = event.getBrokenItem();
        
        TagUtil.getSpecialFromStack(stack).ifPresent(item -> {
            item.onItemBreak(stack, player);
        });
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.hasItem()) return;
        
        Player player = event.getPlayer();
        ItemStack stack = event.getItem();
        
        TagUtil.getSpecialFromStack(stack).ifPresent(item -> {
            switch (event.getAction()) {
            case LEFT_CLICK_AIR:
                item.onLeftClickAir(stack, player);
                break;
            case LEFT_CLICK_BLOCK:
                event.setCancelled(
                        item.onLeftClickBlock(
                                stack, player, event.getClickedBlock(), event.getBlockFace()
                                )
                        );
                break;
            case RIGHT_CLICK_AIR:
                item.onRightClickAir(stack, player);
                break;
            case RIGHT_CLICK_BLOCK:
                event.setCancelled(
                        item.onRightClickBlock(
                                stack, player, event.getClickedBlock(), event.getBlockFace()
                                )
                        );
                break;
            default:
                break;
            }
        });
    }
    
    @EventHandler
    public void onRightClickEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = player.getInventory().getItem(event.getHand());
        Entity entity = event.getRightClicked();
        
        if (stack == null || !(entity instanceof LivingEntity)) return;
        
        TagUtil.getSpecialFromStack(stack).ifPresent(item -> {
            event.setCancelled(item.onRightClickLiving(stack, player, (LivingEntity) entity));
        });
    }
    
    @EventHandler
    public void onEntityAttack(EntityDamageByEntityEvent event) {
        Entity attacker = event.getDamager();
        if (!(attacker instanceof Player)) return;
        
        Entity target = event.getEntity();
        Player player = (Player) attacker;
        ItemStack stack = player.getInventory().getItemInMainHand();
        
        if (stack == null || !(target instanceof LivingEntity)) return;
        
        TagUtil.getSpecialFromStack(stack).ifPresent(item -> {
            event.setCancelled(item.onLeftClickLiving(stack, player, (LivingEntity) target));
        });
    }
}
