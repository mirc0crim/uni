package scenes;

import java.io.File;
import java.io.IOException;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import rt.BlinnIntegrator;
import rt.Camera;
import rt.DiffuseMaterial;
import rt.Film;
import rt.Instance;
import rt.IntegratorFactory;
import rt.Intersectable;
import rt.IntersectableList;
import rt.LightList;
import rt.Mesh;
import rt.Plane;
import rt.PointLight;
import rt.Spectrum;
import rt.Tonemapper;

public class Assignment1_Instancing implements Scene {

	public Camera camera;
	public Film film;
	public IntersectableList objects;
	public LightList lights;
	public IntegratorFactory integratorFactory;
	public String outputFileName;
	public Tonemapper tonemapper;

	/**
	 * Timing: 22 sec on 8 core Xeon 2.5GHz
	 */
	public Assignment1_Instancing() {
		outputFileName = new String("Assignment1_Instancing.png");

		// Specify integrator to be used
		integratorFactory = new BlinnIntegrator();

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

		// Box
		Plane plane = new Plane(new Vector3f(0.f, 1.f, 0.f), 1.f);
		plane.setMaterial(new DiffuseMaterial(new Spectrum(0.f, 0.8f, 0.8f)));
		objects.add(plane);

		plane = new Plane(new Vector3f(0.f, 0.f, 1.f), 1.f);
		plane.setMaterial(new DiffuseMaterial(new Spectrum(0.3f, 0.8f, 0.8f)));
		objects.add(plane);

		plane = new Plane(new Vector3f(-1.f, 0.f, 0.f), 1.f);
		plane.setMaterial(new DiffuseMaterial(new Spectrum(1.f, 0.8f, 0.8f)));
		objects.add(plane);

		plane = new Plane(new Vector3f(1.f, 0.f, 0.f), 1.f);
		plane.setMaterial(new DiffuseMaterial(new Spectrum(0.f, 0.8f, 0.0f)));
		objects.add(plane);

		plane = new Plane(new Vector3f(0.f, -1.f, 0.f), 1.f);
		plane.setMaterial(new DiffuseMaterial(new Spectrum(0.8f, 0.8f, 0.8f)));
		objects.add(plane);

		// Add objects
		Mesh mesh = new Mesh();
		File file = new File(".\\objects\\teapot.obj");
		try {
			mesh.loadObjFile(file, 1f);
		} catch (IOException e) {
			System.out.printf("Could not read .obj file\n");
			return;
		}

		mesh.setMaterial(new DiffuseMaterial(new Spectrum(.5f, .5f, .5f)));

		Matrix4f t = new Matrix4f();
		t.setIdentity();

		// Instance one
		t.setScale(0.5f);
		t.setTranslation(new Vector3f(0.f, -0.25f, 0.f));
		Instance instance = new Instance(mesh, t);
		objects.add(instance);

		// Instance two
		t.setScale(0.5f);
		t.setTranslation(new Vector3f(0.f, 0.25f, 0.f));
		instance = new Instance(mesh, t);
		objects.add(instance);

		// List of lights
		lights = new LightList();

		PointLight light = new PointLight(new Vector3f(0.f, 0.8f, 0.8f), new Spectrum(.7f,
				.7f, .7f));
		lights.add(light);

		light = new PointLight(new Vector3f(-0.8f, 0.2f, 1.f), new Spectrum(.5f, .5f, .5f));
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
