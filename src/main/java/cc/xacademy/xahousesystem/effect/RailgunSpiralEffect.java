package cc.xacademy.xahousesystem.effect;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import cc.xacademy.xahousesystem.util.MathUtil;

public class RailgunSpiralEffect implements IEffect {

    private static final double SPEED = 0.75;
    private static final double EXPLOSION_GAP = 3;
    
    private final World world;
    private final Location start;
    private final Vector dir;
    private final Vector radius;
    private final int explosionTimes;
    private final int explosionRadius;
    private int particleTickCount;
    
    public RailgunSpiralEffect(Player player, int explosionTimes, int explosionRadius) {
        this.world = player.getWorld();
        this.explosionTimes = explosionTimes;
        this.explosionRadius = explosionRadius;
        
        this.start = player.getEyeLocation().clone();
        this.dir = this.start.getDirection();
        this.radius = this.dir.clone().crossProduct(new Vector(0, 1, 0));
        
        if (this.radius.length() != 0) {
            this.radius.normalize().multiply(0.5);
        }
        
        RayTraceResult result = player.getWorld().rayTrace(
                this.start,
                this.dir,
                256, FluidCollisionMode.NEVER,
                false, 0, e -> e != player);
        
        this.particleTickCount = result == null ?
                256 : (int) result.getHitPosition().distance(this.start.toVector());
        
        this.particleTickCount /= SPEED;
    }
    
    @Override
    public boolean tick() {
        double time = (double) this.world.getGameTime();
        Vector point = MathUtil.getCirclePoint(this.start.toVector(), this.dir, this.radius, time);
        
        this.world.spawnParticle(Particle.FLAME, point.toLocation(this.world), 0, 0, 0, 0);
        this.start.add(this.dir);
        
        return --particleTickCount < 0;
    }

}
