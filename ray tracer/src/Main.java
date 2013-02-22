import java.util.Random;

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
				Vector4f pixelRay = Camera.getPixelRay(w, h);
				pixelRay = Ray.transform(pixelRay, Camera.getCameraMatrix());
				int red = new Random().nextInt(255);
				int green = new Random().nextInt(255);
				int blue = new Random().nextInt(255);
				int rgb = (red << 16) + (green << 8) + blue;
				film.setPixel(w, h, rgb);
			}

		film.createImage("abc.jpg");
	}

}
