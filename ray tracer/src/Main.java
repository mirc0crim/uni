import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class Main {

	public static Camera camera;
	public static Film film;
	public static List<Intersectable> IntersectableList;

	public static void main(String[] args) {

		IntersectableList = new ArrayList<Intersectable>();

		Vector3f eye = new Vector3f(0, 0, 2);
		Vector3f lookAt = new Vector3f(0, 0, 0);
		Vector3f up = new Vector3f(0, 1, 0);
		float fov = 60;
		int width = 512;
		int height = 512;
		float aspect = (float) width / (float) height;
		camera = new Camera(eye, lookAt, up, fov, aspect, width, height);

		Vector3f normal = new Vector3f(0, 1, 0);
		float d = 1;
		Plane plane = new Plane(normal, d);
		IntersectableList.add(plane);

		Vector3f v1 = new Vector3f(1, 0, 0);
		Vector3f v2 = new Vector3f(1, 1, 0);
		Vector3f v3 = new Vector3f(0, 1, 0);
		Triangle triangle = new Triangle(v1, v2, v3);
		IntersectableList.add(triangle);

		Vector3f c = new Vector3f(0, 0, 0);
		float r = 1.5f;
		Sphere sphere = new Sphere(c, r);
		IntersectableList.add(sphere);

		film = new Film(width, height);

		for (int w = 0; w < width; w++)
			for (int h = 0; h < height; h++) {
				Vector4f primaryRay = new Vector4f(camera.getPixelRay(w, h));
				primaryRay = Ray.transform(new Matrix4f(camera.getCameraMatrix()),
						new Vector4f(camera.getPixelRay(w, h)));
				float[] distList = new float[IntersectableList.size()];
				int i = 0;
				for (Intersectable object : IntersectableList) {
					distList[i] = object.testIntersection(new Vector3f(primaryRay.x,
							primaryRay.y, primaryRay.z), new Vector3f(eye));
					i++;
				}
				int rgb = 0;
				int red = 0;
				int green = 0;
				int blue = 0;
				if (distList[2] < 0) {
					if (distList[2] < -5) {
						blue = 0;
					} else
						blue = 255 - (int) (distList[2] * -51);
				} else if (distList[1] < 0) {
					if (distList[1] < -5)
						green = 0;
					else
						green = 255 - (int) (distList[1] * -51);
				} else if (distList[0] < 0) {
					if (distList[0] < -5)
						red = 0;
					else
						red = 255 - (int) (distList[0] * -51);
				}
				rgb = (red << 16) + (green << 8) + blue;
				film.setPixel(w, h, rgb);
			}

		film.createImage("abc.jpg");
	}
}
