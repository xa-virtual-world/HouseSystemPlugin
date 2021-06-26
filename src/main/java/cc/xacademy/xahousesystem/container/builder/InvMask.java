package cc.xacademy.xahousesystem.container.builder;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

public class InvMask {
    
    private InvMask(int row, ItemStack[] stacks) {
        
    }
    
    public static Builder builder(int row) {
        return new Builder(row);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder {
        
        private final int row;
        private List<String> pattern;
        
        public InvMask build() {
            ItemStack[] stacks = new ItemStack[this.row * 9];
            InvMask mask = new InvMask(this.row, stacks);
            
            return mask;
        }
    }
}
