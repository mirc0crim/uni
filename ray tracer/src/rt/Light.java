package rt;

import javax.vecmath.Vector4f;

public interface Light {

	Spectrum getCl(Vector4f hitPoint);

	Vector4f getL(Vector4f hitPoint);

}