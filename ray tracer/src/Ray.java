import javax.vecmath.Matrix4f;
import javax.vecmath.Vector4f;


public class Ray {

	Vector4f origin;
	Vector4f direction;

	public Ray(Vector4f orig, Vector4f dir) {
		origin = orig;
		direction = dir;
	}

	public static Vector4f transform(Matrix4f mat, Vector4f r) {
		Matrix4f rAsMatrix = new Matrix4f();
		rAsMatrix.setZero();
		rAsMatrix.setColumn(0, r);
		mat.mul(rAsMatrix);
		return new Vector4f(mat.m00, mat.m10, mat.m20, mat.m30);
	}

}
