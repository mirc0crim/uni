package rt;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tonemapper {

	public void createImage(Film film, String name) {
		BufferedImage image = new BufferedImage(film.getFilmWidth(), film.getFilmHeight(),
				BufferedImage.TYPE_3BYTE_BGR);
		for (int w = 0; w < film.getFilmWidth(); w++)
			for (int h = 0; h < film.getFilmHeight(); h++)
				image.setRGB(w, film.getFilmHeight() - h - 1, film.getPixelRGB(w, h));
		try {
			ImageIO.write(image, "jpg", new File(name));
		} catch (IOException e) {
			System.out.println("Image " + name + " could not be saved");
		}
		image.flush();
	}

}
