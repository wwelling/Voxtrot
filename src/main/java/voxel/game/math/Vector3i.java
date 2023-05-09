package voxel.game.math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Vector3i {

    public int x;
    public int y;
    public int z;

    public Vector3i() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void clear() {
   	 	x = 0;
        y = 0;
        z = 0;
    }
   
    public void clear(int value) {
  	 	x = value;
  	 	y = value;
  	 	z = value;
    }

    public int lengthSquared() {
        return (x * x) + (y * y) + (z * z);
    }

    public int length() {
        return (int) Math.sqrt(lengthSquared());
    }
            
    public int magnitude() {
    	return (int) Math.sqrt((this.x*this.x)+(this.y*this.y)+(this.z*this.z));
    }

    public Vector3i normalize() {
        return divide(length());
    }

    public Vector3i add(Vector3i other, Vector3i dest) {
        dest.set(x + other.x, 
        		 y + other.y, 
        		 z + other.z);
        return dest;
    }
    
    public Vector3i add(Vector3i other) {
        set(x + other.x, 
        	y + other.y, 
        	z + other.z);
        return this;
    }
    
    public Vector3i subtract(Vector3i other, Vector3i dest) {
    	dest.set(x - other.x, 
    			 y - other.y, 
    			 z - other.z);
    	return dest;
    }
    
    public Vector3i subtract(Vector3i other) {
        set(x - other.x, 
        	y - other.y, 
        	z - other.z);
        return this;
    }
        
    public Vector3i negate() {
    	set(-x, -y, -z);
    	return this;
    }

    public Vector3i scale(int scalar) {
        return new Vector3i(x * scalar, 
        					y * scalar, 
        					z * scalar);
    }

    public Vector3i divide(int scalar) {
        return scale(1 / scalar);
    }

    public int dot(Vector3i other) {
        return (x * other.x) + (y * other.y) + (z * other.z);
    }

    public Vector3i cross(Vector3i other) {
        return new Vector3i(y * other.z - z * other.y, 
        					z * other.x - x * other.z, 
        					x * other.y - y * other.x);
    }

    public static int distance(Vector3i start, Vector3i end) {
        return (int) Math.sqrt((end.x - start.x) * (end.x - start.x) + 
        					   (end.y - start.y) * (end.y - start.y) + 
        					   (end.z - start.z) * (end.z - start.z));
    }

    public int distance(Vector3i v) {
        return (int) Math.sqrt((v.x - x) * (v.x - x) + 
        					   (v.y - y) * (v.y - y) + 
        					   (v.z - z) * (v.z - z));
    }
    
    public int distance(Vector3f v) {
        return (int) Math.sqrt((v.x - x) * (v.x - x) + 
        					   (v.y - y) * (v.y - y) + 
        					   (v.z - z) * (v.z - z));
    }
    
    public int norm() {
    	return (int) Math.sqrt((x * x) + (y * y) + (z * z));
    }

    @JsonIgnore
    public FloatBuffer getBuffer() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(3);
        buffer.put(x).put(y).put(z);
        buffer.flip();
        return buffer;
    }
    
    public void print() {
    	System.out.format("[% 10.4f % 10.4f % 10.4f]\n", x, y, z);
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Vector3i)) {
            return false;
        }

        Vector3i otherVector = (Vector3i) object;
        return this.x == otherVector.x && this.y == otherVector.y && this.z == otherVector.z;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Float.valueOf(x).hashCode();
        result = 31 * result + Float.valueOf(y).hashCode();
        result = 31 * result + Float.valueOf(z).hashCode();
        return result;
    }
    
}

