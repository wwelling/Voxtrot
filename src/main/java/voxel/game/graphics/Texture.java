package voxel.game.graphics;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import voxel.game.io.Utility;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;

import static org.lwjgl.stb.STBImage.stbi_info_from_memory;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;

public class Texture {

    private final int id;

    private int width;

    private int height;
    
    /**
     * Constructs texture for skybox.  
     */
    public Texture(String[] paths) {
    	id = glGenTextures();
    	
    	glBindTexture(GL_TEXTURE_CUBE_MAP, id);
    	
    	for(int i = 0; i < paths.length; i++) {
    		ByteBufferPlus byteBufferPlus =  imageByteBuffer(paths[i]);
    		 
    		width = byteBufferPlus.getWidth();
    		height = byteBufferPlus.getHeight();

    		glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, byteBufferPlus.getByteBuffer());
    	}
    	
    	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
    	
    	glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
    }

    /**
     * Constructs texture for voxel. 
     */
    public Texture(String path) {    	
        id = glGenTextures();

        ByteBufferPlus byteBufferPlus =  imageByteBuffer(path);
        
        width = byteBufferPlus.getWidth();
        height = byteBufferPlus.getHeight();
        
        glBindTexture(GL_TEXTURE_2D, id);
        
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, byteBufferPlus.getByteBuffer());

        glGenerateMipmap(GL_TEXTURE_2D);
    	
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);        
        
        glBindTexture(GL_TEXTURE_2D, 0);
    }
    
    /**
     * Constructs texture for font. 
     */
    public Texture(int width, int height, int format, ByteBuffer data) {
        id = glGenTextures();
        
        this.width = width;
        this.height = height;

        glBindTexture(GL_TEXTURE_2D, id);

        glTexImage2D(GL_TEXTURE_2D, 0, format, width, height, 0, format, GL_UNSIGNED_BYTE, data);
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        
        glBindTexture(GL_TEXTURE_2D, 0);
        
    }
    
    public ByteBufferPlus imageByteBuffer(String path) {
    	
    	ByteBuffer imageBuffer;
    	try {
			imageBuffer = Utility.resourceToByteBuffer(path, 64 * 1024);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to read resource to byte buffer!!");
		}
    	
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer componentsBuffer = BufferUtils.createIntBuffer(1);

		if(stbi_info_from_memory(imageBuffer, widthBuffer, heightBuffer, componentsBuffer) == 0) {
			throw new RuntimeException("Failed to read image information: " + stbi_failure_reason());
		}
		
		int width = widthBuffer.get();
		int height = heightBuffer.get();
		
		widthBuffer.position(0);
		heightBuffer.position(0);
		    	
    	return new ByteBufferPlus(stbi_load_from_memory(imageBuffer, widthBuffer, heightBuffer, componentsBuffer, 0), width, height, componentsBuffer);
    }
    
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }
    
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
    
    public void bind(int type) {
        glBindTexture(type, id);
    }
    
    public void unbind(int type) {
        glBindTexture(type, 0);
    }
    
    public void delete() {
        glDeleteTextures(id);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public class ByteBufferPlus {
    	ByteBuffer byteBuffer;
    	int width;
    	int height;
    	
    	IntBuffer componentBuffer;
    	
    	public ByteBufferPlus(ByteBuffer byteBuffer, int width, int height, IntBuffer componentBuffer) {
    		this.byteBuffer = byteBuffer;
    		this.width = width;
    		this.height = height;
    		this.componentBuffer = componentBuffer;
    	}

		public ByteBuffer getByteBuffer() {
			return byteBuffer;
		}

		public void setByteBuffer(ByteBuffer byteBuffer) {
			this.byteBuffer = byteBuffer;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public IntBuffer getComponentBuffer() {
			return componentBuffer;
		}

		public void setComponentBuffer(IntBuffer componentBuffer) {
			this.componentBuffer = componentBuffer;
		}
		
    }
    
}
