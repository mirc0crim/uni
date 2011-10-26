package jrtr;

import javax.vecmath.Matrix4f;

/**
 * Stores the specification of a viewing frustum, or a viewing
 * volume. The viewing frustum is represented by a 4x4 projection
 * matrix. You will extend this class to construct the projection
 * matrix from intuitive parameters.
 * <p>
 * A scene manager (see {@link SceneManagerInterface}, {@link SimpleSceneManager})
 * stores a frustum.
 */
public class Frustum {

	private static Matrix4f projectionMatrix = new Matrix4f();
	private static float nearPlane = 1;// (x+y)/(x-y)=-1.02; (2*x*y)/(x-y)=-2.02
	private static float farPlane = 101;// --> x = 1, y = 101
	private static float aspectRatio = 1;// aspect ratio is 1, because m00=m11;
	private static float verticalFOV = 53;// 1/(tan(x*pi/360)) = 2 --> 53

	/**
	 * Construct a default viewing frustum. The frustum is given by a
	 * default 4x4 projection matrix.
	 */
	public Frustum()
	{
		// float f[] = {2.f, 0.f, 0.f, 0.f,
		// 0.f, 2.f, 0.f, 0.f,
		// 0.f, 0.f, -1.02f, -2.02f,
		// 0.f, 0.f, -1.f, 0.f};
		// projectionMatrix.set(f);
		setProjectionMatrix();
	}

	/**
	 * Return the 4x4 projection matrix, which is used for example by
	 * the renderer.
	 * 
	 * @return the 4x4 projection matrix
	 */
	public static Matrix4f getProjectionMatrix()
	{
		return projectionMatrix;
	}

	private static void setProjectionMatrix() {
		projectionMatrix
		.setM00((float) (1 / (aspectRatio * Math.tan(verticalFOV * Math.PI / 360))));
		projectionMatrix.setM11((float) (1 / Math.tan(verticalFOV * Math.PI / 360)));
		projectionMatrix.setM22((nearPlane + farPlane) / (nearPlane - farPlane));
		projectionMatrix.setM32(-1);
		projectionMatrix.setM23(2 * nearPlane * farPlane / (nearPlane - farPlane));
	}

	public static float getNearPlane() {
		return nearPlane;
	}

	public static void setNearPlane(float nearPl) {
		nearPlane = nearPl;
		setProjectionMatrix();
	}

	public static float getFarPlane() {
		return farPlane;
	}

	public static void setFarPlane(float farPl) {
		farPlane = farPl;
		setProjectionMatrix();
	}

	public static float getAspectRatio() {
		return aspectRatio;
	}

	public static void setAspectRatio(float aspectRa) {
		aspectRatio = aspectRa;
		setProjectionMatrix();
	}

	public static float getVerticalFOV() {
		return verticalFOV;
	}

	public static void setVerticalFOV(float vertFOV) {
		verticalFOV = vertFOV;
		setProjectionMatrix();
	}
}
