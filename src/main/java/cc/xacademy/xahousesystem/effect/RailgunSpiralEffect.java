package cc.xacademy.xahousesystem.effect;

public class RailgunSpiralEffect implements IEffect {

    private int count;
    
    @Override
    public boolean tick() {
        System.out.println(123);
        
        return count++ > 200;
    }

}
