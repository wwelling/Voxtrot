package voxel.game.objects;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import voxel.game.math.Vector3i;

public class Mesh {
	
	private Vector3i location;
	
	private Vector3i sectionLocation;

	private FloatBuffer verticesBuffer;
	private IntBuffer indicesBuffer;
	
	private List<TypeCount> voxelTypeCountList;
			
	public Mesh(Vector3i location, Vector3i sectionLocation, FloatBuffer verticesBuffer, IntBuffer indicesBuffer, List<TypeCount> voxelTypeCountList) {
		this.location = location;
		this.sectionLocation = sectionLocation;
		this.verticesBuffer = verticesBuffer;
		this.indicesBuffer = indicesBuffer;
		this.voxelTypeCountList = voxelTypeCountList;
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

	public FloatBuffer getVerticesBuffer() {
		return verticesBuffer;
	}

	public void setVerticesBuffer(FloatBuffer verticesBuffer) {
		this.verticesBuffer = verticesBuffer;
	}

	public IntBuffer getIndicesBuffer() {
		return indicesBuffer;
	}

	public void setIndicesBuffer(IntBuffer indicesBuffer) {
		this.indicesBuffer = indicesBuffer;
	}

	public List<TypeCount> getVoxelTypeCountList() {
		return voxelTypeCountList;
	}

	public void setVoxelTypeCountList(List<TypeCount> voxelTypeCountList) {
		this.voxelTypeCountList = voxelTypeCountList;
	}
	
}
