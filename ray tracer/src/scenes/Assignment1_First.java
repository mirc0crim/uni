package scenes;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import rt.BlinnMaterial;
import rt.Camera;
import rt.Film;
import rt.IntegratorFactory;
import rt.Intersectable;
import rt.LightList;
import rt.Plane;
import rt.PointLight;
import rt.SaneIntegrator;
import rt.Spectrum;
import rt.Tonemapper;

public class Assignment1_First implements Scene {

	// Variables accessed by the renderer
	public Camera camera;
	public Film film;
	public Intersectable objects;
	public LightList lights;
	public IntegratorFactory integratorFactory;
	public Tonemapper tonemapper;
	public String outputFileName;

	public Assignment1_First() {

		outputFileName = new String("Assignment1_First.png");

		// Specify integrator to be used
		integratorFactory = new SaneIntegrator();
		tonemapper = new Tonemapper();

		// Make camera and film
		Vector3f eye = new Vector3f(0.f,0.f,2.f);
		Vector3f lookAt = new Vector3f(0.f,0.f,0.f);
		Vector3f up = new Vector3f(0.f,1.f,0.f);
		float fov = 60.f;
		int width = 512;
		int height = 512;
		float aspect = (float)width/(float)height;
		camera = new Camera(eye, lookAt, up, fov, aspect, width, height);
		film = new Film(width, height);

		// A plane
		Vector4f normal = new Vector4f(0.f, 1.f, 0.f, 0);
		float d = 1.f;
		Plane plane = new Plane(normal, d);
		Spectrum kd = new Spectrum(0.f, 0.8f, 0.8f);
		plane.setMaterial(new BlinnMaterial(kd));
		objects = plane;

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
