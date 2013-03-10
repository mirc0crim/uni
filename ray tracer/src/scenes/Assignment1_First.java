package scenes;

import javax.vecmath.Vector3f;

import rt.BlinnIntegrator;
import rt.Camera;
import rt.DiffuseMaterial;
import rt.Film;
import rt.IntegratorFactory;
import rt.Intersectable;
import rt.IntersectableList;
import rt.LightList;
import rt.Plane;
import rt.PointLight;
import rt.Spectrum;
import rt.Tonemapper;

public class Assignment1_First implements Scene {

	public Camera camera;
	public Film film;
	public IntersectableList objects;
	public LightList lights;
	public IntegratorFactory integratorFactory;
	public Tonemapper tonemapper;
	public String outputFileName;

	public Assignment1_First() {
		outputFileName = new String("Assignment1_First.png");

		// Specify integrator to be used
		integratorFactory = new BlinnIntegrator();

		// Specify pixel sampler to be used
		tonemapper = new Tonemapper();

		// Make camera and film
		Vector3f eye = new Vector3f(0.f, 0.f, 2.f);
		Vector3f lookAt = new Vector3f(0.f, 0.f, 0.f);
		Vector3f up = new Vector3f(0.f, 1.f, 0.f);
		float fov = 60.f;
		int width = 1024;
		int height = 1024;
		float aspect = (float) width / (float) height;
		camera = new Camera(eye, lookAt, up, fov, aspect, width, height);
		film = new Film(width, height);

		// List of objects
		objects = new IntersectableList();

		// A plane
		Vector3f normal = new Vector3f(0.f, 1.f, 0.f);
		float d = 1.f;
		Plane plane = new Plane(normal, d);
		Spectrum kd = new Spectrum(0.f, 0.8f, 0.8f);
		plane.setMaterial(new DiffuseMaterial(kd));
		objects.add(plane);

		Vector3f position = new Vector3f(0.f, 0.8f, 0.8f);
		Spectrum strength = new Spectrum(10.f, 10.f, 10.f);
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
