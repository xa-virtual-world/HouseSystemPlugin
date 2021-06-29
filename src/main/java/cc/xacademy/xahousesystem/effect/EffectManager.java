package cc.xacademy.xahousesystem.effect;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * A manager for all effects. Not stored to disk
 * and not world-separated.
 */
public class EffectManager extends BukkitRunnable {

    private final Set<IEffect> effects;
    
    public EffectManager() {
        this.effects = new HashSet<>();
    }
    
    public void init(JavaPlugin plugin) {
        this.runTaskTimer(plugin, 0, 1);
    }
    
    public void addEffect(IEffect effect) {
        this.effects.add(effect);
    }

    @Override
    public void run() {
        effects.removeIf(effect -> effect.tick());
    }
}
