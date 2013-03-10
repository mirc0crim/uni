package rt;

public class Film {

	private Spectrum[][] film;
	private int filmWidth;
	private int filmHeight;

	public Film(int width, int height) {
		filmWidth = width;
		filmHeight = height;
		film = new Spectrum[width][height];
	}

	public void setPixel(int w, int h, Spectrum spect) {
		film[w][h] = spect;
	}

	public Spectrum[][] getFilm() {
		return film;
	}

	public int getPixelRGB(int i, int j) {
		int r = (int) (Math.min((255.f * film[i][j].red), 255.f));
		int g = (int) (Math.min((255.f * film[i][j].green), 255.f));
		int b = (int) (Math.min((255.f * film[i][j].blue), 255.f));
		int rgb = r << 16 | g << 8 | b;
		return rgb;
	}

	public int getFilmWidth() {
		return filmWidth;
	}

	public void setFilmWidth(int w) {
		filmWidth = w;
	}

	public int getFilmHeight() {
		return filmHeight;
	}

	public void setFilmHeight(int h) {
		filmHeight = h;
	}

}
