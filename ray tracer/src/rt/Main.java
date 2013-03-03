package rt;

import scenes.Assignment1_Basic;
import scenes.Assignment1_First;
import scenes.Scene;

public class Main {

	private static int sceneSelector = 2;
	private static Scene image;

	public static void main(String[] args) {
		switch (sceneSelector) {
		case 1:
			image = new Assignment1_First();
			break;
		case 2:
			image = new Assignment1_Basic();
			break;
		}
		System.out.println("Rendering image...");
		for (int w = 0; w < image.getFilm().getFilmWidth(); w++)
			for (int h = 0; h < image.getFilm().getFilmHeight(); h++) {
				Ray ray = image.getCamera().getPrimaryRay(w, h);
				Spectrum spectrum = image.getIntegratorFactory().integrate(image.getObjects(),
						image.getLights(), ray);
				image.getFilm().setPixel(w, h, spectrum);
			}
		image.getTonemapper().createImage(image.getFilm(), "asdf.jpg");
		System.out.println("Image successfully written to disk!");
	}
}