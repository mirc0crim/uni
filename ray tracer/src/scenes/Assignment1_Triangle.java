package scenes;

import javax.vecmath.Vector3f;

import rt.BlinnMaterial;
import rt.Camera;
import rt.Film;
import rt.IntegratorFactory;
import rt.Intersectable;
import rt.IntersectableList;
import rt.LightList;
import rt.Plane;
import rt.PointLight;
import rt.ShadowIntegrator;
import rt.Spectrum;
import rt.Tonemapper;
import rt.Triangle;

public class Assignment1_Triangle implements Scene {

	public Camera camera;
	public Film film;
	public IntersectableList objects;
	public LightList lights;
	public IntegratorFactory integratorFactory;
	public Tonemapper tonemapper;
	public String outputFileName;

	/**
	 * Timing: 0.6 sec on 8 core Xeon 2.5GHz
	 */
	public Assignment1_Triangle() {
		outputFileName = new String("Assignment1_Triangle.png");

		// Specify integrator to be used
		integratorFactory = new ShadowIntegrator();

		// Specify pixel sampler to be used
		tonemapper = new Tonemapper();

		// Make camera and film
		Vector3f eye = new Vector3f(0.f, 0.f, 2.f);
		Vector3f lookAt = new Vector3f(0.f, 0.f, 0.f);
		Vector3f up = new Vector3f(0.f, 1.f, 0.f);
		float fov = 60.f;
		int width = 2048;
		int height = 2048;
		float aspect = (float) width / (float) height;
		camera = new Camera(eye, lookAt, up, fov, aspect, width, height);
		film = new Film(width, height);

		// List of objects
		objects = new IntersectableList();

		Vector3f v1 = new Vector3f(0.f, 0.f, 0.f);
		Vector3f v2 = new Vector3f(.8f, 0.f, 0.f);
		Vector3f v3 = new Vector3f(0.f, .3f, 0.f);
		Triangle tri = new Triangle(v1, v2, v3);
		Spectrum kd = new Spectrum(0.8f, 0.8f, 0.8f);
		tri.setMaterial(new BlinnMaterial(kd));
		objects.add(tri);

		Vector3f normal = new Vector3f(0.f, 1.f, 0.f);
		float d = 1.f;
		Plane plane = new Plane(normal, d);
		kd = new Spectrum(0.f, 0.8f, 0.8f);
		plane.setMaterial(new BlinnMaterial(kd));
		objects.add(plane);

		normal = new Vector3f(0.f, 0.f, 1.f);
		d = 1.f;
		plane = new Plane(normal, d);
		kd = new Spectrum(0.3f, 0.8f, 0.8f);
		plane.setMaterial(new BlinnMaterial(kd));
		objects.add(plane);

		normal = new Vector3f(-1.f, 0.f, 0.f);
		d = 1.f;
		plane = new Plane(normal, d);
		kd = new Spectrum(1.f, 0.8f, 0.8f);
		plane.setMaterial(new BlinnMaterial(kd));
		objects.add(plane);

		normal = new Vector3f(1.f, 0.f, 0.f);
		d = 1.f;
		plane = new Plane(normal, d);
		kd = new Spectrum(0.f, 0.8f, 0.0f);
		plane.setMaterial(new BlinnMaterial(kd));
		objects.add(plane);

		normal = new Vector3f(0.f, -1.f, 0.f);
		d = 1.f;
		plane = new Plane(normal, d);
		kd = new Spectrum(0.8f, 0.8f, 0.8f);
		plane.setMaterial(new BlinnMaterial(kd));
		objects.add(plane);

		Vector3f position = new Vector3f(0.f, 0.8f, 0.8f);
		Spectrum strength = new Spectrum(1.f, 1.f, 1.f);
		PointLight light = new PointLight(position, strength);

		// List of lights
		lights = new LightList();
		lights.add(light);
	}

	@Override
	public String getOutputFileName() {
		return outputFileName;
	}

	@Override
	public Film getFilm() {
		return film;
	}

	@Override
	public IntegratorFactory getIntegratorFactory() {
		return integratorFactory;
	}

	@Override
	public Camera getCamera() {
		return camera;
	}

	@Override
	public Intersectable getObjects() {
		return objects;
	}

	@Override
	public LightList getLights() {
		return lights;
	}

	@Override
	public Tonemapper getTonemapper() {
		return tonemapper;
	}
}
