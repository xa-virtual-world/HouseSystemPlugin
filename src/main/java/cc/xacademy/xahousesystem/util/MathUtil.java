package cc.xacademy.xahousesystem.util;

import org.bukkit.util.Vector;

public class MathUtil {

    /**
     * Gets a point (offset by time) on a circle.
     * 
     * @param center the center of the circle
     * @param the unit vector of the forward direction
     * @param radius a relative vector on center specifying the radius
     * @param time the time, duh
     */
    public static Vector getCirclePoint(Vector center, Vector forword, Vector radius, double time) {
        Vector rotated = radius.clone().rotateAroundAxis(forword, time);
        rotated.add(center);
        
        return rotated;
    }
}
