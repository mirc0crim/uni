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

		film = new Film(width, height);

		for (int w = 0; w < width; w++)
			for (int h = 0; h < height; h++) {
				Vector4f primaryRay = Ray.transform(new Matrix4f(camera.getCameraMatrix()),
						new Vector4f(camera.getPixelRay(w, h)));
				Vector3f l = new Vector3f(primaryRay.x, primaryRay.y, primaryRay.z);
				Vector3f p0 = new Vector3f(normal);
				p0.scale(-d);
				Vector3f l0 = new Vector3f(eye);
				Vector3f n = new Vector3f(normal);
				p0.sub(l0);
				float intersection = p0.dot(n) / l.dot(n);
				int rgb = 0;
				if (intersection > 0) {
					int red = 255;
					int green = 255;
					int blue = 255;
					rgb = (red << 16) + (green << 8) + blue;
				}
				film.setPixel(w, h, rgb);
			}

		film.createImage("abc.jpg");
	}



}
