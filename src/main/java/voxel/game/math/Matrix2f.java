package voxel.game.math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Matrix2f {

    public float m00, m01;
    public float m10, m11;
    
    public Matrix2f() {
        m00 = 0.0f; m01 = 0.0f;
        m10 = 0.0f; m11 = 0.0f;
    }

    public Matrix2f(boolean identity) {
    	 m00 = 1.0f; m01 = 0.0f;
         m10 = 0.0f; m11 = 1.0f;
    }
    
    public Matrix2f(float m00, float m01, 
    				float m10, float m11) {
        this.m00 = m00; this.m01 = m01;
        this.m10 = m10; this.m11 = m11;
    }

    public Matrix2f(Vector2f row1, Vector2f row2) {
        m00 = row1.x; m01 = row1.y;
        m10 = row2.x; m11 = row2.y;
    }
    
    public void set(float m00, float m01, 
    				float m10, float m11) {
    	this.m00 = m00; this.m01 = m01;
    	this.m10 = m10; this.m11 = m11;
	}
    
    public void set(Vector2f row1, Vector2f row2) {
        m00 = row1.x; m01 = row1.y;
        m10 = row2.x; m11 = row2.y;
    }
    
    public void clear() {
        m00 = 0.0f; m01 = 0.0f;
        m10 = 0.0f; m11 = 0.0f;
    }
    
    public void clear(float value) {
        m00 = value; m01 = value;
        m10 = value; m11 = value;
    }
    
    public void toIdentity() {
   	 	m00 = 1.0f; m01 = 0.0f;
        m10 = 0.0f; m11 = 1.0f;
    }

    public Matrix2f add(Matrix2f other) {
        set(m00 + other.m00, m01 + other.m01, 
            m10 + other.m10, m11 + other.m11);
        return this;
    }
    
    public Matrix2f subtract(Matrix2f other) {
    	set(m00 - other.m00, m01 - other.m01, 
			m10 - other.m10, m11 - other.m11);
    	return this;
    }

    public Matrix2f negate() {
    	set(-m00, -m01,
			-m10, -m11);
    	return this;
    }    

    public Matrix2f scale(float scalar) {
        set(m00 * scalar, m01 * scalar,
			m10 * scalar, m11 * scalar);
        return this;
    }
    
    public Matrix2f transpose() {
        set(m00, m10,
			m01, m11);
        return this;
    }

    public Vector2f multiply(Vector2f vector, Vector2f dest) {
        dest.set((m00 * vector.x) + (m01 * vector.y), 
        		 (m10 * vector.x) + (m11 * vector.y));
        return dest;
    }

    public Matrix2f multiply(Matrix2f other, Matrix2f dest) {
        dest.set((m00 * other.m00) + (m10 * other.m01), 
        		 (m01 * other.m00) + (m11 * other.m01), 
        		 (m00 * other.m10) + (m10 * other.m11), 
        		 (m01 * other.m10) + (m11 * other.m11));
        return dest;
    }

    public FloatBuffer getBuffer() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
        buffer.put(m00).put(m01);
        buffer.put(m10).put(m11);
        buffer.flip();
        return buffer;
    }
    
    public void print() {
    	System.out.format("[% 10.4f % 10.4f \n",   m00, m01);
    	System.out.format(" % 10.4f % 10.4f]\n\n", m10, m11);
    }
    
}

