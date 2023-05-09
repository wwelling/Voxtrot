package voxel.game.objects.index;

import java.util.Map;

import voxel.game.objects.Greedy;

public class ChunkIndex {
	private Greedy chunk;
	private Map<Integer, Byte> vm;
	public ChunkIndex() {}
	public ChunkIndex(Map<Integer, Byte> vm) {
		this.vm = vm;
	}
	public Greedy getChunk() {
		return chunk;
	}
	public void setChunk(Greedy chunk) {
		this.chunk = chunk;
	}
	public Map<Integer, Byte> getVm() {
		return vm;
	}
	public void setVm(Map<Integer, Byte> vm) {
		this.vm = vm;
	}
}
