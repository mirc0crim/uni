package scenes;

import rt.Camera;
import rt.Film;
import rt.IntegratorFactory;
import rt.Intersectable;
import rt.LightList;
import rt.Tonemapper;

public interface Scene {
	public String getOutputFileName();
	public Film getFilm();
	public IntegratorFactory getIntegratorFactory();
	public Camera getCamera();
	public Intersectable getObjects();
	public LightList getLights();
	public Tonemapper getTonemapper();
}