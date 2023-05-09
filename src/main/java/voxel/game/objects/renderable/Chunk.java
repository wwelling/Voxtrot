package voxel.game.objects.renderable;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import java.util.List;

import voxel.game.graphics.Shader;
import voxel.game.graphics.ShaderProgram;
import voxel.game.graphics.Textures;
import voxel.game.graphics.VertexArrayObject;
import voxel.game.graphics.VertexBufferObject;
import voxel.game.math.Matrix4f;
import voxel.game.math.Vector3i;
import voxel.game.objects.Mesh;
import voxel.game.objects.TypeCount;

public class Chunk {
	
	private VertexArrayObject vertexArrayObject;
	
	private ShaderProgram shaderProgram;

	private int uniformModelviewProjection;
	
	private Vector3i location;
	
	private Vector3i sectionLocation;
	
	private List<TypeCount> voxelTypeCountList;
		
	public Chunk(Mesh mesh) {
		
		Shader vertexShader = Shader.loadShader(GL_VERTEX_SHADER, "src/main/resources/shaders/voxel.vs");
		Shader fragmentShader = Shader.loadShader(GL_FRAGMENT_SHADER, "src/main/resources/shaders/voxel.fs");

		shaderProgram = new ShaderProgram();
		shaderProgram.attachShader(vertexShader);
		shaderProgram.attachShader(fragmentShader);

		vertexShader.delete();
		fragmentShader.delete();

		shaderProgram.link();
		shaderProgram.enable();
		
		location = mesh.getLocation();
		
		sectionLocation = mesh.getSectionLocation();
		
		voxelTypeCountList = mesh.getVoxelTypeCountList();
				
		vertexArrayObject = new VertexArrayObject();
		
	    vertexArrayObject.bind();
	    
	    VertexBufferObject vertexBufferObject = new VertexBufferObject();
		vertexBufferObject.bind(GL_ARRAY_BUFFER);
		vertexBufferObject.uploadData(GL_ARRAY_BUFFER, mesh.getVerticesBuffer(), GL_STATIC_DRAW);
		
		VertexBufferObject indexBufferObject = new VertexBufferObject();
		indexBufferObject.bind(GL_ELEMENT_ARRAY_BUFFER);
		indexBufferObject.uploadData(GL_ELEMENT_ARRAY_BUFFER, mesh.getIndicesBuffer(), GL_STATIC_DRAW);
		
		int positionAttributeLocation = shaderProgram.getAttributeLocation("vertex_coordinates");
		shaderProgram.enableVertexAttribute(positionAttributeLocation);
		shaderProgram.pointVertexAttribute(positionAttributeLocation, 3, 20, 0);
			
		int textureAttributeLocation = shaderProgram.getAttributeLocation("texture_coordinates");
		shaderProgram.enableVertexAttribute(textureAttributeLocation);
		shaderProgram.pointVertexAttribute(textureAttributeLocation, 2, 20, 12);
				
		uniformModelviewProjection = shaderProgram.getUniformLocation("model_view_projection");

		vertexArrayObject.unbind();
	}
	
	public void render(Matrix4f view, Textures textures) {
		shaderProgram.enable();
		shaderProgram.setUniform(uniformModelviewProjection, view);
		vertexArrayObject.bind();
		int indicesOffset = 0;
		for(TypeCount vt : voxelTypeCountList) {			
			textures.get(vt.getType()).bind();
			glDrawElements(GL_TRIANGLES, vt.getCount(), GL_UNSIGNED_INT, indicesOffset);
			textures.get(vt.getType()).unbind();
			indicesOffset += vt.getCount();
		}
		vertexArrayObject.unbind();
		shaderProgram.disable();
	}
	
	public void dispose() {
		vertexArrayObject.delete();
		shaderProgram.delete();
	}
	
	public Vector3i getLocation() {
		return location;
	}

	public Vector3i getSectionLocation() {
		return sectionLocation;
	}
}
