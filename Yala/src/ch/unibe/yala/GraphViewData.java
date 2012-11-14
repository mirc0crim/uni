package ch.unibe.yala;


public class GraphViewData {

	private static int MAX_POINTS = 400; // number of maximum points

	double valueX;
	double valueY;

	public GraphViewData(double valueX, double valueY) {
		super();

		this.valueX = valueX;
		this.valueY = valueY;
	}

	public GraphViewData[] average(float[] speeds) {
		int measurements = speeds.length;
		int groupSize = (int) Math.floor(measurements / MAX_POINTS);
		int remainder = measurements % MAX_POINTS;
		if (remainder != 0)
			groupSize = groupSize + 1;
		int i = 0;
		int k = 0;
		remainder = measurements % groupSize;
		int noPoints = (int) Math.ceil((double) measurements / groupSize);
		GraphViewData[] values = new GraphViewData[noPoints];
		if (remainder == 0)
			while (i < measurements) {
				float ta = 5 * timeAverage(i, i + groupSize - 1);
				float sa = speedAverage(i, groupSize, speeds);
				values[k] = new GraphViewData(ta, sa);
				k++;
				i = i + groupSize;
			}
		else {
			while (i < measurements - groupSize) {
				float ta = 5 * timeAverage(i, i + groupSize - 1);
				float sa = speedAverage(i, groupSize, speeds);
				i = i + groupSize;
				values[k] = new GraphViewData(ta, sa);
				k++;
			}
			float ta = 5 * timeAverage(i, measurements - 1);
			float sa = speedAverage(i, measurements - i, speeds);
			values[k] = new GraphViewData(ta, sa);
			k++;
		}
		return values;
	}

	// average the time
	public float timeAverage(int begin, int end) {
		float average = (float) ((end + begin) / 2.0);
		return average;
	}

	// average the speed
	public float speedAverage(int begin, int groupSize, float[] speeds) {
		float average = 0;
		for (int i = begin; i < begin + groupSize; i++)
			average = average + speeds[i];
		average = average / groupSize;
		return average;
	}

}
