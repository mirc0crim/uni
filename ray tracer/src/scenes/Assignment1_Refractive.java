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
import rt.RefractiveIntegrator;
import rt.RefractiveMaterial;
import rt.Spectrum;
import rt.Sphere;
import rt.Tonemapper;

public class Assignment1_Refractive implements Scene {

	public Camera camera;
	public Film film;
	public IntersectableList objects;
	public LightList lights;
	public IntegratorFactory integratorFactory;
	public String outputFileName;
	public Tonemapper tonemapper;

	/**
	 * Timing: 1.2sec on 8 core Xeon 2.5GHz
	 */
	public Assignment1_Refractive() {
		outputFileName = new String("Assignment1_Refractive.png");

		// Specify integrator to be used
		integratorFactory = new RefractiveIntegrator();

		// Specify pixel sampler to be used
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

		// List of objects
		objects = new IntersectableList();

		Sphere sphere = new Sphere(new Vector3f(0.f, 0.f, 0.f), .4f);
		// RefractiveMaterial(refractiveIndex)
		sphere.setMaterial(new RefractiveMaterial(1.5f));
		objects.add(sphere);

		sphere = new Sphere(new Vector3f(0.4f, 0.2f, -0.3f), .3f);
		// BlinnMaterial(kd, ks, shininess)
		sphere.setMaterial(new BlinnMaterial(new Spectrum(0.8f, 0.f, 0.f), new Spectrum(0.8f,
				0.8f, 0.8f), 30.f));
		objects.add(sphere);

		Plane plane = new Plane(new Vector3f(0.f, 1.f, 0.f), 1.f);
		plane.setMaterial(new BlinnMaterial(new Spectrum(0.f, 0.8f, 0.8f)));
		objects.add(plane);

		plane = new Plane(new Vector3f(0.f, 0.f, 1.f), 1.f);
		plane.setMaterial(new BlinnMaterial(new Spectrum(0.3f, 0.8f, 0.8f)));
		objects.add(plane);

		plane = new Plane(new Vector3f(-1.f, 0.f, 0.f), 1.f);
		plane.setMaterial(new BlinnMaterial(new Spectrum(1.f, 0.8f, 0.8f)));
		objects.add(plane);

		plane = new Plane(new Vector3f(1.f, 0.f, 0.f), 1.f);
		plane.setMaterial(new BlinnMaterial(new Spectrum(0.f, 0.8f, 0.0f)));
		objects.add(plane);

		plane = new Plane(new Vector3f(0.f, -1.f, 0.f), 1.f);
		plane.setMaterial(new BlinnMaterial(new Spectrum(0.8f, 0.8f, 0.8f)));
		objects.add(plane);

		// List of lights
		lights = new LightList();

		PointLight light = new PointLight(new Vector3f(0.f, 0.8f, 0.8f), new Spectrum(.7f,
				.7f, .7f));
		lights.add(light);

		light = new PointLight(new Vector3f(-0.8f, 0.2f, 0.0f), new Spectrum(.5f, .5f, .5f));
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
