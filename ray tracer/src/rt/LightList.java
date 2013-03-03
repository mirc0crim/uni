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

	/**
	 * @return the lightList
	 */
	public ArrayList<Light> getLightList() {
		return lightList;
	}

	/**
	 * @param lightList
	 *            the lightList to set
	 */
	public void setLightList(ArrayList<Light> lightList) {
		this.lightList = lightList;
	}
}