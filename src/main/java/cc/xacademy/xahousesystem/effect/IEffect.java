package cc.xacademy.xahousesystem.effect;

public interface IEffect {

    /**
     * Updates the effect.
     * 
     * @return whether this effect should be removed after this tick
     */
    public boolean tick();    
}
