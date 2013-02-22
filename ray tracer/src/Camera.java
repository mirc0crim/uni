import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;


public class Camera {

	private static Matrix4f cameraMatrix;
	private static Vector3f centerOfProjection;
	private static Vector3f lookAtPoint;
	private static Vector3f upVector;
	private static float theta;
	private static float aspectRatio;
	private static float width;
	private static float height;

	public Camera(Vector3f from, Vector3f to, Vector3f up, float fov, float aspect, int w, int h) {
		cameraMatrix = new Matrix4f();
		centerOfProjection = from;
		lookAtPoint = to;
		upVector = up;
		theta = fov;
		aspectRatio = aspect;
		width = w;
		height = h;
		updateCameraMatrix();
	}

	public static Matrix4f getCameraMatrix() {
		return cameraMatrix;
	}

	public static void setCameraMatrix(Matrix4f cameraToWorld) {
		cameraMatrix.set(cameraToWorld);
	}

	public static Vector3f getCenterOfProjection() {
		return centerOfProjection;
	}

	public static void setCenterOfProjection(Vector3f from) {
		centerOfProjection = from;
	}

	public static Vector3f getLookAtPoint() {
		return lookAtPoint;
	}

	public static void setLookAtPoint(Vector3f to) {
		lookAtPoint = to;
	}

	public static Vector3f getUpVector() {
		return upVector;
	}

	public static void setUpVector(Vector3f up) {
		upVector = up;
	}

	public static float getTheta() {
		return theta;
	}

	public static void setTheta(float fov) {
		theta = fov;
	}

	public static float getAspectRatio() {
		return aspectRatio;
	}

	public static void setAspectRatio(float aspect) {
		aspectRatio = aspect;
	}

	public static float getWidth() {
		return width;
	}

	public static void setWidth(float w) {
		width = w;
	}

	public static float getHeight() {
		return height;
	}

	public static void setHeight(float h) {
		height = h;
	}

	private static void updateCameraMatrix() {
		Matrix4f newMatrix = new Matrix4f();
		Vector3f u = new Vector3f();
		Vector3f v = new Vector3f();
		Vector3f w = new Vector3f();
		Vector3f t = new Vector3f(); // temporary matrix used to calc u,v,w

		t.set(centerOfProjection);
		t.sub(lookAtPoint);
		t.normalize();
		w.set(t);

		t.set(upVector);
		t.cross(t, w);
		t.normalize();
		u.set(t);

		t.cross(w, u);
		v.set(t);

		newMatrix.setColumn(0, u.getX(), u.getY(), u.getZ(), 0);
		newMatrix.setColumn(1, v.getX(), v.getY(), v.getZ(), 0);
		newMatrix.setColumn(2, w.getX(), w.getY(), w.getZ(), 0);
		newMatrix.setColumn(3, centerOfProjection.getX(), centerOfProjection.getY(),
				centerOfProjection.getZ(), 1);
		cameraMatrix.set(newMatrix);
	}

	public static Vector4f getPixelRay(int i, int j) {
		Vector4f suvw = new Vector4f();
		suvw.setX(u(i, j));
		suvw.setY(v(i, j));
		suvw.setZ(-1);
		suvw.setW(1);
		return suvw;
	}

	private static float u(int i, int j) {
		float t = (float) Math.tan(theta / 2);
		float r = aspectRatio * t;
		float l = -r;
		return l + (r - l) * ((i + 0.5f) / width);
	}

	private static float v(int i, int j) {
		float t = (float) Math.tan(theta / 2);
		float b = -t;
		return b + (t - b) * ((j + 0.5f) / height);
	}

}
