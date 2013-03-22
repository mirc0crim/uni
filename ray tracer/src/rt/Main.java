package rt;

import scenes.Assignment1_Basic;
import scenes.Assignment1_First;
import scenes.Assignment1_Instancing;
import scenes.Assignment1_Mesh;
import scenes.Assignment1_Mirror;
import scenes.Assignment1_Refractive;
import scenes.Assignment1_Triangle;
import scenes.Scene;

public class Main {

	private static int sceneNo = 7;
	private static int maxThreads = Runtime.getRuntime().availableProcessors();
	private static Scene image;

	public static void main(String[] args) throws InterruptedException {
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
		case 6:
			image = new Assignment1_Triangle();
			break;
		case 7:
			image = new Assignment1_Mesh();
			break;
		}
		System.out.println("Rendering image with " + maxThreads + " Threads");
		int p = image.getFilm().getFilmWidth() / 10;
		int s = 10;
		long start = System.currentTimeMillis();
		if (image.getFilm().getFilmWidth() % maxThreads != 0) {
			System.out.println("The Film Width should be a multiple of " + maxThreads);
			maxThreads = 1;
		}
		for (int w = 0; w < image.getFilm().getFilmWidth(); w += maxThreads) {
			if (w > p) {
				System.out.println(s + "%");
				s += 10;
				p += image.getFilm().getFilmWidth() / 10;
			}
			Thread[] a = new Thread[maxThreads];
			for (int i = 0; i < maxThreads; i++) {
				a[i] = new Thread(new MyThread(w + i, image));
				a[i].start();
			}
			for (int i = 0; i < maxThreads; i++)
				a[i].join();
		}
		image.getTonemapper().createImage(image.getFilm(), image.getOutputFileName());
		long end = System.currentTimeMillis();
		System.out.println("Image successfully written to disk! " + (end - start));
	}
}