package voxel.game.graphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;

import java.nio.FloatBuffer;

import voxel.game.math.Vector2f;
import voxel.game.math.Vector3f;
import voxel.game.math.Vector4f;
import voxel.game.math.Matrix2f;
import voxel.game.math.Matrix3f;
import voxel.game.math.Matrix4f;

public class ShaderProgram {

    private final int id;

    public ShaderProgram() {
        id = glCreateProgram();
    }

    public void attachShader(Shader shader) {
        glAttachShader(id, shader.getID());
    }

    public void bindFragmentDataLocation(int number, CharSequence name) {
        glBindFragDataLocation(id, number, name);
    }

    public void link() {
        glLinkProgram(id);
        checkStatus();
    }

    public int getAttributeLocation(CharSequence name) {
        return glGetAttribLocation(id, name);
    }

    public void enableVertexAttribute(int location) {
        glEnableVertexAttribArray(location);
    }

    public void disableVertexAttribute(int location) {
        glDisableVertexAttribArray(location);
    }

    public void pointVertexAttribute(int location, int size, int stride, int offset) {
        glVertexAttribPointer(location, size, GL_FLOAT, false, stride, offset);
    }
    
    public void pointVertexAttribute(int location, int size, boolean normalized, int stride, int offset) {
        glVertexAttribPointer(location, size, GL_FLOAT, normalized, stride, offset);
    }

    public int getUniformLocation(CharSequence name) {
        return glGetUniformLocation(id, name);
    }

    public void setUniform(int location, int value) {
        glUniform1i(location, value);
    }

    public void setUniform(int location, Vector2f value) {
        glUniform2fv(location, value.getBuffer());
    }

    public void setUniform(int location, Vector3f value) {
        glUniform3fv(location, value.getBuffer());
    }

    public void setUniform(int location, Vector4f value) {
        glUniform4fv(location, value.getBuffer());
    }

    public void setUniform(int location, Matrix2f value) {
        glUniformMatrix2fv(location, false, value.getBuffer());
    }

    public void setUniform(int location, Matrix3f value) {
        glUniformMatrix3fv(location, false, value.getBuffer());
    }

    public void setUniform(int location, Matrix4f value) {
        glUniformMatrix4fv(location, false, value.getBuffer());
    }
    
    public void setUniform(int location, FloatBuffer value) {
        glUniformMatrix4fv(location, false, value);
    }

    public void enable() {
        glUseProgram(id);
    }
    
    public void disable() {
        glUseProgram(0);
    }

    public void checkStatus() {
        int status = glGetProgrami(id, GL_LINK_STATUS);
        if(status != GL_TRUE) {
            throw new RuntimeException(glGetProgramInfoLog(id));
        }
    }

    public void delete() {
        glDeleteProgram(id);
    }
    
    public int getId() {
    	return id;
    }
    
}
