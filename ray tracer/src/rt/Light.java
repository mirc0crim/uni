package rt;

import javax.vecmath.Vector3f;

public interface Light {

	Spectrum getCl(Vector3f hitPoint);

	Vector3f getL(Vector3f hitPoint);

}