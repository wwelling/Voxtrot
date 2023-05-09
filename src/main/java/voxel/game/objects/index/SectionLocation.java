package voxel.game.objects.index;

import voxel.game.math.Vector3i;

public class SectionLocation {
	
	private Vector3i location;
	
	private boolean active;
	
	private boolean viewable;
	
	public SectionLocation() {}
	
	public SectionLocation(Vector3i location, boolean active) {
		this.location = location;
		this.active = active;
		viewable = false;
	}
	
	public Vector3i getLocation() {
		return location;
	}
	
	public void setLocation(Vector3i location) {
		this.location = location;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isViewable() {
		return viewable;
	}
	
	public void setViewable(boolean viewable) {
		this.viewable = viewable;
	}
	
}
