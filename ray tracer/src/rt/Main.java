package rt;

import scenes.Assignment1_Basic;
import scenes.Assignment1_First;
import scenes.Assignment1_Instancing;
import scenes.Assignment1_Mirror;
import scenes.Assignment1_Refractive;
import scenes.Scene;

public class Main {

	private static int sceneNo = 3;
	private static Scene image;

	public static void main(String[] args) {
		switch (sceneNo) {
		case 1:
			image = new Assignment1_First();
			break;
		case 2:
			image = new Assignment1_Basic();
			break;
		case 3:
			image = new Assignment1_Instancing();
			break;
		case 4:
			image = new Assignment1_Mirror();
			break;
		case 5:
			image = new Assignment1_Refractive();
			break;
		}
		System.out.println("Rendering image...");
		int p = image.getFilm().getFilmWidth() / 10;
		int s = 10;
		for (int w = 0; w < image.getFilm().getFilmWidth(); w++) {
			if (w == p) {
				System.out.println(s + "%");
				s += 10;
				p += image.getFilm().getFilmWidth() / 10;
			}
			for (int h = 0; h < image.getFilm().getFilmHeight(); h++) {
				Ray ray = image.getCamera().getPrimaryRay(w, h);
				Spectrum spectrum = image.getIntegratorFactory().integrate(image.getObjects(),
						image.getLights(), image.getCamera().getCenterOfProjection(), ray, 1);
				image.getFilm().setPixel(w, h, spectrum);
			}
		}
		image.getTonemapper().createImage(image.getFilm(), image.getOutputFileName());
		System.out.println("Image successfully written to disk!");
	}
}