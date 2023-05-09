package voxel.game.graphics;

import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

public class VertexArrayObject {

    private final int id;

    public VertexArrayObject() {
        id = glGenVertexArrays();
    }

    public void bind() {
        glBindVertexArray(id);
    }
    
    public void unbind() {
    	glBindVertexArray(0);
    }
    
    public void delete() {
        glDeleteVertexArrays(id);
    }

    public int getId() {
        return id;
    }
}
