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
		float x = mat.m00 * r.x + mat.m01 * r.y + mat.m02 * r.z + mat.m03 * r.w;
		float y = mat.m10 * r.x + mat.m11 * r.y + mat.m12 * r.z + mat.m13 * r.w;
		float z = mat.m20 * r.x + mat.m21 * r.y + mat.m22 * r.z + mat.m23 * r.w;
		float w = mat.m30 * r.x + mat.m31 * r.y + mat.m32 * r.z + mat.m33 * r.w;
		return new Vector4f(x, y, z, w);
	}

}
