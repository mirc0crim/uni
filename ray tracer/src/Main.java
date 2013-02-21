import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.vecmath.Vector3f;


public class Main {

	public static Camera camera;

	public static void main(String[] args) {

		Vector3f eye = new Vector3f(0.f, 0.f, 2.f);
		Vector3f lookAt = new Vector3f(0.f, 0.f, 0.f);
		Vector3f up = new Vector3f(0.f, 1.f, 0.f);
		float fov = 60.f;
		int width = 512;
		int height = 512;
		float aspect = (float) width / (float) height;
		camera = new Camera(eye, lookAt, up, fov, aspect, width, height);

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
