package cc.xacademy.xahousesystem.container.builder;

import java.util.function.BiConsumer;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import lombok.Data;

@Data
public class Slot {

    private final ItemStack content;
    private final BiConsumer<Player, Integer> clickAction;
}
