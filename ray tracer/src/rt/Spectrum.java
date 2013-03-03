package rt;

public class Spectrum {

	float red;
	float green;
	float blue;

	public Spectrum(float r, float g, float b) {
		red = r;
		green = g;
		blue = b;
	}

	public Spectrum divideBy(float d) {
		return new Spectrum(red / d, green / d, blue / d);
	}

	public Spectrum multipliedBy(float m) {
		return new Spectrum(red * m, green * m, blue * m);
	}

}
