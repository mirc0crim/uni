package rt;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

public class Boundingbox {

	Vector3f bottomFrontLeft;
	Vector3f topBackRight;

	public Boundingbox(Vector3f bfl, Vector3f tbr) {
		bottomFrontLeft = bfl;
		topBackRight = tbr;
	}

	public static Boundingbox combineBox(ArrayList<Boundingbox> bb) {
		ArrayList<Float> bflx = new ArrayList<Float>();
		ArrayList<Float> bfly = new ArrayList<Float>();
		ArrayList<Float> bflz = new ArrayList<Float>();
		ArrayList<Float> tbrx = new ArrayList<Float>();
		ArrayList<Float> tbry = new ArrayList<Float>();
		ArrayList<Float> tbrz = new ArrayList<Float>();
		for (Boundingbox b : bb) {
			bflx.add(b.bottomFrontLeft.x);
			bfly.add(b.bottomFrontLeft.x);
			bflz.add(b.bottomFrontLeft.x);
			tbrx.add(b.bottomFrontLeft.x);
			tbry.add(b.bottomFrontLeft.x);
			tbrz.add(b.bottomFrontLeft.x);
		}
		float minX = getMin(bflx.toArray(new Float[bflx.size()]));
		float minY = getMin(bfly.toArray(new Float[bfly.size()]));
		float minZ = getMin(bflz.toArray(new Float[bflz.size()]));
		float maxX = getMax(tbrx.toArray(new Float[tbrx.size()]));
		float maxY = getMax(tbry.toArray(new Float[tbry.size()]));
		float maxZ = getMax(tbrz.toArray(new Float[tbrz.size()]));

		Vector3f bfl = new Vector3f(minX, minY, minZ);
		Vector3f tbr = new Vector3f(maxX, maxY, maxZ);
		return new Boundingbox(bfl, tbr);
	}

	private static float getMin(Float[] values) {
		float minVal = Float.MAX_VALUE;
		for (float v : values)
			if (v < minVal)
				minVal = v;
		return minVal;
	}

	private static float getMax(Float[] values) {
		float maxVal = Float.MIN_VALUE;
		for (float v : values)
			if (v > maxVal)
				maxVal = v;
		return maxVal;
	}

}
