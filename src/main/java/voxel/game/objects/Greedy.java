package voxel.game.objects;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

import org.lwjgl.BufferUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import voxel.game.math.Vector3i;

public class Greedy implements Supplier<Mesh> {
    
	private Vector3i worldSize;
	
	private Vector3i dimensions;

	private Vector3i location;
	
	private Vector3i sectionLocation;
	
	private int voxelCount;
			
	public Greedy() { }

	public Greedy(Vector3i worldSize, Vector3i dimensions, int voxelCount, Vector3i location, Vector3i sectionLocation) {
		this.worldSize = worldSize;
		this.dimensions = dimensions;
		this.voxelCount = voxelCount;
		this.location = location;
		this.sectionLocation = sectionLocation;
	}
	
	@JsonIgnore
	public Mesh get() {
				
		List<Face> faces = new ArrayList<Face>();
		
		int i, j, k, l, w, h, u, v, n, side = 0;
        
        final int[] x = new int[] {0,0,0};
        final int[] q = new int[] {0,0,0};
        final int[] du = new int[] {0,0,0};
        final int[] dv = new int[] {0,0,0};
        
        final Byte[] mask = new Byte[dimensions.x * dimensions.y];
		        
        Byte voxel, voxel1;
        
        for (boolean backFace = true, b = false; b != backFace; backFace = backFace && b, b = !b) { 
        	
        	for(int d = 0; d < 3; d++) {
        		u = (d + 1) % 3; 
                v = (d + 2) % 3;

                x[0] = 0;
                x[1] = 0;
                x[2] = 0;

                q[0] = 0;
                q[1] = 0;
                q[2] = 0;
                q[d] = 1;
                
                if (d == 0)      { side = backFace ? 3   : 2;  }
                else if (d == 1) { side = backFace ? 5 : 4;   }
                else if (d == 2) { side = backFace ? 0  : 1; } 
                
                for(x[d] = -1; x[d] < dimensions.x;) {
                	
                	n = 0;
                	
                	for(x[v] = 0; x[v] < dimensions.y; x[v]++) {

                        for(x[u] = 0; x[u] < dimensions.x; x[u]++) {

                        	int[] c1 = new int[] {Math.round((location.x/*/World.voxelSize*/ + x[0])), Math.round((location.y/*/World.voxelSize*/ + x[1])), Math.round((location.z/*/World.voxelSize*/ + x[2]))};
                        	int[] c2 = new int[] {Math.round((location.x/*/World.voxelSize*/ + x[0] + q[0])), Math.round((location.y/*/World.voxelSize*/ + x[1] + q[1])), Math.round((location.z/*/World.voxelSize*/ + x[2] + q[2]))};

                        	voxel = World.activeMap.get(c1[0]*worldSize.y*worldSize.z + c1[1]*worldSize.z + c1[2]);
                    		voxel  = (x[d] >= 0) ? (voxel != null) ? voxel : null : null;
                    		
                    		voxel1 = World.activeMap.get(c2[0]*worldSize.y*worldSize.z + c2[1]*worldSize.z + c2[2]);
                    		voxel1 = (x[d] < dimensions.x - 1) ? (voxel1 != null) ? voxel1 : null : null;
                    	
                        	mask[n++] = ((voxel != null && voxel1 != null && voxel.equals(voxel1))) ? null : backFace ? voxel1 : voxel;
                        }
                	}
                	                	
                	x[d]++;
                	
                	n = 0;
                	
                	for(j = 0; j < dimensions.y; j++) {

                        for(i = 0; i < dimensions.x;) {
                        	
                        	 if(mask[n] != null) {

                        		 for(w = 1; i + w < dimensions.x && mask[n + w] != null && mask[n + w].equals(mask[n]); w++) {}

                        		 boolean done = false;

                        		 for(h = 1; j + h < dimensions.y; h++) {

                                     for(k = 0; k < w; k++) {

                                         if(mask[n + k + h * dimensions.x] == null || !mask[n + k + h * dimensions.x].equals(mask[n])) { 
                                        	 done = true; 
                                        	 break; 
                                         }
                                     }

                                     if(done) { break; }
                                 }
                        		                			 
                    			 x[u] = i;  
                                 x[v] = j;

                                 du[0] = 0;
                                 du[1] = 0;
                                 du[2] = 0;
                                 du[u] = w;

                                 dv[0] = 0;
                                 dv[1] = 0;
                                 dv[2] = 0;
                                 dv[v] = h;
                                 
                                 Byte type = mask[n];
                                                                      
                                 switch(side) {
                                 	case 0: {
                                  		faces.add(new Face(new float[] {
                                  				((((float) x[0])/* * World.voxelSize*/) + location.x),
                                  				((((float) x[1])/* * World.voxelSize*/) + location.y),
                                  				((((float) x[2])/* * World.voxelSize*/) + location.z),
                                  				0.0f, 0.0f,
	                                  			((((float) x[0] + du[0])/* * World.voxelSize*/) + location.x),
	                                  			((((float) x[1] + du[1])/* * World.voxelSize*/) + location.y),
	                                  			((((float) x[2] + du[2])/* * World.voxelSize*/) + location.z),
	                                  			(((((float) x[0] + du[0] + dv[0])/* * World.voxelSize*/) + location.x) - ((((float) x[0])/* * World.voxelSize*/) + location.x))/*/World.voxelSize*/, 0.0f,
	                                  			((((float) x[0] + du[0] + dv[0])/* * World.voxelSize*/) + location.x),
	                                  			((((float) x[1] + du[1] + dv[1])/* * World.voxelSize*/) + location.y),
	                                  			((((float) x[2] + du[2] + dv[2])/* * World.voxelSize*/) + location.z),
	                                  			(((((float) x[0] + du[0] + dv[0])/* * World.voxelSize*/) + location.x) - ((((float) x[0])/* * World.voxelSize*/) + location.x))/*/World.voxelSize*/, (((((float) x[1] + du[1] + dv[1])/* * World.voxelSize*/) + location.y) - ((((float) x[1])/* * World.voxelSize*/) + location.y))/*/World.voxelSize*/,
	                                  			((((float) x[0] + dv[0])/* * World.voxelSize*/) + location.x),
	                                  			((((float) x[1] + dv[1])/* * World.voxelSize*/) + location.y),
	                                  			((((float) x[2] + dv[2])/* * World.voxelSize*/) + location.z),
	                                  			0.0f, (((((float) x[1] + du[1] + dv[1])/* * World.voxelSize*/) + location.y) - ((((float) x[1])/* * World.voxelSize*/) + location.y))/*/World.voxelSize*/
                                  		}, type, side));
                                  	} break;
                                  	case 1: {
                                  		faces.add(new Face(new float[] {
                              				((((float) x[0])/* * World.voxelSize*/) + location.x),
                              				((((float) x[1])/* * World.voxelSize*/) + location.y),
                              				((((float) x[2])/* * World.voxelSize*/) + location.z),
                              				0.0f, 0.0f,
                              				((((float) x[0] + du[0])/* * World.voxelSize*/) + location.x),
                              				((((float) x[1] + du[1])/* * World.voxelSize*/) + location.y),
                              				((((float) x[2] + du[2])/* * World.voxelSize*/) + location.z),
                              				(((((float) x[0] + du[0] + dv[0])/* * World.voxelSize*/) + location.x) - ((((float) x[0])/* * World.voxelSize*/) + location.x))/*/World.voxelSize*/, 0.0f,
                              				((((float) x[0] + du[0] + dv[0])/* * World.voxelSize*/) + location.x),
                              				((((float) x[1] + du[1] + dv[1])/* * World.voxelSize*/) + location.y),
                              				((((float) x[2] + du[2] + dv[2])/* * World.voxelSize*/) + location.z),
                              				(((((float) x[0] + du[0] + dv[0])/* * World.voxelSize*/) + location.x) - ((((float) x[0])/* * World.voxelSize*/) + location.x))/*/World.voxelSize*/, (((((float) x[1] + dv[1])/* * World.voxelSize*/) + location.y) - ((((float) x[1] + du[1])/* * World.voxelSize*/) + location.y))/*/World.voxelSize*/,
                              				((((float) x[0] + dv[0])/* * World.voxelSize*/) + location.x),
                              				((((float) x[1] + dv[1])/* * World.voxelSize*/) + location.y),
                              				((((float) x[2] + dv[2])/* * World.voxelSize*/) + location.z),
                              				0.0f, (((((float) x[1] + dv[1])/* * World.voxelSize*/) + location.y) - ((((float) x[1] + du[1])/* * World.voxelSize*/) + location.y))/*/World.voxelSize*/
                                  		}, type, side));
                                  	} break;
                                  	case 2: {
                                  		faces.add(new Face(new float[] {
                              				((((float) x[0])/* * World.voxelSize*/) + location.x),
                              				((((float) x[1])/* * World.voxelSize*/) + location.y),
                              				((((float) x[2])/* * World.voxelSize*/) + location.z),
                              				0.0f, 0.0f,
                                  			((((float) x[0] + du[0])/* * World.voxelSize*/) + location.x),
                                  			((((float) x[1] + du[1])/* * World.voxelSize*/) + location.y),
                                  			((((float) x[2] + du[2])/* * World.voxelSize*/) + location.z),
                                  			(((((float) x[1] + du[1] + dv[1])/* * World.voxelSize*/) + location.y) - ((((float) x[1])/* * World.voxelSize*/) + location.y))/*/World.voxelSize*/, 0.0f,
                                  			((((float) x[0] + du[0] + dv[0])/* * World.voxelSize*/) + location.x),
                                  			((((float) x[1] + du[1] + dv[1])/* * World.voxelSize*/) + location.y),
                                  			((((float) x[2] + du[2] + dv[2])/* * World.voxelSize*/) + location.z),
                                  			(((((float) x[1] + du[1] + dv[1])/* * World.voxelSize*/) + location.y) - ((((float) x[1])/* * World.voxelSize*/) + location.y))/*/World.voxelSize*/, (((((float) x[2] + dv[2])/* * World.voxelSize*/) + location.z) - ((((float) x[2] + du[2])/* * World.voxelSize*/) + location.z))/*/World.voxelSize*/,
                                  			((((float) x[0] + dv[0])/* * World.voxelSize*/) + location.x),
                                  			((((float) x[1] + dv[1])/* * World.voxelSize*/) + location.y),
                                  			((((float) x[2] + dv[2])/* * World.voxelSize*/) + location.z),
                                  			0.0f, (((((float) x[2] + dv[2])/* * World.voxelSize*/) + location.z) - ((((float) x[2] + du[2])/* * World.voxelSize*/) + location.z))/*/World.voxelSize*/
                                  		}, type, side));
                                  	} break;
                                  	case 3: {
                                  		faces.add(new Face(new float[] {
                              				((((float) x[0])/* * World.voxelSize*/) + location.x),
                              				((((float) x[1])/* * World.voxelSize*/) + location.y),
                              				((((float) x[2])/* * World.voxelSize*/) + location.z),
                              				0.0f, 0.0f,
                              				((((float) x[0] + du[0])/* * World.voxelSize*/) + location.x),
                              				((((float) x[1] + du[1])/* * World.voxelSize*/) + location.y),
                              				((((float) x[2] + du[2])/* * World.voxelSize*/) + location.z),
                              				(((((float) x[1] + du[1])/* * World.voxelSize*/) + location.y) - ((((float) x[1])/* * World.voxelSize*/) + location.y))/*/World.voxelSize*/, 0.0f,
                              				((((float) x[0] + du[0] + dv[0])/* * World.voxelSize*/) + location.x),
                              				((((float) x[1] + du[1] + dv[1])/* * World.voxelSize*/) + location.y),
                              				((((float) x[2] + du[2] + dv[2])/* * World.voxelSize*/) + location.z),
                              				(((((float) x[1] + du[1])/* * World.voxelSize*/) + location.y) - ((((float) x[1])/* * World.voxelSize*/) + location.y))/*/World.voxelSize*/, (((((float) x[2] + dv[2])/* * World.voxelSize*/) + location.z) - ((((float) x[2] + du[2])/* * World.voxelSize*/) + location.z))/*/World.voxelSize*/,
                              				((((float) x[0] + dv[0])/* * World.voxelSize*/) + location.x),
                              				((((float) x[1] + dv[1])/* * World.voxelSize*/) + location.y),
                              				((((float) x[2] + dv[2])/* * World.voxelSize*/) + location.z),
                              				0.0f, (((((float) x[2] + dv[2])/* * World.voxelSize*/) + location.z) - ((((float) x[2] + du[2])/* * World.voxelSize*/) + location.z))/*/World.voxelSize*/
                                  		}, type, side));
                                  	} break; 
                                  	case 4: {
                                  		faces.add(new Face(new float[] {
                              				((((float) x[0])/* * World.voxelSize*/) + location.x),
                              				((((float) x[1])/* * World.voxelSize*/) + location.y),
                              				((((float) x[2])/* * World.voxelSize*/) + location.z),
                              				0.0f, 0.0f,
                                  			((((float) x[0] + du[0])/* * World.voxelSize*/) + location.x),
                                  			((((float) x[1] + du[1])/* * World.voxelSize*/) + location.y),
                                  			((((float) x[2] + du[2])/* * World.voxelSize*/) + location.z),
                                  			(((((float) x[2] + du[2])/* * World.voxelSize*/) + location.z) - ((((float) x[2] + dv[2])/* * World.voxelSize*/) + location.z))/*/World.voxelSize*/, 0.0f,
                                  			((((float) x[0] + du[0] + dv[0])/* * World.voxelSize*/) + location.x),
                                  			((((float) x[1] + du[1] + dv[1])/* * World.voxelSize*/) + location.y),
                                  			((((float) x[2] + du[2] + dv[2])/* * World.voxelSize*/) + location.z),
                                  			(((((float) x[2] + du[2])/* * World.voxelSize*/) + location.z) - ((((float) x[2] + dv[2])/* * World.voxelSize*/) + location.z))/*/World.voxelSize*/, (((((float) x[0] + du[0] + dv[0])/* * World.voxelSize*/) + location.x) - ((((float) x[0])/* * World.voxelSize*/) + location.x))/*/World.voxelSize*/, 
                                  			((((float) x[0] + dv[0])/* * World.voxelSize*/) + location.x),
                                  			((((float) x[1] + dv[1])/* * World.voxelSize*/) + location.y),
                                  			((((float) x[2] + dv[2])/* * World.voxelSize*/) + location.z),
                                  			0.0f, (((((float) x[0] + du[0] + dv[0])/* * World.voxelSize*/) + location.x) - ((((float) x[0])/* * World.voxelSize*/) + location.x))/*/World.voxelSize*/
                                  		}, type, side));
                                  	} break;
                                  	case 5: {	                                  		
                                  		faces.add(new Face(new float[] {
                              				((((float) x[0])/* * World.voxelSize*/) + location.x),
                              				((((float) x[1])/* * World.voxelSize*/) + location.y),
                              				((((float) x[2])/* * World.voxelSize*/) + location.z),
                              				0.0f, 0.0f,
                              				((((float) x[0] + du[0])/* * World.voxelSize*/) + location.x),
                              				((((float) x[1] + du[1])/* * World.voxelSize*/) + location.y),
                              				((((float) x[2] + du[2])/* * World.voxelSize*/) + location.z),
                              				(((((float) x[2] + du[2])/* * World.voxelSize*/) + location.z) - ((((float) x[2] + dv[2])/* * World.voxelSize*/) + location.z))/*/World.voxelSize*/, 0.0f,
                              				((((float) x[0] + du[0] + dv[0])/* * World.voxelSize*/) + location.x),
                              				((((float) x[1] + du[1] + dv[1])/* * World.voxelSize*/) + location.y),
                              				((((float) x[2] + du[2] + dv[2])/* * World.voxelSize*/) + location.z),
                              				(((((float) x[2] + du[2])/* * World.voxelSize*/) + location.z) - ((((float) x[2] + dv[2])/* * World.voxelSize*/) + location.z))/*/World.voxelSize*/, (((((float) x[0] + du[0] + dv[0])/* * World.voxelSize*/) + location.x) - ((((float) x[0])/* * World.voxelSize*/) + location.x))/*/World.voxelSize*/, 
                              				((((float) x[0] + dv[0])/* * World.voxelSize*/) + location.x),
                              				((((float) x[1] + dv[1])/* * World.voxelSize*/) + location.y),
                              				((((float) x[2] + dv[2])/* * World.voxelSize*/) + location.z),
                              				0.0f, (((((float) x[0] + du[0] + dv[0])/* * World.voxelSize*/) + location.x) - ((((float) x[0])/* * World.voxelSize*/) + location.x))/*/World.voxelSize*/
                                  		}, type, side));
                                  	} break;
                                 }
                        		 
                        		 for(l = 0; l < h; ++l) {
                                     for(k = 0; k < w; ++k) { 
                                    	 mask[n + k + l * dimensions.x] = null; 
                                	 }
                                 }
                        		 
                        		 i += w; 
                                 n += w;
                        		 
                        	 }
                        	 else {
                        		 i++;
                        		 n++;
                        	 }
                        	
                        }
                        
                	}

                }
                
        	}
        	
        }
        
        if(faces.size() == 0) return null;
        
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(faces.size()*20);
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(faces.size()*6);
                
        Collections.sort(faces, new FaceComparator());
        
        List<TypeCount> voxelTypeCountList = new ArrayList<TypeCount>();
        
        Byte texture = faces.get(0).getType();
		
        int count = 0;
        
        int vertextCount = 0;
        
        for(Face face : faces) {
        	if(texture != face.getType()) {
        		voxelTypeCountList.add(new TypeCount(texture, count));
				texture = face.getType();
				count = 24;
			}
			else {
				count += 24;
			}
        	        	
        	verticesBuffer.put(face.getVertices());
        	
        	if(face.getSide() == 0 || face.getSide() == 3 || face.getSide() == 5) {
        		indicesBuffer.put(new int[] {
          			vertextCount+3, vertextCount+2, vertextCount,
          			vertextCount,   vertextCount+2, vertextCount+1 
          		});
        	}
        	else {
        		indicesBuffer.put(new int[] {
          			vertextCount, vertextCount+1, vertextCount+2,
          			vertextCount, vertextCount+2, vertextCount+3 
          		});
        	}
        	vertextCount += 4;
        }
        
        voxelTypeCountList.add(new TypeCount(texture, count));
		
        verticesBuffer.flip();
		indicesBuffer.flip();
        
        return new Mesh(location, sectionLocation, verticesBuffer, indicesBuffer, voxelTypeCountList);        
	}
	
	public Vector3i getWorldSize() {
		return worldSize;
	}

	public void setWorldSize(Vector3i worldSize) {
		this.worldSize = worldSize;
	}

	public Vector3i getDimensions() {
		return dimensions;
	}

	public void setDimensions(Vector3i dimensions) {
		this.dimensions = dimensions;
	}

	public int getVoxelCount() {
		return voxelCount;
	}

	public void setVoxelCount(int voxelCount) {
		this.voxelCount = voxelCount;
	}
	
	public Vector3i getLocation() {
		return location;
	}

	public void setLocation(Vector3i location) {
		this.location = location;
	}
	
	public Vector3i getSectionLocation() {
		return sectionLocation;
	}

	public void setSectionLocation(Vector3i sectionLocation) {
		this.sectionLocation = sectionLocation;
	}

	
	public class Face {
		
		private float[] vertices;
		private Byte type;
		private Integer side;
		
		public Face(float[] vertices, Byte type, Integer side) {
			this.vertices = vertices;
			this.type = type;
			this.side = side;
		}
		
		public float[] getVertices() {
			return vertices;
		}

		public void setVertices(float[] vertices) {
			this.vertices = vertices;
		}
		
		public Byte getType() {
			return type;
		}

		public void setType(Byte type) {
			this.type = type;
		}

		public Integer getSide() {
			return side;
		}

		public void setSide(Integer side) {
			this.side = side;
		}
			
	}
	
	public class FaceComparator implements Comparator<Face> {
		@Override
		public int compare(Face face1, Face face2) {
			return face1.getType().compareTo(face2.getType());
		}
	}
	
}
