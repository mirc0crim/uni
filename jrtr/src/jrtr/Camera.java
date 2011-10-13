package jrtr;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

/**
 * Stores the specification of a virtual camera. You will extend
 * this class to construct a 4x4 camera matrix, i.e., the world-to-
 * camera transform from intuitive parameters.
 * 
 * A scene manager (see {@link SceneManagerInterface}, {@link SimpleSceneManager})
 * stores a camera.
 */
public class Camera {

	private Matrix4f cameraMatrix;
	private Vector3f centerOfProjection, lookAtPoint, upVector;

	/**
	 * Construct a camera with a default camera matrix. The camera
	 * matrix corresponds to the world-to-camera transform. This default
	 * matrix places the camera at (0,0,10) in world space, facing towards
	 * the origin (0,0,0) of world space, i.e., towards the negative z-axis.
	 */
	public Camera()
	{
		centerOfProjection = new Vector3f(0,0,10);
		lookAtPoint = new Vector3f(0,0,0);
		upVector = new Vector3f(0,1,0);
		cameraMatrix = new Matrix4f();
		// float f[] = { 1.f, 0.f, 0.f, 0.f,
		// 0.f, 1.f, 0.f, 0.f,
		// 0.f, 0.f, 1.f, -10.f,
		// 0.f, 0.f, 0.f, 1.f };
		// cameraMatrix.set(f);
		setCameraMatrix();
	}

	/**
	 * Return the camera matrix, i.e., the world-to-camera transform. For example,
	 * this is used by the renderer.
	 * 
	 * @return the 4x4 world-to-camera transform matrix
	 */
	public Matrix4f getCameraMatrix()
	{
		return cameraMatrix;
	}

	public Vector3f getCenterOfProjection() {
		return centerOfProjection;
	}

	public void setCenterOfProjection(Vector3f centerOfProjection) {
		this.centerOfProjection = centerOfProjection;
		setCameraMatrix();
	}

	public Vector3f getLookAtPoint() {
		return lookAtPoint;
	}

	public void setLookAtPoint(Vector3f lookAtPoint) {
		this.lookAtPoint = lookAtPoint;
		setCameraMatrix();
	}

	public Vector3f getUpVector() {
		return upVector;
	}

	public void setUpVector(Vector3f upVector) {
		this.upVector = upVector;
		setCameraMatrix();
	}

	private void setCameraMatrix(){
		Matrix4f newMatrix = new Matrix4f();
		Vector3f x = new Vector3f();
		Vector3f y = new Vector3f();
		Vector3f z = new Vector3f();
		Vector3f t = new Vector3f(); // temporary matrix used to calc x,y,z

		t.set(centerOfProjection);
		t.sub(lookAtPoint);
		t.normalize();
		z.set(t);

		t.set(upVector);
		t.cross(t, z);
		t.normalize();
		x.set(t);

		t.cross(z, x);
		y.set(t);

		newMatrix.setColumn(0, x.getX(), x.getY(), x.getZ(), 0);
		newMatrix.setColumn(1, y.getX(), y.getY(), y.getZ(), 0);
		newMatrix.setColumn(2, z.getX(), z.getY(), z.getZ(), 0);
		newMatrix.setColumn(3, centerOfProjection.getX(), centerOfProjection.getY(),
				centerOfProjection.getZ(), 1);
		newMatrix.invert();
		cameraMatrix.set(newMatrix);
	}

}
