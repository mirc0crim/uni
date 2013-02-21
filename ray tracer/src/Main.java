import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public class Main {

	public static void main(String[] args) {
		createImage(200, 200);
	}

	public static void createImage(int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int r = 0; r < height; r++)
			for (int c = 0; c < width; c++) {
				int red = 255 * new Random().nextInt();
				int green = 255 * new Random().nextInt();
				int blue = 255 * new Random().nextInt();
				int rgb = red << 16 | green << 8 | blue;
				image.setRGB(c, r, rgb);
			}
		try {
			ImageIO.write(image, "jpg", new File("abc.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
