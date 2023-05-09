package voxel.game.math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Matrix3f {

    public float m00, m01, m02;
    public float m10, m11, m12;
    public float m20, m21, m22;
    
    public Matrix3f() {
    	m00 = 0.0f; m01 = 0.0f; m02 = 0.0f;    	
    	m10 = 0.0f; m11 = 0.0f; m12 = 0.0f;    	
    	m20 = 0.0f; m21 = 0.0f; m22 = 0.0f;
    }
    
    public Matrix3f(boolean identity) {
    	 m00 = 1f; m01 = 0f; m02 = 0f;
         m10 = 0f; m11 = 1f; m12 = 0f;
         m20 = 0f; m21 = 0f; m22 = 1f;
    }
    
    public Matrix3f(float m00, float m01, float m02,
    				float m10, float m11, float m12,
    				float m20, float m21, float m22) {
    	this.m00 = m00; this.m01 = m01; this.m02 = m02;    	
    	this.m10 = m10; this.m11 = m11; this.m12 = m12;    	
    	this.m20 = m20; this.m21 = m21; this.m22 = m22;
    }

    public Matrix3f(Vector3f row1, Vector3f row2, Vector3f row3) {
    	m00 = row1.x; m01 = row1.y; m02 = row1.z;    	
    	m10 = row2.x; m11 = row2.y; m12 = row2.z;    	
    	m20 = row3.x; m21 = row3.y; m22 = row3.z;
    }
    
    public void set(float m00, float m01, float m02,
					float m10, float m11, float m12,
					float m20, float m21, float m22) {
		this.m00 = m00; this.m01 = m01; this.m02 = m02;    	
		this.m10 = m10; this.m11 = m11; this.m12 = m12;    	
		this.m20 = m20; this.m21 = m21; this.m22 = m22;
	}
    
    public void set(Vector3f row1, Vector3f row2, Vector3f row3) {
    	m00 = row1.x; m01 = row1.y; m02 = row1.z;    	
    	m10 = row2.x; m11 = row2.y; m12 = row2.z;    	
    	m20 = row3.x; m21 = row3.y; m22 = row3.z;
    }
    
    public void clear() {
    	m00 = 0.0f; m01 = 0.0f; m02 = 0.0f;    	
    	m10 = 0.0f; m11 = 0.0f; m12 = 0.0f;    	
    	m20 = 0.0f; m21 = 0.0f; m22 = 0.0f;
    }
    
    public void clear(float value) {
    	m00 = value; m01 = value; m02 = value;    	
    	m10 = value; m11 = value; m12 = value;    	
    	m20 = value; m21 = value; m22 = value;
    }
    
    public void toIdentity() {
   	 	m00 = 1f; m01 = 0f; m02 = 0f;
        m10 = 0f; m11 = 1f; m12 = 0f;
        m20 = 0f; m21 = 0f; m22 = 1f;
   }

    public Matrix3f add(Matrix3f other) {
        set(m00 + other.m00, m01 + other.m01, m02 + other.m02,
			m10 + other.m10, m11 + other.m11, m12 + other.m12,
			m20 + other.m20, m21 + other.m21, m22 + other.m22);
        return this;
    }

    public Matrix3f subtract(Matrix3f other) {
    	set(m00 - other.m00, m01 - other.m01, m02 - other.m02,
			m10 - other.m10, m11 - other.m11, m12 - other.m12,
			m20 - other.m20, m21 - other.m21, m22 - other.m22);
    	return this;
    }
    
    public Matrix3f negate() {
    	set(-m00, -m01, -m02,
			-m10, -m11, -m12,
			-m20, -m21, -m22);
    	return this;
    }

    public Matrix3f scale(float scalar) {        
        set(m00 * scalar, m01 * scalar, m02 * scalar,        
			m10 * scalar, m11 * scalar, m12 * scalar,        
			m20 * scalar, m21 * scalar, m22 * scalar);
        return this;
    }
    
    public Matrix3f transpose() {
        set(m00, m10, m20,        
			m01, m11, m21,
			m02, m12, m22);
        return this;
    }

    public Vector3f multiply(Vector3f vector, Vector3f dest) {
        dest.set((m00 * vector.x) + (m01 * vector.y) + (m02 * vector.z), 
        		 (m10 * vector.x) + (m11 * vector.y) + (m12 * vector.z), 
        		 (m20 * vector.x) + (m21 * vector.y) + (m22 * vector.z));
        return dest;
    }

    public Matrix3f multiply(Matrix3f other, Matrix3f dest) {
    	dest.set((m00 * other.m00) + (m10 * other.m01) + (m20 * other.m02), 
				 (m01 * other.m00) + (m11 * other.m01) + (m21 * other.m02),
				 (m02 * other.m00) + (m12 * other.m01) + (m22 * other.m02),        					
				 (m00 * other.m10) + (m10 * other.m11) + (m20 * other.m12), 
				 (m01 * other.m10) + (m11 * other.m11) + (m21 * other.m12), 
				 (m02 * other.m10) + (m12 * other.m11) + (m22 * other.m12),                			
				 (m00 * other.m20) + (m10 * other.m21) + (m20 * other.m22), 
				 (m01 * other.m20) + (m11 * other.m21) + (m21 * other.m22), 
				 (m02 * other.m20) + (m12 * other.m21) + (m22 * other.m22));
    	return dest;
    }

    public FloatBuffer getBuffer() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(9);
        buffer.put(m00).put(m01).put(m02);
        buffer.put(m10).put(m11).put(m12);
        buffer.put(m20).put(m21).put(m22);
        buffer.flip();
        return buffer;
    }
    
    public void print() {
    	System.out.format("[% 10.4f % 10.4f % 10.4f \n",   m00, m01, m02);
    	System.out.format(" % 10.4f % 10.4f % 10.4f \n",   m10, m11, m12);
    	System.out.format(" % 10.4f % 10.4f % 10.4f]\n\n", m20, m21, m22);
    }
    
}

