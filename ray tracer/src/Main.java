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
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int h = 0; h < height; h++)
			for (int w = 0; w < width; w++) {
				int red = new Random().nextInt(255);
				int green = new Random().nextInt(255);
				int blue = new Random().nextInt(255);
				int rgb = (red << 16) + (green << 8) + blue;
				image.setRGB(w, h, rgb);
			}
		try {
			ImageIO.write(image, "jpg", new File("abc.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
