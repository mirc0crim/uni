package ch.unibe.yala;


public class GraphViewData {

	double valueX;
	double valueY;

	public GraphViewData(double valueX, double valueY) {
		super();
		this.valueX = valueX;
		this.valueY = valueY;
	}

	public float timeAverage(int begin, int end) {
		float average = (float) ((end + begin) / 2.0);
		return average;
	}

	public float speedAverage(int begin, int groupSize, float[] speeds) {
		float average = 0;
		for (int i = begin; i < begin + groupSize; i++)
			average = average + speeds[i];
		average = average / groupSize;
		return average;
	}

}
