package voxel.game.control;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_DEPTH_BITS;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_C;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWWindowPosCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLContext;

import voxel.game.core.Timer;
import voxel.game.graphics.TextRenderer;
import voxel.game.math.Vector3f;
import voxel.game.math.Vector3i;
import voxel.game.objects.World;
import voxel.game.text.Font;

public class Window {
	
	private float mouseSensitivity = 0.05f;
	
	private boolean control = false;	
	private boolean fullScreen = false;	
	private boolean mouseGrabbed = true;
		
	private TextRenderer textRenderer;	
	private Font font;
	
	private long id;
	
	private int width = 800;
	private int height = 600;
	
	private int prevWidth;
	private int prevHeight;
	
	private int fullWidth;
	private int fullHeight;
	
	private int xPos;
	private int yPos;
	
	private double prevXPos;
	private double prevYPos;
	
    private boolean vsync;
    
    private World world;
    private Camera camera;
    private Timer timer;
    
    public Window(Vector3i worldDimensions, Vector3i sectionDimensions, Vector3i chunkDimensions, float voxelSize, Timer timer) {    	
    	camera = new Camera(new Vector3f(65.0f, 75.0f, -205.0f), 45.0f, width / height, 0.025f, 5000.0f);
    	world = new World(camera, worldDimensions, sectionDimensions, chunkDimensions, voxelSize);
    	this.timer = timer;
    }
    
    private void subinit() {
		font = new Font("src/main/resources/fonts/couri.ttf", 18, new Vector3i(255, 255, 255));
		textRenderer.init();		
		world.init();
	}
    
	public void init() {
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_DEPTH_BITS, 32);
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		
		if(System.getProperty("os.name").toLowerCase().startsWith("mac")) {
			glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        }
		
		id = glfwCreateWindow(width, height, "Voxel Game Engine", NULL, NULL);

		if(id == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}

		ByteBuffer videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		fullWidth = GLFWvidmode.width(videoMode);
		fullHeight = GLFWvidmode.height(videoMode);
		
		xPos = (fullWidth - width) / 2;
		yPos = (fullHeight - height) / 2;
		
		glfwSetWindowPos(id, xPos, yPos);
		
		DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
		
		glfwGetCursorPos(id, x, y);
		
		prevXPos = x.get();
		prevYPos = y.get();
		
		setupWindow(id);
		
		textRenderer = new TextRenderer();
		
		subinit();
	}
	
	public void render() {
		world.render();
	}
	
	public void dispose() {
		keyCallback.release();
		moveCallback.release();
		resizeCallback.release();
		scrollCallback.release();
		cursorCallback.release();

		textRenderer.dispose();
	}
	
	private void setupWindow(long windowId) {
		if(!fullScreen) {
			glfwSetWindowPos(windowId, xPos, yPos);
		}
		
		glfwMakeContextCurrent(windowId);

		glfwShowWindow(windowId);
		
		glfwSetKeyCallback(windowId, keyCallback);		
		glfwSetWindowPosCallback(windowId, moveCallback);		
		glfwSetFramebufferSizeCallback(windowId, resizeCallback);
		glfwSetScrollCallback(windowId, scrollCallback);
		glfwSetCursorPosCallback(windowId, cursorCallback);
		
		if(mouseGrabbed) {
			glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
		}
		else {
			glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
		}
		
		GLContext.createFromCurrent();
		
		setVSync(true);
		
		
		glEnable(GL_TEXTURE_2D);		
		
		glCullFace(GL_BACK);
		
		
		glEnable(GL_DEPTH_TEST);				
		glDepthFunc(GL_LEQUAL);

		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		
		glEnable(GL_BLEND);
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glViewport(0, 0, width, height);
	}
		
	public void toggleFullScreen() {
		
		fullScreen = !fullScreen;
		
		glfwDestroyWindow(id);
		
		long newWindow = NULL;
		
		if(fullScreen) {
			prevWidth = width;
			prevHeight = height;
			width = fullWidth;
			height = fullHeight;
			newWindow = glfwCreateWindow(width, height, "Voxel Game Engine", glfwGetPrimaryMonitor(), NULL);
		}
		else {
			width = prevWidth;
			height = prevHeight;
			newWindow = glfwCreateWindow(width, height, "Voxel Game Engine", NULL, NULL);			
		}
		
		if (newWindow == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
		
		id = newWindow;
		
		setupWindow(newWindow);
		
		subinit();	
	}
	
	public void drawText(String text, float x, float y) {
		font.drawText(textRenderer, text, x, y);
	}
		
    public boolean isDefaultContext() {
        return GL.getCapabilities().OpenGL32;
    }
    
    public boolean isClosing() {
        return glfwWindowShouldClose(id) == GL_TRUE;
    }

    public void setTitle(CharSequence title) {
        glfwSetWindowTitle(id, title);
    }

    public void update() {
        glfwSwapBuffers(id);
        glfwPollEvents();
    }

    public void destroy() {
    	dispose();
        glfwDestroyWindow(id);
    }

    public void setVSync(boolean vsync) {
        this.vsync = vsync;
        if (vsync) {
            glfwSwapInterval(1);
        } else {
            glfwSwapInterval(0);
        }
    }

    public boolean isVSyncEnabled() {
        return this.vsync;
    }
    
    public void drawWorldInfo() {
    	drawText("Welcome to Voxtrot!", 5.0f, 5.0f);
    }
    
    public void drawTimerInfo() {    	
    	drawText("FPS: " + timer.getFPS() + " UPS: " + timer.getUPS(), 5.0f, height - 100.0f);
    	drawText("Game time: " + timer.getGameTime(), 5.0f, height - 120.0f);
    }
    
    public void drawCameraInfo() {
		drawText("Camera position: " + camera.getPosition().x + ", " + camera.getPosition().y + ", " + camera.getPosition().z, 5.0f, height - 20.0f);
		drawText("Camera direction: " + camera.getDirection().x + ", " + camera.getDirection().y + ", " + camera.getDirection().z, 5.0f, height - 40.0f);
		drawText("Camera right: " + camera.getRight().x + ", " + camera.getRight().y + ", " + camera.getRight().z, 5.0f, height - 60.0f);
		drawText("Camera up: " + camera.getUp().x + ", " + camera.getUp().y + ", " + camera.getUp().z, 5.0f, height - 80.0f);
    }

	
	private GLFWWindowPosCallback moveCallback = new GLFWWindowPosCallback() {
		@Override
		public void invoke(long windowId, int x, int y) {
			xPos = x;
			yPos = y;
		}		
	};
	
	private GLFWFramebufferSizeCallback resizeCallback = new GLFWFramebufferSizeCallback() {
		@Override
		public void invoke(long windowId, int w, int h) {
			width = w;
			height = h;
			glViewport(0, 0, width, height);
			textRenderer.init();
			world.init();
		}		
	};
	
	private GLFWCursorPosCallback cursorCallback = new GLFWCursorPosCallback() {
		@Override
		public void invoke(long windowId, double x, double y) {
			if(isMouseGrabbed()) {
				if(x != prevXPos) {
					camera.yaw((float) -(x - prevXPos)* mouseSensitivity);
					prevXPos = x;
				}
				if(y != prevYPos) {
					camera.pitch((float) (y - prevYPos)* mouseSensitivity);
					prevYPos = y;
				}
			}
			else {
				prevXPos = x;
				prevYPos = y;
			}
		}		
	};
	
	private GLFWScrollCallback scrollCallback = new GLFWScrollCallback() {
		@Override
		public void invoke(long windowId, double arg1, double arg2) {
	
		}				
	};
	
	private GLFWKeyCallback keyCallback = new GLFWKeyCallback() {		
		@Override
		public void invoke(long windowId, int key, int scanCode, int action, int mods) {
			if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
				glfwSetWindowShouldClose(windowId, GL_TRUE);
				world.dispose();
			}
			if(key == GLFW_KEY_LEFT_CONTROL && action == GLFW_PRESS) {
				control = true;
			}
			if(key == GLFW_KEY_LEFT_CONTROL && action == GLFW_RELEASE) {
				control = false;
			}
			if(control && (key == GLFW_KEY_Z && action == GLFW_RELEASE)) {
				if(mouseGrabbed) {
					glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
					mouseGrabbed = false;
				}
				else {
					glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
					mouseGrabbed = true;
				}
				control = false;
			}
			if(control && (key == GLFW_KEY_X && action == GLFW_RELEASE)) {
				toggleFullScreen();
				control = false;
			}
			if(control && (key == GLFW_KEY_C && action == GLFW_RELEASE)) {
				world.toggleWireFrame();
				control = false;
			}
		}		
	};
	
	public void pollKeyboard(long windowId) {
		
		if(glfwGetKey(windowId, GLFW_KEY_W) == 1) {
			camera.goForward();
		}
		if(glfwGetKey(windowId, GLFW_KEY_S) == 1) {
			camera.goBackward();
		}
		if(glfwGetKey(windowId, GLFW_KEY_A) == 1) {
			camera.strafeLeft();
		}
		if(glfwGetKey(windowId, GLFW_KEY_D) == 1) {
			camera.strafeRight();
		}
		if(glfwGetKey(windowId, GLFW_KEY_R) == 1) {
			camera.goUp();
		}
		if(glfwGetKey(windowId, GLFW_KEY_F) == 1) {
			camera.goDown();
		}
		
        if(glfwGetKey(windowId, GLFW_KEY_UP) == 1) {
        	camera.pitch(-1);
		}
		if(glfwGetKey(windowId, GLFW_KEY_DOWN) == 1) {
			camera.pitch(1);
		}
        if(glfwGetKey(windowId, GLFW_KEY_LEFT) == 1) {
        	camera.yaw(1);
		}
		if(glfwGetKey(windowId, GLFW_KEY_RIGHT) == 1) {
			camera.yaw(-1);
		}
		if(glfwGetKey(windowId, GLFW_KEY_PAGE_UP) == 1) {
			camera.roll(1);
		}
		if(glfwGetKey(windowId, GLFW_KEY_PAGE_DOWN) == 1) {
			camera.roll(-1);
		}
	}

	public long getId() {
		return id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isMouseGrabbed() {
		return mouseGrabbed;
	}
	
}
