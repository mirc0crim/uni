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

	private Matrix4f projectionMatrix;
	private float nearPlane, farPlane, aspectRatio, verticalFOV;

	/**
	 * Construct a default viewing frustum. The frustum is given by a
	 * default 4x4 projection matrix.
	 */
	public Frustum()
	{
		aspectRatio = 1; // aspect ratio is 1, because m00 = m11
		verticalFOV = 413; // 1/(tan(x*pi/360)) = 2
		nearPlane = 1; // (x+y)/(x-y) = -1.02; (2*x*y)/(x-y) = -2.02;
		farPlane = 101; // --> x = 1, y = 101
		projectionMatrix = new Matrix4f();
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
	public Matrix4f getProjectionMatrix()
	{
		return projectionMatrix;
	}

	private void setProjectionMatrix() {
		Matrix4f newMatrix = new Matrix4f();
		projectionMatrix
		.setM00((float) (1 / (aspectRatio * Math.tan(verticalFOV * Math.PI / 360))));
		projectionMatrix.setM11((float) (1 / Math.tan(verticalFOV * Math.PI / 360)));
		projectionMatrix.setM22((nearPlane + farPlane) / (nearPlane - farPlane));
		projectionMatrix.setM32(-1);
		projectionMatrix.setM23(2 * nearPlane * farPlane / (nearPlane - farPlane));
	}

	public float getNearPlane() {
		return nearPlane;
	}

	public void setNearPlane(float nearPlane) {
		this.nearPlane = nearPlane;
		setProjectionMatrix();
	}

	public float getFarPlane() {
		return farPlane;
	}

	public void setFarPlane(float farPlane) {
		this.farPlane = farPlane;
		setProjectionMatrix();
	}

	public float getAspectRatio() {
		return aspectRatio;
	}

	public void setAspectRatio(float aspectRatio) {
		this.aspectRatio = aspectRatio;
		setProjectionMatrix();
	}

	public float getVerticalFOV() {
		return verticalFOV;
	}

	public void setVerticalFOV(float verticalFOV) {
		this.verticalFOV = verticalFOV;
		setProjectionMatrix();
	}
}
