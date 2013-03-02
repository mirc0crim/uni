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
		int width = 1024;
		int height = 1024;
		float aspect = (float) width / (float) height;
		camera = new Camera(eye, lookAt, up, fov, aspect, width, height);

		Vector3f normal1 = new Vector3f(0, 1, 0);
		float d = 1;
		Plane bottom = new Plane(normal1, d);
		IntersectableList.add(bottom);
		Vector3f normal2 = new Vector3f(0, -1, 0);
		Plane top = new Plane(normal2, d);
		IntersectableList.add(top);

		Vector3f v1 = new Vector3f(1, 0, 0);
		Vector3f v2 = new Vector3f(1, 1, 0);
		Vector3f v3 = new Vector3f(0, 1, 0);
		Triangle triangle = new Triangle(v1, v2, v3);
		IntersectableList.add(triangle);

		Vector3f c = new Vector3f(0, 0, 0);
		float r = 1;
		Sphere sphere = new Sphere(c, r);
		IntersectableList.add(sphere);

		film = new Film(width, height);

		for (int w = 0; w < width; w++)
			for (int h = 0; h < height; h++) {
				Vector4f primaryRay = Ray.transform(new Matrix4f(camera.getCameraMatrix()),
						new Vector4f(camera.getPixelRay(w, h)));
				float[] distList = new float[IntersectableList.size()];
				int o = 0;
				for (Intersectable object : IntersectableList) {
					distList[o] = object.testIntersection(new Vector3f(primaryRay.x,
							primaryRay.y, primaryRay.z), new Vector3f(eye));
					o++;
				}
				int red = 0;
				int green = 0;
				int blue = 0;
				if (distList[3] < 0 && distList[3] > -5) {
					blue = 255 - (int) (distList[3] * -51);
				} else if (distList[1] < 0 && distList[1] > -5) {
					green = 255 - (int) (distList[1] * -51);
				} else if (distList[0] < 0 && distList[0] > -5) {
					red = 255 - (int) (distList[0] * -51);
				} else if (distList[2] < 0 && distList[2] > -5) {
					red = 255 - (int) (distList[2] * -51);
				}
				int rgb = (red << 16) + (green << 8) + blue;
				film.setPixel(w, h, rgb);
			}

		film.createImage("abc.jpg");
	}
}
