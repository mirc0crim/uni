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

	private static Matrix4f cameraMatrix = new Matrix4f();
	private static Vector3f centerOfProjection = new Vector3f(0, 0, 10);
	private static Vector3f lookAtPoint = new Vector3f(0, 0, 0);
	private static Vector3f upVector = new Vector3f(0, 1, 0);

	/**
	 * Construct a camera with a default camera matrix. The camera
	 * matrix corresponds to the world-to-camera transform. This default
	 * matrix places the camera at (0,0,10) in world space, facing towards
	 * the origin (0,0,0) of world space, i.e., towards the negative z-axis.
	 */
	public Camera()
	{
		// float f[] = { 1.f, 0.f, 0.f, 0.f,
		// 0.f, 1.f, 0.f, 0.f,
		// 0.f, 0.f, 1.f, -10.f,
		// 0.f, 0.f, 0.f, 1.f };
		// cameraMatrix.set(f);
		updateCameraMatrix();
	}

	/**
	 * Return the camera matrix, i.e., the world-to-camera transform. For example,
	 * this is used by the renderer.
	 * 
	 * @return the 4x4 world-to-camera transform matrix
	 */
	public static Matrix4f getCameraMatrix() {
		return cameraMatrix;
	}

	public static void setCameraMatrix(Matrix4f cameraMat) {
		cameraMatrix.set(cameraMat);
	}

	public static Vector3f getCenterOfProjection() {
		return centerOfProjection;
	}

	public static void setCenterOfProjection(Vector3f centerOfProjec) {
		centerOfProjection = centerOfProjec;
		updateCameraMatrix();
	}

	public static Vector3f getLookAtPoint() {
		return lookAtPoint;
	}

	public static void setLookAtPoint(Vector3f lookPnt) {
		lookAtPoint = lookPnt;
		updateCameraMatrix();
	}

	public static Vector3f getUpVector() {
		return upVector;
	}

	public static void setUpVector(Vector3f upVec) {
		upVector = upVec;
		updateCameraMatrix();
	}

	private static void updateCameraMatrix() {
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
