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
		int r = (int) (255.f * film[i][j].red);
		int g = (int) (255.f * film[i][j].green);
		int b = (int) (255.f * film[i][j].blue);
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
