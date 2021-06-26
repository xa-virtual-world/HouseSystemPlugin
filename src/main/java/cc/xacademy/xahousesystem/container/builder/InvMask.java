package cc.xacademy.xahousesystem.container.builder;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class InvMask {
    
    private final boolean[] mask;
    
    public void apply(Inventory inv, ItemStack stack) {
        for (int i = 0; i < Math.min(this.mask.length, inv.getSize()); i++) {
            if (this.mask[i]) {
                inv.setItem(i, new ItemStack(stack));
            }
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        
        private List<String> patterns;
        
        private Builder() {
            this.patterns = new ArrayList<>();
        }
        
        public Builder pattern(String data) {
            if (data.length() != 9) {
                throw new IllegalArgumentException("Pattern length must be 9!");
            }
            
            if (patterns.size() >= 6) {
                throw new IllegalStateException("Too many pattern rows!");
            }
            
            this.patterns.add(data);
            
            return this;
        }
        
        public InvMask build() {
            
            boolean[] mask = new boolean[this.patterns.size() * 9];
            
            for (int i = 0; i < this.patterns.size(); i++) {
                String line = this.patterns.get(i);
                
                for (int j = 0; j < 9; j++) {
                    char state = line.charAt(j);
                    if (state != '0' && state != '1') {
                        throw new IllegalArgumentException("Pattern content must be '0' or '1'!");
                    }
                    
                    mask[i * 9 + j] = state == '1';
                }
            }
            
            InvMask out = new InvMask(mask);
            
            return out;
        }
    }
}
