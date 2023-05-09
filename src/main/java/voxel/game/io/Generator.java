package voxel.game.io;

import static org.lwjgl.stb.STBPerlin.stb_perlin_noise3;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import voxel.game.math.Vector3i;
import voxel.game.objects.Greedy;
import voxel.game.objects.index.ChunkIndex;
import voxel.game.objects.index.SectionIndex;
import voxel.game.objects.index.SectionLocation;

public class Generator {
	
	private Vector3i worldDimensions;	
	private Vector3i sectionDimensions;	
	private Vector3i chunkDimensions;
	
	protected float voxelSize;
	
	public Generator(Vector3i worldDimensions, Vector3i sectionDimensions, Vector3i chunkDimensions, float voxelSize) {				
		this.worldDimensions   = worldDimensions;
		this.sectionDimensions = sectionDimensions;
		this.chunkDimensions   = chunkDimensions;
		
		this.voxelSize = voxelSize;
	}
	
	public void generateWorld() throws IOException {
		
		File maps = new File("src/main/resources/maps");
		
		deleteDirectory(maps);
		
	    try{
	    	maps.mkdir();
	    } 
	    catch(SecurityException se){
	        System.out.println("Could not create " + maps.getName());
	    }
	    
	    File sections = new File("src/main/resources/maps/sections");
		
		deleteDirectory(sections);
		
	    try{
	    	sections.mkdir();
	    } 
	    catch(SecurityException se){
	        System.out.println("Could not create " + sections.getName());
	    }
	    
		System.out.println("Generating World!");
		
		long startWorldGeneration = System.nanoTime();
		
		float coordinateScalar = 0.025f;
		
		int elevationDelta = 10;
		int minShellThickness = 1;
		
		int worldWidth  = chunkDimensions.x*sectionDimensions.x*worldDimensions.x;
		int worldHeight = chunkDimensions.y*sectionDimensions.y*worldDimensions.y;
		int worldLength = chunkDimensions.z*sectionDimensions.z*worldDimensions.z;
		
		Vector3i center = new Vector3i(worldWidth/2, worldHeight/2, worldLength/2);
		
		float outerCircumference = (Math.min(Math.min(worldWidth, worldHeight), Math.min(worldHeight, worldLength)) / 2) - elevationDelta; 
		float innerCircumference = outerCircumference - minShellThickness;
		
		List<SectionLocation> sectionLocations = new ArrayList<SectionLocation>();
		
		int worldVoxelCount = 0;
				
		Vector3i scd = new Vector3i(sectionDimensions.x*chunkDimensions.x, sectionDimensions.y*chunkDimensions.y, sectionDimensions.z*chunkDimensions.z);
		
		Vector3i worldSize = new Vector3i(worldDimensions.x*sectionDimensions.x*chunkDimensions.x, worldDimensions.y*sectionDimensions.y*chunkDimensions.y, worldDimensions.z*sectionDimensions.z*chunkDimensions.z);
		
		for(int wX = 0; wX < worldDimensions.x; wX++) {
			int sectionX = wX*scd.x;
			
			for(int wY = 0; wY < worldDimensions.y; wY++) {
				int sectionY = wY*scd.y;
				
				for(int wZ = 0; wZ < worldDimensions.z; wZ++) {
					int sectionZ = wZ*scd.z;
					
					int sectionVoxelCount = 0;					
					
					SectionIndex sectionIndex = new SectionIndex(new ArrayList<ChunkIndex>());
					
					for(int sX = 0; sX < sectionDimensions.x; sX++) {						
						int chunkX = sectionX + sX*chunkDimensions.x;
						
						for(int sY = 0; sY < sectionDimensions.y; sY++) {							
							int chunkY = sectionY + sY*chunkDimensions.y;
							
							for(int sZ = 0; sZ < sectionDimensions.z; sZ++) {
								int chunkZ = sectionZ + sZ*chunkDimensions.z;
								
								int voxelCount = 0;
								
								sectionIndex.getCi().add(new ChunkIndex(new HashMap<Integer, Byte>()));
																								
								for(int cX = 0; cX < chunkDimensions.x; cX++) {									
									int voxelX = chunkX + cX;
									
									for(int cY = 0; cY < chunkDimensions.y; cY++) {										
										int voxelY = chunkY + cY;
										
										for(int cZ = 0; cZ < chunkDimensions.z; cZ++) {											
											int voxelZ = chunkZ + cZ;
											
											int distanceFromCenter = new Vector3i(voxelX, voxelY, voxelZ).distance(center);
																						
											float noise = (stb_perlin_noise3(voxelX*coordinateScalar, voxelY*coordinateScalar, voxelZ*coordinateScalar, 0, 0, 0) + 1) * elevationDelta;

											if(distanceFromCenter > innerCircumference && distanceFromCenter <= outerCircumference + noise) {
												sectionIndex.getCi().get(sX*sectionDimensions.y*sectionDimensions.z + sY*sectionDimensions.z + sZ).getVm().put(voxelX*worldSize.y*worldSize.z + voxelY*worldSize.z + voxelZ, new Byte("0"));
												voxelCount++;
											}
																						
										}
									}
								}
																
								sectionIndex.getCi().get(sX*sectionDimensions.y*sectionDimensions.z + sY*sectionDimensions.z + sZ).setChunk(new Greedy(worldSize, chunkDimensions, voxelCount, new Vector3i(chunkX, chunkY, chunkZ), new Vector3i(sectionX, sectionY, sectionZ)));
								sectionVoxelCount += voxelCount;
							}
						}
					}
					
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					
					Utility.objectMapper.writeValue(byteArrayOutputStream, sectionIndex);
					
					Utility.writeFile("src/main/resources/maps/sections/section-"+sectionX+"-"+sectionY+"-"+sectionZ+".map", byteArrayOutputStream.toByteArray());
					
					sectionLocations.add(new SectionLocation(new Vector3i(sectionX, sectionY, sectionZ), sectionVoxelCount > 0));
					
					worldVoxelCount += sectionVoxelCount;
					
					System.out.println("Section: " + sectionVoxelCount);
					
				}
			}
		}
		
		Utility.writeFile("src/main/resources/maps/section.index", Utility.objectMapper.writeValueAsBytes(sectionLocations));
						
		System.out.println("World Generated! " + worldVoxelCount + " voxels!");
		
		System.out.println("Time to generate world: " + (System.nanoTime() - startWorldGeneration) / 1000000000.0 + " seconds");
		
	}
	
	public boolean deleteDirectory(File path) {
		if(path.exists()) {
			File[] files = path.listFiles();
			for(int i = 0; i < files.length; i++) {
				if(files[i].isDirectory()) {
					deleteDirectory(files[i]);
				}
				else {
					files[i].delete();
				}
			}
		}
		return path.delete();
	}

}