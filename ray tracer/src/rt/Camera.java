package rt;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class Camera {

	private Matrix4f cameraMatrix;
	private Vector3f centerOfProjection;
	private Vector3f lookAtPoint;
	private Vector3f upVector;
	private Vector4f origin;
	private float theta;
	private float aspectRatio;
	private float width;
	private float height;

	public Camera(Vector3f from, Vector3f to, Vector3f up, float fov, float aspect, int w,
			int h) {
		cameraMatrix = new Matrix4f();
		centerOfProjection = from;
		lookAtPoint = to;
		upVector = up;
		theta = fov;
		aspectRatio = aspect;
		width = w;
		height = h;
		origin = new Vector4f(0, 0, 0, 1);
		updateCameraMatrix();
	}

	public Matrix4f getCameraMatrix() {
		return cameraMatrix;
	}

	public void setCameraMatrix(Matrix4f cameraToWorld) {
		cameraMatrix.set(cameraToWorld);
	}

	public Vector3f getCenterOfProjection() {
		return centerOfProjection;
	}

	public void setCenterOfProjection(Vector3f from) {
		centerOfProjection = from;
		updateCameraMatrix();
	}

	public Vector3f getLookAtPoint() {
		return lookAtPoint;
	}

	public void setLookAtPoint(Vector3f to) {
		lookAtPoint = to;
		updateCameraMatrix();
	}

	public Vector3f getUpVector() {
		return upVector;
	}

	public void setUpVector(Vector3f up) {
		upVector = up;
		updateCameraMatrix();
	}

	public float getTheta() {
		return theta;
	}

	public void setTheta(float fov) {
		theta = fov;
	}

	public float getAspectRatio() {
		return aspectRatio;
	}

	public void setAspectRatio(float aspect) {
		aspectRatio = aspect;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float w) {
		width = w;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float h) {
		height = h;
	}

	private void updateCameraMatrix() {
		Matrix4f myMat = new Matrix4f();
		Vector3f uVec = new Vector3f();
		Vector3f vVec = new Vector3f();
		Vector3f wVec = new Vector3f();
		Vector3f tVec = new Vector3f(); // temp matrix used to calc Vec u,v,w

		tVec.set(centerOfProjection);
		tVec.sub(lookAtPoint);
		tVec.normalize();
		wVec.set(tVec);

		tVec.set(upVector);
		tVec.cross(upVector, wVec);
		tVec.normalize();
		uVec.set(tVec);

		tVec.set(wVec);
		tVec.cross(wVec, uVec);
		vVec.set(tVec);

		myMat.setColumn(0, uVec.x, uVec.y, uVec.z, 0);
		myMat.setColumn(1, vVec.x, vVec.y, vVec.z, 0);
		myMat.setColumn(2, wVec.x, wVec.y, wVec.z, 0);
		myMat.setColumn(3, centerOfProjection.x, centerOfProjection.y, centerOfProjection.z, 1);
		cameraMatrix.set(myMat);
	}

	public Ray getPrimaryRay(int i, int j) {
		Vector4f suvw = new Vector4f(u(i, j), v(i, j), -1, 1);
		Vector4f o = new Vector4f(origin);
		cameraMatrix.transform(suvw);
		cameraMatrix.transform(o);
		return new Ray(suvw, o);
	}

	private float u(int i, int j) {
		float t = (float) Math.tan(theta / (2 * Math.PI) / 2);
		float r = aspectRatio * t;
		float l = -r;
		return l + (r - l) * ((i + 0.5f) / width);
	}

	private float v(int i, int j) {
		float t = (float) Math.tan(theta / (2 * Math.PI) / 2);
		float b = -t;
		return b + (t - b) * ((j + 0.5f) / height);
	}

}
