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

	public Spectrum(Spectrum spec) {
		red = spec.red;
		green = spec.green;
		blue = spec.blue;
	}

	public Spectrum divideBy(float d) {
		return new Spectrum(red / d, green / d, blue / d);
	}

	public Spectrum multipliedBy(float m) {
		return new Spectrum(red * m, green * m, blue * m);
	}

	public Spectrum multipliedBy(Spectrum spec) {
		return new Spectrum(red * spec.red, green * spec.green, blue * spec.blue);
	}

	public void append(Spectrum spec) {
		setRed(red + spec.red);
		setGreen(green + spec.green);
		setBlue(blue + spec.blue);
	}

	public void clampMax(float f) {
		setRed(Math.min(getRed(), f));
		setGreen(Math.min(getGreen(), f));
		setBlue(Math.min(getBlue(), f));
	}

	public void clampMin(float f) {
		setRed(Math.max(getRed(), f));
		setGreen(Math.max(getGreen(), f));
		setBlue(Math.max(getBlue(), f));
	}

	public float getRed() {
		return red;
	}

	public void setRed(float r) {
		red = r;
	}

	public float getGreen() {
		return green;
	}

	public void setGreen(float g) {
		green = g;
	}

	public float getBlue() {
		return blue;
	}

	public void setBlue(float b) {
		blue = b;
	}

}
