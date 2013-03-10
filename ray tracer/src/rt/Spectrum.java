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
		this.setRed(red + spec.red);
		this.setGreen(green + spec.green);
		this.setBlue(blue + spec.blue);
	}

	public void clampMax(float f) {
		this.setRed(Math.min(getRed(), f));
		this.setGreen(Math.min(getGreen(), f));
		this.setBlue(Math.min(getBlue(), f));
	}

	public void clampMin(float f) {
		this.setRed(Math.max(getRed(), f));
		this.setGreen(Math.max(getGreen(), f));
		this.setBlue(Math.max(getBlue(), f));
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
