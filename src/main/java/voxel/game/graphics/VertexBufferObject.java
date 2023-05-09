package voxel.game.graphics;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.opengl.GL15.*;

public class VertexBufferObject {

    private final int id;

    public VertexBufferObject() {
        id = glGenBuffers();
    }

    public void bind(int target) {
        glBindBuffer(target, id);
    }
    
    public void unbind(int target) {
        glBindBuffer(target, 0);
    }
    
    public void uploadData(int target, long size, int usage) {
        glBufferData(target, size, usage);
    }
    
    public void uploadData(int target, IntBuffer data, int usage) {
        glBufferData(target, data, usage);
    }
    
    public void uploadData(int target, ShortBuffer data, int usage) {
        glBufferData(target, data, usage);
    }
    
    public void uploadData(int target, FloatBuffer data, int usage) {
        glBufferData(target, data, usage);
    }
    
    public void uploadData(int target, DoubleBuffer data, int usage) {
        glBufferData(target, data, usage);
    }
        
    public void uploadSubData(int target, long offset, FloatBuffer data) {
        glBufferSubData(target, offset, data);
    }
    
    public void uploadSubData(int target, long offset, IntBuffer data) {
        glBufferSubData(target, offset, data);
    }

    public void delete() {
        glDeleteBuffers(id);
    }

    public int getId() {
        return id;
    }
    
}
