package rt;

import scenes.Scene;

public class MyThread implements Runnable {

	Scene image;
	int w;

	public MyThread(int width, Scene img) {
		image = img;
		w = width;
	}

	public void run() {
		for (int h = 0; h < image.getFilm().getFilmHeight(); h++) {
			Ray ray = image.getCamera().getPrimaryRay(w, h);
			Spectrum spectrum = image.getIntegratorFactory().integrate(image.getObjects(),
					image.getLights(), image.getCamera().getCenterOfProjection(), ray, 1);
			image.getFilm().setPixel(w, h, spectrum);
		}
	}

}