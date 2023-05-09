package voxel.game.math;

import static java.lang.Math.PI;

public class Trigonometry {
    
    public static final float degreesToRadians = (float) (PI / 180d);
    public static final float radiansToDegrees = (float) (180d / PI);

    public static float coTangent(float angle) {
        return (float) (1.0f / Math.tan(angle));
    }

    public static float degreesToRadians(float degrees) {
        return degrees * degreesToRadians;
    }
    
}
