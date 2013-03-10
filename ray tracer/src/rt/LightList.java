package rt;

import java.util.ArrayList;

public class LightList {

	private ArrayList<Light> lightList;

	public LightList() {
		setLightList(new ArrayList<Light>());
	}

	public void add(Light light) {
		lightList.add(light);
	}

	public ArrayList<Light> getLightList() {
		return lightList;
	}

	public void setLightList(ArrayList<Light> lights) {
		lightList = lights;
	}
}