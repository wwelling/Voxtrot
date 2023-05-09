package voxel.game;

import java.io.IOException;

import voxel.game.core.Game;
import voxel.game.io.Generator;
import voxel.game.math.Vector3i;

public class Main {
	
	public static boolean generateWorld = true;
	
	public static void main(String[] args) {
		
		float voxelSize = 1.0f;
		
		Vector3i worldDimensions   = new Vector3i(2, 2, 2);
		Vector3i sectionDimensions = new Vector3i(2, 2, 2);
		Vector3i chunkDimensions   = new Vector3i(16, 16, 16);

		if(generateWorld) {
			try {
				new Generator(worldDimensions, sectionDimensions, chunkDimensions, voxelSize).generateWorld();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		new Game(worldDimensions, sectionDimensions, chunkDimensions, voxelSize).start();
	}

}
