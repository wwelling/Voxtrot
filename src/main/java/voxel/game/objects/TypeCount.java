package voxel.game.objects;

public class TypeCount {

	private Byte type;
	
	private int count;
	
	public TypeCount(Byte type, int count) {
		this.type = type;
		this.count = count;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}