package voxel.game.math;

import static voxel.game.math.Trigonometry.coTangent;
import static voxel.game.math.Trigonometry.degreesToRadians;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Matrix4f {

    public float m00, m01, m02, m03;
    public float m10, m11, m12, m13;
    public float m20, m21, m22, m23;
    public float m30, m31, m32, m33;

    public Matrix4f() {
    	m00 = 0.0f; m01 = 0.0f; m02 = 0.0f; m03 = 0.0f;        
        m10 = 0.0f; m11 = 0.0f; m12 = 0.0f; m13 = 0.0f;        
        m20 = 0.0f; m21 = 0.0f; m22 = 0.0f; m23 = 0.0f;        
        m30 = 0.0f; m31 = 0.0f; m32 = 0.0f; m33 = 0.0f;
    }
    
    public Matrix4f(boolean identity) {
    	m00 = 1.0f; m01 = 0.0f; m02 = 0.0f; m03 = 0.0f;
        m10 = 0.0f; m11 = 1.0f; m12 = 0.0f; m13 = 0.0f;
        m20 = 0.0f; m21 = 0.0f; m22 = 1.0f; m23 = 0.0f;
        m30 = 0.0f; m31 = 0.0f; m32 = 0.0f; m33 = 1.0f;
    }
    
    public Matrix4f(float e) {
    	m00 = e; m01 = e; m02 = e; m03 = e;
        m10 = e; m11 = e; m12 = e; m13 = e;
        m20 = e; m21 = e; m22 = e; m23 = e;
        m30 = e; m31 = e; m32 = e; m33 = e;
    }
    
    public Matrix4f(float m00, float m01, float m02, float m03,
					float m10, float m11, float m12, float m13,
					float m20, float m21, float m22, float m23,
					float m30, float m31, float m32, float m33) {
    		this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
    		this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
    		this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
    		this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
    }
    
    public Matrix4f(Vector4f row1, Vector4f row2, Vector4f row3, Vector4f row4) {
    	m00 = row1.x; m01 = row1.y; m02 = row1.z; m03 = row1.w;        
        m10 = row2.x; m11 = row2.y; m12 = row2.z; m13 = row2.w;        
        m20 = row3.x; m21 = row3.y; m22 = row3.z; m23 = row3.w;        
        m30 = row4.x; m31 = row4.y; m32 = row4.z; m33 = row4.w;
    }
    
    public void set(float m00, float m01, float m02, float m03,
					float m10, float m11, float m12, float m13,
					float m20, float m21, float m22, float m23,
					float m30, float m31, float m32, float m33) {
		this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
		this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
		this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
		this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
	}
    
    public void set(Vector4f row1, Vector4f row2, Vector4f row3, Vector4f row4) {
    	m00 = row1.x; m01 = row1.y; m02 = row1.z; m03 = row1.w;        
        m10 = row2.x; m11 = row2.y; m12 = row2.z; m13 = row2.w;        
        m20 = row3.x; m21 = row3.y; m22 = row3.z; m23 = row3.w;        
        m30 = row4.x; m31 = row4.y; m32 = row4.z; m33 = row4.w;
    }
    
    public void clear() {
    	m00 = 0.0f; m01 = 0.0f; m02 = 0.0f; m03 = 0.0f;        
        m10 = 0.0f; m11 = 0.0f; m12 = 0.0f; m13 = 0.0f;        
        m20 = 0.0f; m21 = 0.0f; m22 = 0.0f; m23 = 0.0f;        
        m30 = 0.0f; m31 = 0.0f; m32 = 0.0f; m33 = 0.0f;
    }
    
    public void clear(float value) {
    	m00 = value; m01 = value; m02 = value; m03 = value;        
        m10 = value; m11 = value; m12 = value; m13 = value;        
        m20 = value; m21 = value; m22 = value; m23 = value;        
        m30 = value; m31 = value; m32 = value; m33 = value;
    }
    
    public void toIdentity() {
    	m00 = 1.0f; m01 = 0.0f; m02 = 0.0f; m03 = 0.0f;        
        m10 = 0.0f; m11 = 1.0f; m12 = 0.0f; m13 = 0.0f;        
        m20 = 0.0f; m21 = 0.0f; m22 = 1.0f; m23 = 0.0f;        
        m30 = 0.0f; m31 = 0.0f; m32 = 0.0f; m33 = 1.0f;
    }
    
    public Matrix4f add(Matrix4f other) {    	
    	set(m00 + other.m00, m01 + other.m01, m02 + other.m02, m03 + other.m03,
			m10 + other.m10, m11 + other.m11, m12 + other.m12, m13 + other.m13,
			m20 + other.m20, m21 + other.m21, m22 + other.m22, m23 + other.m23,
			m30 + other.m30, m31 + other.m31, m32 + other.m32, m33 + other.m33);
    	return this;
    }
    
    public Matrix4f subtract(Matrix4f other) {    	
    	set(m00 - other.m00, m01 - other.m01, m02 - other.m02, m03 - other.m03,
			m10 - other.m10, m11 - other.m11, m12 - other.m12, m13 - other.m13,
			m20 - other.m20, m21 - other.m21, m22 - other.m22, m23 - other.m23,
			m30 - other.m30, m31 - other.m31, m32 - other.m32, m33 - other.m33);
    	return this;
    }

    public Matrix4f negate() {
    	m00 = -m00; m01 = -m01; m02 = -m02; m03 = -m03;
    	m10 = -m10; m11 = -m11; m12 = -m12; m13 = -m13;
    	m20 = -m20; m21 = -m21; m22 = -m22; m23 = -m23;
    	m30 = -m30; m31 = -m31; m32 = -m32; m33 = -m33;
        return this;
    }
    
    public Matrix4f scale(float scalar) {        
        set(m00 * scalar, m01 * scalar, m02 * scalar, m03 * scalar,
			m10 * scalar, m11 * scalar, m12 * scalar, m13 * scalar,
			m20 * scalar, m21 * scalar, m22 * scalar, m23 * scalar,
			m30 * scalar, m31 * scalar, m32 * scalar, m33 * scalar);
        return this;
    }
        
    public Matrix4f transpose() {
        set(m00, m10, m20, m30,
			m01, m11, m21, m31,
			m02, m12, m22, m32,
			m03, m13, m23, m33);
        return this;
    }
    
    public Matrix4f rotate(float angle, Vector3f vec) {
        float c = (float) Math.cos(Math.toRadians(angle));
        float s = (float) Math.sin(Math.toRadians(angle));
        float x = vec.x, y = vec.y, z = vec.z;
        if (vec.length() != 1f) {
            vec = vec.normalize();
            x = vec.x;
            y = vec.y;
            z = vec.z;
        }

        set(x * x * (1f - c) + c,     x * y * (1f - c) - z * s, x * z * (1f - c) + y * s, 0.0f,
    		y * x * (1f - c) + z * s, y * y * (1f - c) + c,     y * z * (1f - c) - x * s, 0.0f,
    		x * z * (1f - c) - y * s, y * z * (1f - c) + x * s, z * z * (1f - c) + c,	  0.0f,
    		0.0f,					  0.0f,						0.0f,					  0.0f);

        return this;
    }
    
    public Matrix4f translate(float tx, float ty, float tz) {
        m30 += m00 * tx + m10 * ty + m20 * tz;
        m31 += m01 * tx + m11 * ty + m21 * tz;
        m32 += m02 * tx + m12 * ty + m22 * tz;
        m33 += m03 * tx + m13 * ty + m23 * tz;
        return this;
    }
    
    public Matrix4f perspective(final float fovy, final float aspect, final float zNear, final float zFar) {
        float y_scale = coTangent(degreesToRadians(fovy / 2.0f));
        float x_scale = y_scale / aspect;
        float frustrum_length = zFar - zNear;
        
        set(x_scale, 0.0f,    0.0f,                                        0.0f,
			0.0f,    y_scale, 0.0f,                                        0.0f,
			0.0f,    0.0f,    -((zFar + zNear) / frustrum_length),        -1.0f,
			0.0f,    0.0f,    -((2.0f * zNear * zFar) / frustrum_length),  0.0f);
        return this;
    }
    
    public Matrix4f orthographic(float left, float right, float bottom, float top, float zNear, float zFar) {       
        set(2.0f / (right - left), 0.0f, 0.0f, 0.0f,
			0.0f, 2.0f / (top - bottom), 0.0f, 0.0f,
			0.0f, 0.0f, (-2.0f) / (zFar - zNear), 0.0f,
			
			-((right + left) / (right - left)), 
			-((top + bottom) / (top - bottom)), 
			-((zFar + zNear) / (zFar - zNear)), 
			1.0f);
        return this;
    }
    
    public Matrix4f lookAt(Vector3f position, Vector3f direction, Vector3f up, Vector3f right) {         
        set(right.x, up.x, -direction.x, 0.0f,
			right.y, up.y, -direction.y, 0.0f,
			right.z, up.z, -direction.z, 0.0f,
			
			-right.x * position.x - right.y * position.y - right.z * position.z, 
			-up.x * position.x - up.y * position.y - up.z * position.z, 
			direction.x * position.x + direction.y * position.y + direction.z * position.z, 
			1.0f);
        return this;
    }
        
    public Vector4f multiply(Vector4f vector, Vector4f dest) {
    	dest.set(m00 * vector.x + m01 * vector.y + m02 * vector.z + m03 * vector.w,
    			 m10 * vector.x + m11 * vector.y + m12 * vector.z + m13 * vector.w,
    			 m20 * vector.x + m21 * vector.y + m22 * vector.z + m23 * vector.w,
    			 m30 * vector.x + m31 * vector.y + m32 * vector.z + m33 * vector.w);
    	return dest;
    }
    
    public Vector4f multiply(Vector3f vec, Vector4f dest) {
    	float x = vec.x, y = vec.y, z = vec.z, w = 0.0f;
        dest.set(m00 * x + m01 * y + m02 * z + m03 * w,
                 m10 * x + m11 * y + m12 * z + m13 * w,
                 m20 * x + m21 * y + m22 * z + m23 * w,
                 m30 * x + m31 * y + m32 * z + m33 * w);
        return dest;
    }

    public Matrix4f multiply(Matrix4f other, Matrix4f dest) {
    	dest.set((m00 * other.m00) + (m10 * other.m01) + (m20 * other.m02) + (m30 * other.m03),
				 (m01 * other.m00) + (m11 * other.m01) + (m21 * other.m02) + (m31 * other.m03),
				 (m02 * other.m00) + (m12 * other.m01) + (m22 * other.m02) + (m32 * other.m03),
				 (m03 * other.m00) + (m13 * other.m01) + (m23 * other.m02) + (m33 * other.m03),
				 (m00 * other.m10) + (m10 * other.m11) + (m20 * other.m12) + (m30 * other.m13),
				 (m01 * other.m10) + (m11 * other.m11) + (m21 * other.m12) + (m31 * other.m13),
				 (m02 * other.m10) + (m12 * other.m11) + (m22 * other.m12) + (m32 * other.m13),
				 (m03 * other.m10) + (m13 * other.m11) + (m23 * other.m12) + (m33 * other.m13),
				 (m00 * other.m20) + (m10 * other.m21) + (m20 * other.m22) + (m30 * other.m23),
				 (m01 * other.m20) + (m11 * other.m21) + (m21 * other.m22) + (m31 * other.m23),
				 (m02 * other.m20) + (m12 * other.m21) + (m22 * other.m22) + (m32 * other.m23),
				 (m03 * other.m20) + (m13 * other.m21) + (m23 * other.m22) + (m33 * other.m23),
				 (m00 * other.m30) + (m10 * other.m31) + (m20 * other.m32) + (m30 * other.m33),
				 (m01 * other.m30) + (m11 * other.m31) + (m21 * other.m32) + (m31 * other.m33),
				 (m02 * other.m30) + (m12 * other.m31) + (m22 * other.m32) + (m32 * other.m33),
				 (m03 * other.m30) + (m13 * other.m31) + (m23 * other.m32) + (m33 * other.m33));
    	return dest;
    }
        
    FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
    
    public FloatBuffer getBuffer() {
    	buffer.clear();
        buffer.put(m00).put(m01).put(m02).put(m03);
        buffer.put(m10).put(m11).put(m12).put(m13);
        buffer.put(m20).put(m21).put(m22).put(m23);
        buffer.put(m30).put(m31).put(m32).put(m33);
        buffer.flip();
        return buffer;
    }
    
    public void print(String name) {
    	System.out.println(name);
    	System.out.format("[% 10.4f % 10.4f % 10.4f % 10.4f \n",   m00, m01, m02, m03);
    	System.out.format(" % 10.4f % 10.4f % 10.4f % 10.4f \n",   m10, m11, m12, m13);
    	System.out.format(" % 10.4f % 10.4f % 10.4f % 10.4f \n",   m20, m21, m22, m23);
    	System.out.format(" % 10.4f % 10.4f % 10.4f % 10.4f]\n\n", m30, m31, m32, m33);
    }
    
    public void print() {
    	System.out.format("[% 10.4f % 10.4f % 10.4f % 10.4f \n",   m00, m01, m02, m03);
    	System.out.format(" % 10.4f % 10.4f % 10.4f % 10.4f \n",   m10, m11, m12, m13);
    	System.out.format(" % 10.4f % 10.4f % 10.4f % 10.4f \n",   m20, m21, m22, m23);
    	System.out.format(" % 10.4f % 10.4f % 10.4f % 10.4f]\n\n", m30, m31, m32, m33);
    }
    
}

