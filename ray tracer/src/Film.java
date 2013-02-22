import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Film {

	int[][] film;
	int filmWidth;
	int filmHeight;

	public Film(int width, int height) {
		filmWidth = width;
		filmHeight = height;
		film = new int[width][height];
		for (int w = 0; w < width; w++)
			for (int h = 0; h < height; h++)
				film[w][h] = 0;
	}

	public void setPixel(int w, int h, int rgb) {
		film[w][h] = rgb;
	}

	public void createImage(String name) {
		BufferedImage image = new BufferedImage(filmWidth, filmHeight, BufferedImage.TYPE_INT_RGB);
		for (int h = 0; h < filmHeight; h++)
			for (int w = 0; w < filmWidth; w++)
				image.setRGB(w, h, film[w][h]);
		try {
			ImageIO.write(image, "jpg", new File(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
