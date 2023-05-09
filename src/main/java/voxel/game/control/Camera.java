package voxel.game.control;

import voxel.game.math.Matrix4f;
import voxel.game.math.Vector3f;
import voxel.game.math.Vector3i;
import voxel.game.math.Vector4f;
import voxel.game.objects.World;

public class Camera {
			
	private float speedIncrements = 0.25f;
	
	private float movementSpeed = 8 * speedIncrements;
	private float rotationSpeed = 2.5f;
		
	private float[][] frustum;
	
	private Matrix4f modelViewProjection;
		
	private Matrix4f projection;
	
	private Matrix4f view;
    
    private Matrix4f rotation;
	
	private Vector3f position;
	
	private Vector3f direction;
	private Vector3f right;
	private Vector3f up;
	
	private float fov;
	private float aspect;
	private float zNear;
	private float zFar;
					
	private Matrix4f tempMatrix4f;
	private Vector4f tempVector4f;
	private Vector3f tempVector3f;
		
	public Camera(Vector3f position, float fov, float aspect, float zNear, float zFar) {
		this.position = position;
		this.fov = fov;
		this.aspect = aspect;
		this.zNear = zNear;
		this.zFar = zFar;
		
		frustum = new float[6][4];
		
		direction = new Vector3f(0.0f, 0.0f, 1.0f);
		right = new Vector3f(-1.0f, 0.0f, 0.0f);
		up = new Vector3f(0.0f, 1.0f, 0.0f);
		
		projection = new Matrix4f();
		
		view = new Matrix4f();
		
		rotation = new Matrix4f(1.0f);
				
		tempMatrix4f = new Matrix4f();		
		tempVector4f = new Vector4f();
		tempVector3f = new Vector3f();
		
		projection.perspective(fov, aspect, zNear, zFar);
		view.lookAt(position, direction, up, right);
		modelViewProjection = projection.multiply(view, tempMatrix4f);
		calculateFrustrum();
	}

	public Matrix4f updateProjection(float fov, float aspect, float zNear, float zFar) {		
		if(this.fov != fov || this.aspect != aspect || this.zNear != zNear || this.zFar != zFar) {
			this.fov = fov;
			this.aspect = aspect;
			this.zNear = zNear;
			this.zFar = zFar;
			
			projection.perspective(fov, aspect, zNear, zFar);
			modelViewProjection = projection.multiply(view, tempMatrix4f);
			calculateFrustrum();
		}
		return projection;
	}
	
	public void updateProjection() {
		view.lookAt(position, direction, up, right);
		modelViewProjection = projection.multiply(view, tempMatrix4f);
		calculateFrustrum();
	}
	
	public void yaw(float radians) {
		radians *= rotationSpeed;
		rotation.rotate(radians, up);
		direction.set(rotation.multiply(direction, tempVector4f));
		right = direction.cross(up);
		updateProjection();
	}
	
	public void pitch(float radians) {
		radians *= rotationSpeed;
		rotation.rotate(-radians, right);
		direction.set(rotation.multiply(direction, tempVector4f));
		up = right.cross(direction);
		updateProjection();
	}
	
	public void roll(float radians) {
		radians *= rotationSpeed;
		rotation.rotate(-radians, direction);
		up.set(rotation.multiply(up, tempVector4f));
		right = direction.cross(up);
		updateProjection();
	}
	
	public boolean collision(int x, int y, int z) {
		if(x < 0 || y < 0 || z < 0 ) {
			return false;
		}
		if(World.activeMap.get(x*World.size.y*World.size.z + y*World.size.z + z) != null) {
			return true;
		}
		return false;
	}
	
	public void goForward() {
		boolean update = false;
		float incrementalMovementSpeed = 0.0f;
		while(incrementalMovementSpeed < movementSpeed) {
			Vector3f temp = position.add(direction.scale(speedIncrements), tempVector3f);		
			if(collision((int) Math.floor(temp.x/*/World.voxelSize*/), (int) Math.floor(temp.y/*/World.voxelSize*/), (int) Math.floor(temp.z/*/World.voxelSize*/))) {
				position.add(direction.scale(-speedIncrements));
				break;
			}
			position.add(direction.scale(speedIncrements));
			incrementalMovementSpeed += speedIncrements;
			update = true;
		}
		if(update) {
			updateProjection();
		}
	}
	
	public void goBackward() {
		boolean update = false;
		float incrementalMovementSpeed = 0.0f;
		while(incrementalMovementSpeed < movementSpeed) {
			Vector3f temp = position.subtract(direction.scale(speedIncrements), tempVector3f);
			if(collision((int) Math.floor(temp.x/*/World.voxelSize*/), (int) Math.floor(temp.y/*/World.voxelSize*/), (int) Math.floor(temp.z/*/World.voxelSize*/))) {
				position.subtract(direction.scale(-speedIncrements));
				break;
			}
			position.subtract(direction.scale(speedIncrements));
			incrementalMovementSpeed += speedIncrements;
			update = true;
		}
		if(update) {
			updateProjection();
		}
	}
	
	public void strafeLeft() {
		boolean update = false;
		float incrementalMovementSpeed = 0.0f;
		while(incrementalMovementSpeed < movementSpeed) {
			Vector3f temp = position.subtract(right.scale(speedIncrements), tempVector3f);
			if(collision((int) Math.floor(temp.x/*/World.voxelSize*/), (int) Math.floor(temp.y/*/World.voxelSize*/), (int) Math.floor(temp.z/*/World.voxelSize*/))) {
				position.subtract(right.scale(-speedIncrements));
				break;
			}
			position.subtract(right.scale(speedIncrements));
			incrementalMovementSpeed += speedIncrements;
			update = true;
		}
		if(update) {
			updateProjection();
		}
	}
	
	public void strafeRight() {
		boolean update = false;
		float incrementalMovementSpeed = 0.0f;
		while(incrementalMovementSpeed < movementSpeed) {
			Vector3f temp = position.add(right.scale(speedIncrements), tempVector3f);
			if(collision((int) Math.floor(temp.x/*/World.voxelSize*/), (int) Math.floor(temp.y/*/World.voxelSize*/), (int) Math.floor(temp.z/*/World.voxelSize*/))) {
				position.add(right.scale(-speedIncrements));
				break;
			}
			position.add(right.scale(speedIncrements));
			incrementalMovementSpeed += speedIncrements;
			update = true;
		}
		if(update) {
			updateProjection();
		}
	}
	
	public void goUp() {
		boolean update = false;
		float incrementalMovementSpeed = 0.0f;
		while(incrementalMovementSpeed < movementSpeed) {
			Vector3f temp = position.add(up.scale(speedIncrements), tempVector3f);
			if(collision((int) Math.floor(temp.x/*/World.voxelSize*/), (int) Math.floor(temp.y/*/World.voxelSize*/), (int) Math.floor(temp.z/*/World.voxelSize*/))) {
				position.add(up.scale(-speedIncrements));
				break;
			}
			position.add(up.scale(speedIncrements));
			incrementalMovementSpeed += speedIncrements;
			update = true;
		}
		if(update) {
			updateProjection();
		}
	}
	
	public void goDown() {
		boolean update = false;
		float incrementalMovementSpeed = 0.0f;
		while(incrementalMovementSpeed < movementSpeed) {
			Vector3f temp = position.subtract(up.scale(speedIncrements), tempVector3f);
			if(collision((int) Math.floor(temp.x/*/World.voxelSize*/), (int) Math.floor(temp.y/*/World.voxelSize*/), (int) Math.floor(temp.z/*/World.voxelSize*/))) {
				position.subtract(up.scale(-speedIncrements));
				break;
			}
			position.subtract(up.scale(speedIncrements));
			incrementalMovementSpeed += speedIncrements;
			update = true;
		}
		if(update) {
			updateProjection();
		}
	}
	
	public void calculateFrustrum() {
        float temp;

        // Extract the RIGHT clipping plane
        frustum[0][0] = modelViewProjection.m03 - modelViewProjection.m00;
        frustum[0][1] = modelViewProjection.m13 - modelViewProjection.m10;
        frustum[0][2] = modelViewProjection.m23 - modelViewProjection.m20;
        frustum[0][3] = modelViewProjection.m33 - modelViewProjection.m30;

        // Normalize it
        temp = (float) Math.sqrt(frustum[0][0] * frustum[0][0] + frustum[0][1] * frustum[0][1] + frustum[0][2] * frustum[0][2]);
        frustum[0][0] /= temp;
        frustum[0][1] /= temp;
        frustum[0][2] /= temp;
        frustum[0][3] /= temp;


        // Extract the LEFT clipping plane
        frustum[1][0] = modelViewProjection.m03 + modelViewProjection.m00;
        frustum[1][1] = modelViewProjection.m13 + modelViewProjection.m10;
        frustum[1][2] = modelViewProjection.m23 + modelViewProjection.m20;
        frustum[1][3] = modelViewProjection.m33 + modelViewProjection.m30;

        // Normalize it
        temp = (float) Math.sqrt(frustum[1][0] * frustum[1][0] + frustum[1][1] * frustum[1][1] + frustum[1][2] * frustum[1][2]);
        frustum[1][0] /= temp;
        frustum[1][1] /= temp;
        frustum[1][2] /= temp;
        frustum[1][3] /= temp;


        // Extract the BOTTOM clipping plane
        frustum[2][0] = modelViewProjection.m03 + modelViewProjection.m01;
        frustum[2][1] = modelViewProjection.m13 + modelViewProjection.m11;
        frustum[2][2] = modelViewProjection.m23 + modelViewProjection.m21;
        frustum[2][3] = modelViewProjection.m33 + modelViewProjection.m31;
        
        // Normalize it
        temp = (float) Math.sqrt(frustum[2][0] * frustum[2][0] + frustum[2][1] * frustum[2][1] + frustum[2][2] * frustum[2][2]);
        frustum[2][0] /= temp;
        frustum[2][1] /= temp;
        frustum[2][2] /= temp;
        frustum[2][3] /= temp;


        // Extract the TOP clipping plane
        frustum[3][0] = modelViewProjection.m03 - modelViewProjection.m01;
        frustum[3][1] = modelViewProjection.m13 - modelViewProjection.m11;
        frustum[3][2] = modelViewProjection.m23 - modelViewProjection.m21;
        frustum[3][3] = modelViewProjection.m33 - modelViewProjection.m31;

        // Normalize it
        temp = (float) Math.sqrt(frustum[3][0] * frustum[3][0] + frustum[3][1] * frustum[3][1] + frustum[3][2] * frustum[3][2]);
        frustum[3][0] /= temp;
        frustum[3][1] /= temp;
        frustum[3][2] /= temp;
        frustum[3][3] /= temp;


        // Extract the FAR clipping plane
        frustum[4][0] = modelViewProjection.m03 - modelViewProjection.m02;
        frustum[4][1] = modelViewProjection.m13 - modelViewProjection.m12;
        frustum[4][2] = modelViewProjection.m23 - modelViewProjection.m22;
        frustum[4][3] = modelViewProjection.m33 - modelViewProjection.m32;

        // Normalize it
        temp = (float) Math.sqrt(frustum[4][0] * frustum[4][0] + frustum[4][1] * frustum[4][1] + frustum[4][2] * frustum[4][2]);
        frustum[4][0] /= temp;
        frustum[4][1] /= temp;
        frustum[4][2] /= temp;
        frustum[4][3] /= temp;


        // Extract the NEAR clipping plane.
        frustum[5][0] = modelViewProjection.m03 + modelViewProjection.m02;
        frustum[5][1] = modelViewProjection.m13 + modelViewProjection.m12;
        frustum[5][2] = modelViewProjection.m23 + modelViewProjection.m22;
        frustum[5][3] = modelViewProjection.m33 + modelViewProjection.m32;

        // Normalize it
        temp = (float) Math.sqrt(frustum[5][0] * frustum[5][0] + frustum[5][1] * frustum[5][1] + frustum[5][2] * frustum[5][2]);
        frustum[5][0] /= temp;
        frustum[5][1] /= temp;
        frustum[5][2] /= temp;
        frustum[5][3] /= temp;
    }
		
    public boolean isChunkInFrustum(Vector3i location, Vector3i dimensions) {        
        for(int p = 0; p < 6; p++ ) {
            if(frustum[p][0] * (location.x - dimensions.x*World.voxelSize) + frustum[p][1] * (location.y - dimensions.y*World.voxelSize) + frustum[p][2] * (location.z - dimensions.z*World.voxelSize) + frustum[p][3] > 0) continue;
            if(frustum[p][0] * (location.x + dimensions.x*World.voxelSize) + frustum[p][1] * (location.y - dimensions.y*World.voxelSize) + frustum[p][2] * (location.z - dimensions.z*World.voxelSize) + frustum[p][3] > 0) continue;
            if(frustum[p][0] * (location.x - dimensions.x*World.voxelSize) + frustum[p][1] * (location.y + dimensions.y*World.voxelSize) + frustum[p][2] * (location.z - dimensions.z*World.voxelSize) + frustum[p][3] > 0) continue;
            if(frustum[p][0] * (location.x + dimensions.x*World.voxelSize) + frustum[p][1] * (location.y + dimensions.y*World.voxelSize) + frustum[p][2] * (location.z - dimensions.z*World.voxelSize) + frustum[p][3] > 0) continue;
            if(frustum[p][0] * (location.x - dimensions.x*World.voxelSize) + frustum[p][1] * (location.y - dimensions.y*World.voxelSize) + frustum[p][2] * (location.z + dimensions.z*World.voxelSize) + frustum[p][3] > 0) continue;
            if(frustum[p][0] * (location.x + dimensions.x*World.voxelSize) + frustum[p][1] * (location.y - dimensions.y*World.voxelSize) + frustum[p][2] * (location.z + dimensions.z*World.voxelSize) + frustum[p][3] > 0) continue;
            if(frustum[p][0] * (location.x - dimensions.x*World.voxelSize) + frustum[p][1] * (location.y + dimensions.y*World.voxelSize) + frustum[p][2] * (location.z + dimensions.z*World.voxelSize) + frustum[p][3] > 0) continue;
            if(frustum[p][0] * (location.x + dimensions.x*World.voxelSize) + frustum[p][1] * (location.y + dimensions.y*World.voxelSize) + frustum[p][2] * (location.z + dimensions.z*World.voxelSize) + frustum[p][3] > 0) continue;
            return false;
        }
        return true;
    }
	
	public void printFrustum() {
		System.out.println("Frustum");
    	System.out.format("[% 10.4f % 10.4f % 10.4f % 10.4f \n",   frustum[0][0], frustum[0][1], frustum[0][2], frustum[0][3]);
    	System.out.format(" % 10.4f % 10.4f % 10.4f % 10.4f \n",   frustum[1][0], frustum[1][1], frustum[1][2], frustum[1][3]);
    	System.out.format(" % 10.4f % 10.4f % 10.4f % 10.4f \n",   frustum[2][0], frustum[2][1], frustum[2][2], frustum[2][3]);
    	System.out.format(" % 10.4f % 10.4f % 10.4f % 10.4f \n",   frustum[3][0], frustum[3][1], frustum[3][2], frustum[3][3]);
    	System.out.format(" % 10.4f % 10.4f % 10.4f % 10.4f \n",   frustum[4][0], frustum[4][1], frustum[4][2], frustum[4][3]);
    	System.out.format(" % 10.4f % 10.4f % 10.4f % 10.4f]\n\n", frustum[5][0], frustum[5][1], frustum[5][2], frustum[5][3]);
	}
	
	public void printView() {
		view.print("View");
	}
	
	public void printProjection() {
		projection.print("Projection");
	}
	
	public void printModelViewProjection() {
		modelViewProjection.print("Model View Projection");
	}
	
	public float getFov() {
		return fov;
	}

	public void setFov(float fov) {
		this.fov = fov;
	}

	public float getAspect() {
		return aspect;
	}

	public void setAspect(float aspect) {
		this.aspect = aspect;
	}

	public float getzNear() {
		return zNear;
	}

	public void setzNear(float zNear) {
		this.zNear = zNear;
	}

	public float getzFar() {
		return zFar;
	}

	public void setzFar(float zFar) {
		this.zFar = zFar;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getDirection() {
		return direction;
	}
	
	public Matrix4f getModelViewProjection() {
		return modelViewProjection;
	}
	
	public Matrix4f getProjection() {
		return projection;
	}

	public Matrix4f getView() {
		return view;
	}

	public Matrix4f getRotation() {
		return rotation;
	}

	public Vector3f getRight() {
		return right;
	}
	
	public Vector3f getUp() {
		return up;
	}
	
	public float[][] getFrustum() {
		return frustum;
	}
	
}

