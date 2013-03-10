package rt;

public class RefractiveMaterial extends Material {

	private float refractiveIndex;

	public RefractiveMaterial(float refractIndex) {
		refractiveIndex = refractIndex;
	}

	public float getRefractiveIndex() {
		return refractiveIndex;
	}

	public void setRefractiveIndex(float refractIndex) {
		refractiveIndex = refractIndex;
	}

}