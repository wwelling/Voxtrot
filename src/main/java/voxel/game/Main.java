package voxel.game;

import java.io.IOException;

import voxel.game.core.Game;
import voxel.game.io.Generator;
import voxel.game.math.Vector3i;

public class Main {
	
	public static boolean generateWorld = true;
	
	public static void main(String[] args) {

		if (args.length != 10) {
		  System.exit(-1);
		}
		
		float voxelSize = Float.parseFloat(String.format("%sf", args[0]));

		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}

		int wx = Integer.parseInt(args[0]);
		int wy = Integer.parseInt(args[1]);
		int wz = Integer.parseInt(args[2]);

		int sx = Integer.parseInt(args[3]);
		int sy = Integer.parseInt(args[4]);
		int sz = Integer.parseInt(args[5]);

		int cx = Integer.parseInt(args[6]);
		int cy = Integer.parseInt(args[7]);
		int cz = Integer.parseInt(args[8]);
		
		Vector3i worldDimensions   = new Vector3i(wx, wy, wz);
		Vector3i sectionDimensions = new Vector3i(sx, sy, sz);
		Vector3i chunkDimensions   = new Vector3i(cz, cy, cz);

		if (generateWorld) {
			try {
				new Generator(worldDimensions, sectionDimensions, chunkDimensions, voxelSize).generateWorld();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		new Game(worldDimensions, sectionDimensions, chunkDimensions, voxelSize).start();
	}

}
