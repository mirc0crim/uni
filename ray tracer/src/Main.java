import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class Main {

	public static Camera camera;
	public static Film film;

	public static void main(String[] args) {

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

		Vector3f v1 = new Vector3f(1, 0, 0);
		Vector3f v2 = new Vector3f(1, 1, 0);
		Vector3f v3 = new Vector3f(0, 1, 0);
		Triangle triangle = new Triangle(v1, v2, v3);

		Vector3f c = new Vector3f(0, 0, 0);
		float r = 1.5f;
		Sphere sphere = new Sphere(c, r);

		film = new Film(width, height);

		for (int w = 0; w < width; w++)
			for (int h = 0; h < height; h++) {
				Vector4f primaryRay = new Vector4f(camera.getPixelRay(w, h));
				primaryRay = Ray.transform(new Matrix4f(camera.getCameraMatrix()),
						new Vector4f(camera.getPixelRay(w, h)));
				float intersectionPlane = plane.testIntersection(new Vector3f(primaryRay.x,
						primaryRay.y, primaryRay.z), new Vector3f(eye));
				float intersectionTriangle = triangle.testIntersection(new Vector3f(
						primaryRay.x, primaryRay.y, primaryRay.z), new Vector3f(eye));
				float intersectionSphere = sphere.testIntersection(new Vector3f(primaryRay.x,
						primaryRay.y, primaryRay.z), new Vector3f(eye));
				int rgb = 0;
				int red = 0;
				int green = 0;
				int blue = 0;
				if (intersectionSphere < 0) {
					// red = (int) (Math.abs(primaryRay.x) * 255);
					if (intersectionSphere < -5) {
						blue = 0;
					} else
						blue = 255 - (int) (intersectionSphere * -51);
				} else if (intersectionTriangle < 0) {
					// blue = (int) (Math.abs(primaryRay.x) * 255);
					// red = (int) (Math.abs(primaryRay.y) * 255);
					if (intersectionTriangle < -5)
						green = 0;
					else
						green = 255 - (int) (intersectionTriangle * -51);
				} else if (intersectionPlane < 0) {
					// green = (int) (Math.abs(primaryRay.x) * 255);
					// blue = (int) (Math.abs(primaryRay.y) * 255);
					if (intersectionPlane < -5)
						red = 0;
					else
						red = 255 - (int) (intersectionPlane * -51);
				}
				rgb = (red << 16) + (green << 8) + blue;
				film.setPixel(w, h, rgb);
			}

		film.createImage("abc.jpg");
	}
}
