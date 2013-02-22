import javax.vecmath.Matrix4f;
import javax.vecmath.Vector4f;


public class Ray {

	Vector4f origin;
	Vector4f direction;

	public Ray(Vector4f orig, Vector4f dir) {
		origin = orig;
		direction = dir;
	}

	public static Vector4f transform(Vector4f r, Matrix4f mat) {
		Matrix4f t1 = new Matrix4f();
		for (int i = 0; i < 4; i++)
			t1.setColumn(i, r);
		mat.mul(t1);
		Vector4f t2 = new Vector4f();
		t2.setX(mat.m00);
		t2.setY(mat.m10);
		t2.setZ(mat.m20);
		t2.setW(mat.m30);
		return t2;
	}

}
