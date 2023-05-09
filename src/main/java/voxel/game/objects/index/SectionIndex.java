package voxel.game.objects.index;

import java.util.List;

public class SectionIndex {
	private List<ChunkIndex> ci;
	public SectionIndex() {}
	public SectionIndex(List<ChunkIndex> ci) {
		this.ci = ci;
	}
	public List<ChunkIndex> getCi() {
		return ci;
	}
	public void setCi(List<ChunkIndex> ci) {
		this.ci = ci;
	}
}
