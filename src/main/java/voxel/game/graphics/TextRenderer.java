package voxel.game.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import voxel.game.math.Matrix4f;
import voxel.game.math.Vector3i;
import voxel.game.math.Vector4f;

import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

public class TextRenderer {
    
    private VertexArrayObject vertexArrayObject;
    private VertexBufferObject vertexBufferObject;
        
    private ShaderProgram shaderProgram;
    
    private FloatBuffer vertices;
    
    private int numVertices;
    
    private boolean drawing;

    public void init() {
        long windowID = glfwGetCurrentContext();
        
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        
        glfwGetWindowSize(windowID, w, h);
        
        int width = w.get(0);
        int height = h.get(0);

        vertexArrayObject = new VertexArrayObject();
        vertexArrayObject.bind();

        vertexBufferObject = new VertexBufferObject();
        vertexBufferObject.bind(GL_ARRAY_BUFFER);

        vertices = BufferUtils.createFloatBuffer(4096);

        long size = vertices.capacity() * 4;
        vertexBufferObject.uploadData(GL_ARRAY_BUFFER, size, GL_DYNAMIC_DRAW);

        numVertices = 0;
        drawing = false;

        Shader vertexShader = Shader.loadShader(GL_VERTEX_SHADER, "src/main/resources/shaders/text.vs");
        Shader fragmentShader = Shader.loadShader(GL_FRAGMENT_SHADER, "src/main/resources/shaders/text.fs");

        shaderProgram = new ShaderProgram();
        shaderProgram.attachShader(vertexShader);
        shaderProgram.attachShader(fragmentShader);
        
        vertexShader.delete();
        fragmentShader.delete();
                
        shaderProgram.link();
        shaderProgram.enable();
        
        specifyVertexAttributes();

        int uniTex = shaderProgram.getUniformLocation("textureImage");
        shaderProgram.setUniform(uniTex, 0);

        Matrix4f model = new Matrix4f(true);
        int uniModel = shaderProgram.getUniformLocation("model");
        shaderProgram.setUniform(uniModel, model);

        Matrix4f view = new Matrix4f(true);
        int uniView = shaderProgram.getUniformLocation("view");
        shaderProgram.setUniform(uniView, view);

        Matrix4f projection = new Matrix4f();
        projection.orthographic(0f, width, 0f, height, -1f, 1f);
        
        int uniProjection = shaderProgram.getUniformLocation("projection");
        shaderProgram.setUniform(uniProjection, projection);
    }
    
    protected void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
        
    public void begin() {
        if(drawing) {
            throw new IllegalStateException("Renderer is already drawing!");
        }
        drawing = true;
        numVertices = 0;
    }

    public void end() {
        if(!drawing) {
            throw new IllegalStateException("Renderer isn't drawing!");
        }
        drawing = false;
        flush();
    }

    public void flush() {
        if(numVertices > 0) {
            vertices.flip();

            if(vertexArrayObject != null) {
                vertexArrayObject.bind();
            } else {
                vertexBufferObject.bind(GL_ARRAY_BUFFER);
                specifyVertexAttributes();
            }
            shaderProgram.enable();
            
            vertexBufferObject.bind(GL_ARRAY_BUFFER);
            vertexBufferObject.uploadSubData(GL_ARRAY_BUFFER, 0, vertices);

            glDrawArrays(GL_TRIANGLES, 0, numVertices);

            vertices.clear();
            numVertices = 0;
        }
    }  
    
    public void dispose() {
        if(vertexArrayObject != null) {
            vertexArrayObject.delete();
        }
        vertexBufferObject.delete();        
        shaderProgram.delete();
    }

    public void drawTexture(Texture texture, float x, float y) {
        drawTexture(texture, x, y, new Vector3i(255,255,255));
    }

    public void drawTexture(Texture texture, float x, float y, Vector3i color) {
        float x1 = x;
        float y1 = y;
        float x2 = x1 + texture.getWidth();
        float y2 = y1 + texture.getHeight();

        float s1 = 0f;
        float t1 = 0f;
        float s2 = 1f;
        float t2 = 1f;

        drawTextureRegion(x1, y1, x2, y2, s1, t1, s2, t2, color);
    }
    
    public void drawTextureRegion(Texture texture, float x, float y, float regWidth, float regHeight, Vector4f texCoor, Vector3i color) {
        float x1 = x;
        float y1 = y;
        float x2 = x + regWidth;
        float y2 = y + regHeight;

        float s1 = texCoor.x;
        float t1 = texCoor.y;
        float s2 = texCoor.z;
        float t2 = texCoor.w;

        drawTextureRegion(x1, y1, x2, y2, s1, t1, s2, t2, color);
    }

    public void drawTextureRegion(float x1, float y1, float x2, float y2, float s1, float t1, float s2, float t2, Vector3i color) {
    	if (vertices.remaining() < 7 * 6) {
            flush();
        }

        float r = color.x / 255f;
        float g = color.y / 255f;
        float b = color.z / 255f;
                
        vertices.put(x1).put(y1).put(r).put(g).put(b).put(s1).put(t1);
        vertices.put(x1).put(y2).put(r).put(g).put(b).put(s1).put(t2);
        vertices.put(x2).put(y2).put(r).put(g).put(b).put(s2).put(t2);

        vertices.put(x1).put(y1).put(r).put(g).put(b).put(s1).put(t1);
        vertices.put(x2).put(y2).put(r).put(g).put(b).put(s2).put(t2);
        vertices.put(x2).put(y1).put(r).put(g).put(b).put(s2).put(t1);

        numVertices += 6;
    }
    
    public void specifyVertexAttributes() {
        int posAttrib = shaderProgram.getAttributeLocation("vertex_position");
        shaderProgram.enableVertexAttribute(posAttrib);
        shaderProgram.pointVertexAttribute(posAttrib, 2, 7 * 4, 0);
        
        int colAttrib = shaderProgram.getAttributeLocation("vertex_color");
        shaderProgram.enableVertexAttribute(colAttrib);
        shaderProgram.pointVertexAttribute(colAttrib, 3, 7 * 4, 2 * 4);

        int texAttrib = shaderProgram.getAttributeLocation("texture_coordinates");
        shaderProgram.enableVertexAttribute(texAttrib);
        shaderProgram.pointVertexAttribute(texAttrib, 2, 7 * 4, 5 * 4);
    }

}
