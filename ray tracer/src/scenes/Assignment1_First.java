package scenes;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import rt.BlinnIntegrator;
import rt.BlinnMaterial;
import rt.Camera;
import rt.Film;
import rt.IntegratorFactory;
import rt.Intersectable;
import rt.IntersectableList;
import rt.LightList;
import rt.PointLight;
import rt.Spectrum;
import rt.Sphere;
import rt.Tonemapper;

public class Assignment1_First implements Scene {

	// Variables accessed by the renderer
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
		tonemapper = new Tonemapper();

		// Make camera and film
		Vector3f eye = new Vector3f(0.f, 0.f, 2.f);
		Vector3f lookAt = new Vector3f(0.f, 0.f, 0.f);
		Vector3f up = new Vector3f(0.f, 1.f, 0.f);
		float fov = 60.f;
		int width = 512;
		int height = 512;
		float aspect = (float) width / (float) height;
		camera = new Camera(eye, lookAt, up, fov, aspect, width, height);
		film = new Film(width, height);

		objects = new IntersectableList();

		Vector4f center = new Vector4f(1f, 1f, 0.f, 1.f);
		float radius = 0.2f;
		Sphere sphere = new Sphere(center, radius);
		Spectrum kd = new Spectrum(0.2f, 0.2f, 0.2f);
		sphere.setMaterial(new BlinnMaterial(kd));
		objects.add(sphere);

		// List of lights
		lights = new LightList();

		Vector4f position = new Vector4f(0.f, 0.8f, 0.8f, 1);
		Spectrum strength = new Spectrum(10.f, 10.f, 10.f);
		PointLight light = new PointLight(position, strength);

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
