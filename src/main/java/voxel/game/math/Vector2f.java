package voxel.game.math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Vector2f {

    public float x;
    public float y;

    public Vector2f() {
        x = 0.0f;
        y = 0.0f;
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public void clear() {
   	 	x = 0.0f;
        y = 0.0f;
    }
   
    public void clear(float value) {
  	 	x = value;
  	 	y = value;
    }

    public float lengthSquared() {
        return (x * x) + (y * y);
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    public Vector2f normalize() {
        return divide(length());
    }

    public Vector2f add(Vector2f other, Vector2f dest) {
    	dest.set(x + other.x, 
        		 y + other.y);
    	return dest;
    }
    
    public Vector2f add(Vector2f other) {
        set(x + other.x, 
        	y + other.y);
        return this;
    }
    
    public Vector2f subtract(Vector2f other, Vector2f dest) {
    	dest.set(x - other.x, 
    			 y - other.y);
    	return dest;
    }
    
    public Vector2f subtract(Vector2f other) {
    	set(x - other.x, 
    		y - other.y);
    	return this;
    }

    public Vector2f negate() {
    	set(-x, -y);
    	return this;
    }

    public Vector2f scale(float scalar) {
        return new Vector2f(x * scalar, 
        					y * scalar);
    }

    public Vector2f divide(float scalar) {
        return scale(1.0f / scalar);
    }

    public float dot(Vector2f other) {
        return (x * other.x) + (y * other.y);
    }

    public FloatBuffer getBuffer() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(2);
        buffer.put(x).put(y);
        buffer.flip();
        return buffer;
    }
    
    public void print() {
    	System.out.format("[% 10.4f % 10.4f]\n", x, y);
    }
    
}

