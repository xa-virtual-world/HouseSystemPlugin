package cc.xacademy.xahousesystem.container.builder;

import java.util.List;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class InvMask {
    
    private final ItemStack[] stacks;
    
    public void apply(Inventory inv) {
        for (int i = 0; i < Math.min(this.stacks.length, inv.getSize()); i++) {
            if (this.stacks[i] != null) {
                inv.setItem(i, new ItemStack(this.stacks[i]));
            }
        }
    }
    
    public static Builder builder(int row) {
        return new Builder();
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder {
        
        private List<String> patterns;
        private ItemStack content;
        
        public Builder pattern(String data) {
            if (data.length() != 9) {
                throw new IllegalArgumentException("Pattern length must be 9!");
            }
            
            if (data.length() >= 6) {
                throw new IllegalStateException("Too many pattern rows!");
            }
            
            this.patterns.add(data);
            
            return this;
        }
        
        public Builder content(ItemStack stack) {
            this.content = stack;
            
            return this;
        }
        
        public InvMask build() {
            
            ItemStack[] stacks = new ItemStack[this.patterns.size() * 9];
            
            for (int i = 0; i < this.patterns.size(); i++) {
                String line = this.patterns.get(i);
                
                for (int j = 0; j < 9; j++) {
                    char state = line.charAt(j);
                    if (state != '0' && state != 1) {
                        throw new IllegalArgumentException("Pattern content must be '0' or '1'!");
                    }
                    
                    stacks[i * 9 + j] = state == 1 ? this.content : null;
                }
            }
            
            InvMask mask = new InvMask(stacks);
            
            return mask;
        }
    }
}
