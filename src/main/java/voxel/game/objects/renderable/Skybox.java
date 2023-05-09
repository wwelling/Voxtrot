package voxel.game.objects.renderable;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import voxel.game.control.Camera;
import voxel.game.graphics.Shader;
import voxel.game.graphics.ShaderProgram;
import voxel.game.graphics.Texture;
import voxel.game.graphics.VertexArrayObject;
import voxel.game.graphics.VertexBufferObject;
import voxel.game.math.Matrix4f;

public class Skybox {
	
	private VertexArrayObject vertexArrayObject;
	
	private ShaderProgram shaderProgram;
	
	private Texture texture;
		
	public Skybox(Camera camera) {
		
		Shader vertexShader = Shader.loadShader(GL_VERTEX_SHADER, "src/main/resources/shaders/skybox.vs");
        Shader fragmentShader = Shader.loadShader(GL_FRAGMENT_SHADER, "src/main/resources/shaders/skybox.fs");
		
        shaderProgram = new ShaderProgram();
        shaderProgram.attachShader(vertexShader);
        shaderProgram.attachShader(fragmentShader);
        
        vertexShader.delete();
        fragmentShader.delete();
                
        shaderProgram.link();
        shaderProgram.enable();
        
		texture = new Texture(new String[] {
				"src/main/resources/textures/skybox/front.jpg",
				"src/main/resources/textures/skybox/back.jpg",
				"src/main/resources/textures/skybox/top.jpg",
				"src/main/resources/textures/skybox/bottom.jpg",
				"src/main/resources/textures/skybox/left.jpg",
				"src/main/resources/textures/skybox/right.jpg"
		});
		
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(108);
		
		float size = camera.getzFar() / 2;
				
		verticesBuffer.put(new float[] {
			
			-size,  size, -size,
	        -size, -size, -size,
	         size, -size, -size,
	         size, -size, -size,
	         size,  size, -size,
	        -size,  size, -size,
	  
	        -size, -size,  size,
	        -size, -size, -size,
	        -size,  size, -size,
	        -size,  size, -size,
	        -size,  size,  size,
	        -size, -size,  size,
	  
	         size, -size, -size,
	         size, -size,  size,
	         size,  size,  size,
	         size,  size,  size,
	         size,  size, -size,
	         size, -size, -size,
	   
	        -size, -size,  size,
	        -size,  size,  size,
	         size,  size,  size,
	         size,  size,  size,
	         size, -size,  size,
	        -size, -size,  size,
	  
	        -size,  size, -size,
	         size,  size, -size,
	         size,  size,  size,
	         size,  size,  size,
	        -size,  size,  size,
	        -size,  size, -size,
	  
	        -size, -size, -size,
	        -size, -size,  size,
	         size, -size, -size,
	         size, -size, -size,
	        -size, -size,  size,
	         size, -size,  size
		});
		
		verticesBuffer.flip();
		
		vertexArrayObject = new VertexArrayObject();
		
	    vertexArrayObject.bind();
	    
	    VertexBufferObject vertexBufferObject = new VertexBufferObject();
		vertexBufferObject.bind(GL_ARRAY_BUFFER);
		vertexBufferObject.uploadData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
		
		int positionAttributeLocation = shaderProgram.getAttributeLocation("vertex");
		shaderProgram.enableVertexAttribute(positionAttributeLocation);
		shaderProgram.pointVertexAttribute(positionAttributeLocation, 3, 0, 0);
		
		shaderProgram.setUniform(shaderProgram.getUniformLocation("projection"), camera.getProjection());
		
		vertexArrayObject.unbind();
	}
	
	public void render(Matrix4f view) {
						
		shaderProgram.enable();
		
		view.m30 = 0.0f;
		view.m31 = 0.0f;
		view.m32 = 0.0f;
		
		shaderProgram.setUniform(shaderProgram.getUniformLocation("view"), view);
		
		vertexArrayObject.bind();
		
		texture.bind(GL_TEXTURE_CUBE_MAP);
				
		glDrawArrays(GL_TRIANGLES, 0, 36);
		
		texture.unbind(GL_TEXTURE_CUBE_MAP);
		
		vertexArrayObject.unbind();
		
		shaderProgram.disable();
	}
	
	public void dispose() {
		vertexArrayObject.delete();
		shaderProgram.delete();
	}
		
}
