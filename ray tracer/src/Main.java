import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;


public class Main {

	public static Camera camera;
	public static Intersectable objects;
	public static Film film;

	public static void main(String[] args) {

		Vector3f eye = new Vector3f(0.f, 0.f, 2.f);
		Vector3f lookAt = new Vector3f(0.f, 0.f, 0.f);
		Vector3f up = new Vector3f(0.f, 1.f, 0.f);
		float fov = 60.f;
		int width = 512;
		int height = 512;
		float aspect = (float) width / (float) height;
		camera = new Camera(eye, lookAt, up, fov, aspect, width, height);

		Vector3f normal = new Vector3f(0.f, 1.f, 0.f);
		float d = 1.f;
		Plane plane = new Plane(normal, d);
		objects = plane;

		Vector3f v1 = new Vector3f(2, 2, 0);
		Vector3f v2 = new Vector3f(0, -7, 0);
		Vector3f v3 = new Vector3f(-7, 0, 0);
		Triangle triangle = new Triangle(v1, v2, v3);

		film = new Film(width, height);

		for (int w = 0; w < width; w++)
			for (int h = 0; h < height; h++) {
				Vector4f primaryRay = Ray.transform(new Matrix4f(camera.getCameraMatrix()),
						new Vector4f(camera.getPixelRay(w, h)));
				boolean intersectionPlane = plane.testIntersection(new Vector3f(primaryRay.x,
						primaryRay.y, primaryRay.z), eye);
				boolean intersectionTriangle = triangle.testIntersection(new Vector3f(primaryRay.x,
						primaryRay.y, primaryRay.z), eye);
				int rgb = 0;
				int red = 0;
				int green = 0;
				int blue = 0;
				if (intersectionTriangle)
					red = 255;
				else if (intersectionPlane)
					blue = 255;
				rgb = (red << 16) + (green << 8) + blue;
				film.setPixel(w, h, rgb);
			}

		film.createImage("abc.jpg");
	}



}
