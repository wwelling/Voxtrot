package voxel.game.core;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import voxel.game.math.Vector3i;

public class Game extends Engine {
	
	public Game(Vector3i worldDimensions, Vector3i sectionDimensions, Vector3i chunkDimensions, float voxelSize) {
		super(worldDimensions, sectionDimensions, chunkDimensions, voxelSize);
	}

	@Override
	public void loop() {
				
		while (glfwWindowShouldClose(window.getId()) == GL_FALSE) {
			
			timer.update();
			
			window.pollKeyboard(window.getId());

			glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
						
			window.render();
			
			window.drawWorldInfo();
			window.drawCameraInfo();
			window.drawTimerInfo();
			
			glfwSwapBuffers(window.getId());
			glfwPollEvents();
			
			if (!window.isVSyncEnabled()) {
				sync(TARGET_FPS);
			}
		}
		
	}
	
}
