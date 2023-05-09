package voxel.game.core;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_TRUE;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.glfw.GLFWErrorCallback;

import voxel.game.control.Window;
import voxel.game.math.Vector3i;

public abstract class Engine {
	
	public static final int TARGET_FPS = 75;
	public static final int TARGET_UPS = 60;

	protected Window window;

	protected Timer timer;

	public Engine(Vector3i worldDimensions, Vector3i sectionDimensions, Vector3i chunkDimensions, float voxelSize) {
		timer = new Timer();
		window = new Window(worldDimensions, sectionDimensions, chunkDimensions, voxelSize, timer);
	}

	public abstract void loop();

	public void start() {
		try {
			init();			
			loop();

			window.dispose();

		} finally {
			glfwTerminate();
			errorCallback.release();
		}
	}

	public void init() {
		glfwSetErrorCallback(errorCallback);

		if (glfwInit() != GL_TRUE) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		window.init();
		
		timer.init();			
	}

    public void sync(int fps) {
    	
        double lastLoopTime = timer.getLastLoopTime();
        
        double now = timer.getTime();
        
        float targetTime = 1f / fps;

        while (now - lastLoopTime < targetTime) {
            Thread.yield();

            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }

            now = timer.getTime();
        }
    }
    
    private GLFWErrorCallback errorCallback = new GLFWErrorCallback() {
		@Override
		public void invoke(int arg0, long arg1) {
			errorCallback = errorCallbackPrint(System.err);
		}
	};
	
}
