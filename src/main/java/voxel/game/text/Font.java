package voxel.game.text;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTBakedChar;

import voxel.game.graphics.TextRenderer;
import voxel.game.graphics.Texture;
import voxel.game.io.Utility;
import voxel.game.math.Vector3i;
import voxel.game.math.Vector4f;

import static org.lwjgl.stb.STBTruetype.stbtt_BakeFontBitmap;
import static org.lwjgl.stb.STBTruetype.stbtt_GetBakedQuad;

import static org.lwjgl.opengl.GL11.*;

public class Font {

    private final Map<Character, Glyph> glyphs;

    private final Texture texture;

    private int fontHeight;
    
    private Vector3i color;
    
    public Font(String ttfPath, int fontHeight, Vector3i color) {
    	this.color = color;
        glyphs = new HashMap<>();
        texture = createFontTexture(ttfPath, fontHeight);
    }
        
    private Texture createFontTexture(String ttfPath, int fontHeight) {
		this.fontHeight = fontHeight;
		
		int BITMAP_W = 512;
		int BITMAP_H = 512;
				
		ByteBuffer cdata = BufferUtils.createByteBuffer(128 * STBTTBakedChar.SIZEOF);
		
		ByteBuffer bitmap = BufferUtils.createByteBuffer(BITMAP_W * BITMAP_H);
		
		try {
			ByteBuffer ttf = Utility.resourceToByteBuffer(ttfPath, 64 * 1024);
			stbtt_BakeFontBitmap(ttf, fontHeight, bitmap, BITMAP_W, BITMAP_H, 32, cdata);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		FloatBuffer xBuffer = BufferUtils.createFloatBuffer(1);
		FloatBuffer yBuffer = BufferUtils.createFloatBuffer(1);
		
		STBTTAlignedQuad q = new STBTTAlignedQuad();
		
		xBuffer.put(0, 0.0f);
		yBuffer.put(0, 0.0f);
		
		for(int i = 32; i < 256; i++) {            
            char c = (char) i;
            
            if(c == '\n') {
				yBuffer.put(0, yBuffer.get(0) + this.fontHeight);
				xBuffer.put(0, 0.0f);
				continue;
			} 
            else if(c < 32 || 128 <= c) {
				continue;
            }
            
            float x = xBuffer.get();
			float y = yBuffer.get();
			
			xBuffer.position(0);
			yBuffer.position(0);
             
			stbtt_GetBakedQuad(cdata, BITMAP_W, BITMAP_H, c - 32, xBuffer, yBuffer, q.buffer(), 1);
			
			float width = q.getX1() - q.getX0();
			float height = q.getY1() - q.getY0();

			if(height > fontHeight) {
				this.fontHeight = (int) height;
			}
			
			if(c == ' ') {
				width = 10f;
			}
			
            glyphs.put(c, new Glyph(width, height, x, y - q.getY1(), new Vector4f(q.getS0(), q.getT1(), q.getS1(), q.getT0())));
        	
		}
		
    	return new Texture(BITMAP_W, BITMAP_H, GL_RED, bitmap);
    }

    public void drawText(TextRenderer renderer, CharSequence text, float x, float y) {

    	float drawX = x;
        float drawY = y;
        
        texture.bind();
        
        renderer.begin();
        
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if(ch == '\n') {
                drawY -= fontHeight;
                drawX = x;
                continue;
            }
            if(ch == '\r') {
                continue;
            }
            
            Glyph g = glyphs.get(ch);            
            
            renderer.drawTextureRegion(texture, drawX, drawY + g.y, g.width, g.height, g.texCoor, color);
            
            drawX += g.width;
        }
        renderer.end();
    }

    public void dispose() {
        texture.delete();
    }
    
}
