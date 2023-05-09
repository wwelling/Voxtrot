package voxel.game.core;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Timer {

    private double lastLoopTime;

    private float timeCount;
    
    private int gameTime;

    private int fps;

    private int fpsCount;

    private int ups;

    private int upsCount;

    public void init() {
        lastLoopTime = getTime();
    }

    public double getTime() {
        return glfwGetTime();
    }

    public float getDelta() {
        double time = getTime();
        float delta = (float) (time - lastLoopTime);
        lastLoopTime = time;
        timeCount += delta;
        return delta;
    }

    public void updateFPS() {
        fpsCount++;
    }

    public void updateUPS() {
        upsCount++;
    }

    public void update() {
    	
    	getDelta();
    	
		updateUPS();
		updateFPS();
		
        if (timeCount > 1f) {
            fps = fpsCount;
            fpsCount = 0;

            ups = upsCount;
            upsCount = 0;

            timeCount -= 1f;
            gameTime++;
        }
    }

    public int getFPS() {
        return fps > 0 ? fps : fpsCount;
    }

    public int getUPS() {
        return ups > 0 ? ups : upsCount;
    }

    public double getLastLoopTime() {
        return lastLoopTime;
    }

	public int getGameTime() {
		return gameTime;
	}

	public void setGameTime(int gameTime) {
		this.gameTime = gameTime;
	}
    
}
