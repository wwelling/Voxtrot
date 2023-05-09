package voxel.game.graphics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Textures {
	
	private Map<Byte, Texture> textures;

	public Textures() {
		textures = new HashMap<Byte, Texture>();
		textures.put(new Byte("0"), new Texture("src/main/resources/textures/x.png"));
		textures.put(new Byte("1"), new Texture("src/main/resources/textures/red.png"));
		textures.put(new Byte("2"), new Texture("src/main/resources/textures/green.png"));
		textures.put(new Byte("3"), new Texture("src/main/resources/textures/blue.png"));
		textures.put(new Byte("4"), new Texture("src/main/resources/textures/white.png"));
		textures.put(new Byte("5"), new Texture("src/main/resources/textures/black.png"));
    }
	
	public Texture get(Byte textureType) {
		return textures.get(textureType);
	}
	
	public void delete() {
		Iterator<Map.Entry<Byte, Texture>> iterator = textures.entrySet().iterator() ;
        while(iterator.hasNext()){
        	Map.Entry<Byte, Texture> texture = iterator.next();
        	texture.getValue().delete();
            iterator.remove();
        }
	}
	
}
