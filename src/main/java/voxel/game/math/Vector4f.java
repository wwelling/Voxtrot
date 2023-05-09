package voxel.game.math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Vector4f {

    public float x;
    public float y;
    public float z;
    public float w;

    public Vector4f() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        w = 0.0f;
    }

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public void set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public void clear() {
    	 x = 0.0f;
         y = 0.0f;
         z = 0.0f;
         w = 0.0f;
    }
    
    public void clear(float value) {
   	 	x = value;
        y = value;
        z = value;
        w = value;
   }

    public float lengthSquared() {
        return (x * x) + (y * y) + (z * z) + (w * w);
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    public Vector4f normalize() {
        return divide(length());
    }
    
    public Vector4f add(Vector4f other, Vector4f dest) {
        dest.set(x + other.x, 
        		 y + other.y, 
        		 z + other.z, 
        		 w + other.w);
        return dest;
    }

    public Vector4f add(Vector4f other) {
        set(x + other.x, 
        	y + other.y, 
        	z + other.z, 
        	w + other.w);
        return this;
    }
    
    public Vector4f subtract(Vector4f other, Vector4f dest) {
   	 	dest.set(x - other.x, 
   	 			 y - other.y, 
   	 			 z - other.z, 
   	 			 w - other.w);
   	 	return dest;
    }
    
   public Vector4f subtract(Vector4f other) {
   	 	set(x - other.x, 
   	 		y - other.y, 
   	 		z - other.z, 
   	 		w - other.w);
   	 	return this;
   }

    public Vector4f negate() {
    	set(-x, -y, -z, -w);
    	return this;
    }

    public Vector4f scale(float scalar) {
        return new Vector4f(x * scalar, 
        					y * scalar, 
        					z * scalar, 
        					w * scalar);
    }

    public Vector4f divide(float scalar) {
        return scale(1.0f / scalar);
    }

    public float dot(Vector4f other) {
        return (x * other.x) + 
        	   (y * other.y) + 
        	   (z * other.z) + 
        	   (w * other.w);
    }

    public FloatBuffer getBuffer() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
        buffer.put(x).put(y).put(z).put(w);
        buffer.flip();
        return buffer;
    }
    
    public void print() {
    	System.out.format("[% 10.4f % 10.4f % 10.4f % 10.4f]\n", x, y, z, w);
    }
    
}

