package ch.unibe.yala;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.location.Location;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

public class FinishActivity extends MapActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finish);

		List<Double> myDistances = new ArrayList<Double>();
		List<Double> myTimes = new ArrayList<Double>();
		List<Double> myHeight = new ArrayList<Double>();

		Double[] distances;
		Double[] times;
		Double[] speeds;
		Double[] height;

		GraphViewData[] gvd;
		GraphViewData[] gvdA;

		String[] vLabelSpeed = new String[3];
		String[] vLabelAlti = new String[3];

		TextView stats = (TextView) findViewById(R.id.stats);

		if (MainActivity.points.length > 1) {
			for (int i = 0; i < MainActivity.points.length - 1; i++) {
				float[] result = new float[3];
				Location.distanceBetween(MainActivity.points[i].getLatitudeE6() / 1E6,
						MainActivity.points[i].getLongitudeE6() / 1E6,
						MainActivity.points[i + 1].getLatitudeE6() / 1E6,
						MainActivity.points[i + 1].getLongitudeE6() / 1E6, result);
				myDistances.add(Double.valueOf(result[0]));
				double timeDiff = MainActivity.times[i + 1] - MainActivity.times[i];
				myTimes.add(timeDiff / 1000);
				myHeight.add(MainActivity.alti[i]);
			}
			myHeight.add(MainActivity.alti[MainActivity.points.length - 1]);

			times = new Double[myTimes.size()];
			times = myTimes.toArray(times);
			distances = new Double[myDistances.size()];
			distances = myDistances.toArray(distances);
			height = new Double[myHeight.size()];
			height = myHeight.toArray(height);

			gvd = new GraphViewData[distances.length];
			gvdA = new GraphViewData[height.length];
			speeds = new Double[distances.length];
			double thatTime = 0;
			for (int i = 0; i < distances.length; i++) {
				thatTime += times[i];
				speeds[i] = distances[i] / times[i];
				gvd[i] = new GraphViewData(thatTime, speeds[i]);
			}
			thatTime = 0;
			for (int i = 0; i < height.length; i++) {
				if (i > 0)
					thatTime += times[i - 1];
				gvdA[i] = new GraphViewData(thatTime, height[i]);
			}
			vLabelSpeed[0] = Math.round(maxValue(speeds)) + "m/s";
			vLabelSpeed[1] = Math.round(avgValue(speeds)) + "m/s";
			vLabelSpeed[2] = Math.round(minValue(speeds)) + "m/s";
			vLabelAlti[0] = Math.round(maxValue(height)) + "m";
			vLabelAlti[1] = Math.round(avgValue(height)) + "m";
			vLabelAlti[2] = Math.round(minValue(height)) + "m";

			long sec = 0;
			long min = 0;
			long hour = 0;
			sec = (MainActivity.times[MainActivity.times.length - 1] - MainActivity.times[0]) / 1000;
			if (sec / 60 > 1) {
				min = (long) Math.floor(sec / 60);
				sec = sec - min * 60;
				if (min / 60 > 1) {
					hour = (long) Math.floor(min / 60);
					min = min - hour * 60;
				}
			}
			double elev = Math.round(maxValue(height)) - Math.round(minValue(height));
			double dist = 0;
			for (Double distance : distances)
				dist += distance;
			stats.setText("Duration: " + hour + ":" + min + ":" + sec + " (h:m:s)\n");
			stats.append("Elevation: " + elev + " m\n");
			stats.append("Average Speed: " + Math.round(avgValue(speeds) * 3.6) + " km/h ("
					+ Math.round(avgValue(speeds)) + " m/s)\n");
			stats.append("Distance: " + (int) dist + " m\n");
		} else {
			gvd = new GraphViewData[] { new GraphViewData(1, 2.0d), new GraphViewData(2, 3.0d) };
			gvdA = new GraphViewData[] { new GraphViewData(1, 3.0d), new GraphViewData(2, 2.0d) };
			vLabelSpeed = new String[] { "fast", "average", "slow" };
			vLabelAlti = new String[] { "high", "average", "low" };
			stats.setText("Duration: 0:0:0 (h:m:s)\n");
			stats.append("Elevation: 0 m\n");
			stats.append("Average Speed: 0 km/h (0 m/s)\n");
			stats.append("Distance: 0 m");
		}

		GraphViewSeries seriesSpeed = new GraphViewSeries(gvd);
		GraphView graphSpeed = new LineGraphView(this, "Speed / Time");
		graphSpeed.addSeries(seriesSpeed);
		graphSpeed.setHorizontalLabels(new String[] { "Start", "Middle", "End" });
		graphSpeed.setVerticalLabels(vLabelSpeed);
		LinearLayout layoutSpeed = (LinearLayout) findViewById(R.id.graphSpeed);
		layoutSpeed.addView(graphSpeed);

		GraphViewSeries seriesAlti = new GraphViewSeries(gvdA);
		GraphView graphAlti = new LineGraphView(this, "Altitude / Time");
		graphAlti.addSeries(seriesAlti);
		graphAlti.setHorizontalLabels(new String[] { "Start", "Middle", "End" });
		graphAlti.setVerticalLabels(vLabelAlti);
		LinearLayout layoutAlti = (LinearLayout) findViewById(R.id.graphAlti);
		layoutAlti.addView(graphAlti);

		MapView routeMap = (MapView) findViewById(R.id.route);
		MapController mMapController = routeMap.getController();
		mMapController.setZoom(16);
		if (MainActivity.points.length > 0) {
			mMapController.setCenter(MainActivity.points[0]);
			MyMapOverlay mapOvlay = new MyMapOverlay(MainActivity.points);
			routeMap.getOverlays().add(mapOvlay);
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	private double minValue(Double[] myArray) {
		List<Double> b = Arrays.asList(myArray);
		return Collections.min(b);
	}

	private double maxValue(Double[] myArray) {
		List<Double> b = Arrays.asList(myArray);
		return Collections.max(b);
	}

	private double avgValue(Double[] myArray) {
		return (maxValue(myArray) + minValue(myArray)) / 2;
	}

	@Override
	public void onBackPressed() {
		finish();
	}

}
