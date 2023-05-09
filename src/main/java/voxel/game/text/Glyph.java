package voxel.game.text;

import voxel.game.math.Vector4f;

public class Glyph {

    public final float width;
    public final float height;
    public final float x;
    public final float y;
    
    public final Vector4f texCoor;

    public Glyph(float width, float height, float x, float y, Vector4f texCoor) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.texCoor = texCoor;
    }
    
}

