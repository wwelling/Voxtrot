package voxel.game.math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Vector3f {

    public float x;
    public float y;
    public float z;

    public Vector3f() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void set(Vector3f vec) {
        x = vec.x;
        y = vec.y;
        z = vec.z;
    }
    
    public void set(Vector4f vec) {
        x = vec.x;
        y = vec.y;
        z = vec.z;
    }
    
    public void clear() {
   	 	x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }
   
    public void clear(float value) {
  	 	x = value;
  	 	y = value;
  	 	z = value;
    }

    public float lengthSquared() {
        return (x * x) + (y * y) + (z * z);
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }
        
    public float magnitude() {
    	return (float) Math.sqrt((this.x*this.x)+(this.y*this.y)+(this.z*this.z));
    }

    public Vector3f normalize() {
        set(divide(length()));
        return this;
    }

    public Vector3f add(Vector3f other, Vector3f dest) {
        dest.set(x + other.x, 
        		 y + other.y, 
        		 z + other.z);
        return dest;
    }
    
    public Vector3f add(Vector3f other) {
        set(x + other.x, 
        	y + other.y, 
        	z + other.z);
        return this;
    }
    
    public Vector3f subtract(Vector3f other, Vector3f dest) {
        dest.set(x - other.x, 
        		 y - other.y, 
        		 z - other.z);
        return dest;
    }
    
    public Vector3f subtract(Vector3f other) {
        set(x - other.x, 
        	y - other.y, 
        	z - other.z);
        return this;
    }
        
    public Vector3f negate() {
    	set(-x, -y, -z);
    	return this;
    }

    public Vector3f scale(float scalar) {
        return new Vector3f(x * scalar, 
        					y * scalar, 
        					z * scalar);
    }

    public Vector3f divide(float scalar) {
        return scale(1.0f / scalar);
    }

    public float dot(Vector3f other) {
        return (x * other.x) + (y * other.y) + (z * other.z);
    }

    public Vector3f cross(Vector3f other) {
        return new Vector3f(y * other.z - z * other.y, 
        					z * other.x - x * other.z, 
        					x * other.y - y * other.x);
    }

    public static float distance(Vector3f start, Vector3f end) {
        return (float) Math.sqrt((end.x - start.x) * (end.x - start.x) + 
        						 (end.y - start.y) * (end.y - start.y) + 
        						 (end.z - start.z) * (end.z - start.z));
    }

    public float distance(Vector3f v) {
        return (float) Math.sqrt((v.x - x) * (v.x - x) + 
        						 (v.y - y) * (v.y - y) + 
        						 (v.z - z) * (v.z - z));
    }
    
    public float norm() {
    	return (float) Math.sqrt((x * x) + (y * y) + (z * z));
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
        if (!(object instanceof Vector3f)) {
            return false;
        }

        Vector3f otherVector = (Vector3f) object;
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

