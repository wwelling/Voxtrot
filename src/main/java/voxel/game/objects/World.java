package voxel.game.objects;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPolygonMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.core.type.TypeReference;

import voxel.game.control.Camera;
import voxel.game.graphics.Textures;
import voxel.game.io.SectionIndexSupplier;
import voxel.game.io.Utility;
import voxel.game.math.Vector3i;
import voxel.game.objects.index.SectionLocation;
import voxel.game.objects.renderable.Chunk;
import voxel.game.objects.renderable.Skybox;

public class World {

	public static java.util.Map<Integer, Byte> activeMap;

	public static float voxelSize;

	public static Vector3i size;

	public boolean wireFrame = false;

	private Camera camera;

	private Skybox skybox;

	private Textures textures;

	public Vector3i worldDimensions;

	public Vector3i sectionDimensions;

	public Vector3i chunkDimensions;

	private List<SectionLocation> sectionLocations;

	private List<Chunk> chunks;

	private List<Mesh> meshList;

	private ExecutorService executorService;

	private float sight;

	public World(Camera camera, Vector3i worldDimensions, Vector3i sectionDimensions, Vector3i chunkDimensions, float voxelSize) {
		this.camera = camera;
		this.worldDimensions = worldDimensions;
		this.sectionDimensions = sectionDimensions;
		this.chunkDimensions = chunkDimensions;

		World.voxelSize = voxelSize;

		World.size = new Vector3i(worldDimensions.x * sectionDimensions.x * chunkDimensions.x, worldDimensions.y * sectionDimensions.y * chunkDimensions.y, worldDimensions.z * sectionDimensions.z * chunkDimensions.z);

		activeMap = new ConcurrentHashMap<Integer, Byte>();

		chunks = new ArrayList<Chunk>();

		meshList = new ArrayList<Mesh>();

		executorService = Executors.newFixedThreadPool(5);

		try {
			sectionLocations = Utility.objectMapper.readValue(Utility.readFile("src/main/resources/maps/section.index"), new TypeReference<List<SectionLocation>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		sight = camera.getzFar() / 4;
	}

	public void init() {

		textures = new Textures();
		
		skybox = new Skybox(camera);

		CompletableFuture.supplyAsync(() -> {

			sectionLocations.parallelStream().filter(sl -> sl.isActive() && sl.getLocation().distance(camera.getPosition()) <= sight).forEach(sectionLocation -> {

				Vector3i location = sectionLocation.getLocation();

				String path = "src/main/resources/maps/sections/section-" + location.x + "-" + location.y + "-" + location.z + ".map";

				CompletableFuture.supplyAsync(new SectionIndexSupplier(path), executorService).whenComplete((sectionIndex, e1) -> {

					sectionIndex.getCi().parallelStream().filter(ci -> ci.getChunk().getVoxelCount() > 0).forEach(ci -> {

						CompletableFuture.supplyAsync(() -> {
							activeMap.putAll(ci.getVm());
							return true;
						}).whenComplete((success, e) -> {
							CompletableFuture.supplyAsync(ci.getChunk(), executorService).whenComplete((mesh, e2) -> {
								meshList.add(mesh);
								chunks.add(new Chunk(mesh));
							});
						});

					});

				});

				sectionLocation.setViewable(true);

			});
			return true;
		});

	}

	boolean buffering = false;

	public void update() {
		for (int i = meshList.size() - 1; i >= 0 ; i--) {
			Mesh mesh;
			if ((mesh = meshList.get(i)) != null) {
				chunks.add(new Chunk(mesh));
				meshList.remove(i);
			}
		}
	}

	public void render() {

		update();

		glEnable(GL_CULL_FACE);

		if (wireFrame) {
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		}

		for (int i = 0; i < chunks.size(); i++) {
			Chunk chunk = chunks.get(i);
			if (camera.isChunkInFrustum(chunk.getLocation(), chunkDimensions)) {
				chunk.render(camera.getModelViewProjection(), textures);
			}
		}

		if (wireFrame) {
			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		}

		skybox.render(camera.getView());

		glDisable(GL_CULL_FACE);
	}

	public void dispose() {
		chunks.stream().forEach(mesh -> {
			mesh.dispose();
		});
		skybox.dispose();
		textures.delete();
		executorService.shutdown();
	}

	public void toggleWireFrame() {
		wireFrame = !wireFrame;
	}

}
