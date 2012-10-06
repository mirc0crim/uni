package ch.unibe.yala;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.location.Location;
import android.os.Bundle;
import android.widget.LinearLayout;

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

		Double[] distances;
		Double[] times;
		Double[] speeds;

		GraphViewData[] gvd;

		String[] vLabel = new String[3];

		if (MainActivity.points.length > 1) {
			for (int i = 0; i < MainActivity.points.length - 1; i++) {
				float[] result = new float[3];
				Location.distanceBetween(MainActivity.points[i].getLatitudeE6() / 1E6,
						MainActivity.points[i].getLongitudeE6() / 1E6,
						MainActivity.points[i + 1].getLatitudeE6() / 1E6,
						MainActivity.points[i + 1].getLongitudeE6() / 1E6, result);
				myDistances.add(Double.valueOf(result[0]));
				double timeDiff = MainActivity.times[i + 1].getTime() - MainActivity.times[i].getTime();
				myTimes.add(timeDiff / 1000);
			}

			times = new Double[myTimes.size()];
			times = myTimes.toArray(times);
			distances = new Double[myDistances.size()];
			distances = myDistances.toArray(distances);

			gvd = new GraphViewData[distances.length];
			speeds = new Double[distances.length];
			double thatTime = 0;
			for (int i = 0; i < distances.length; i++) {
				thatTime += times[i];
				speeds[i] = distances[i] / times[i];
				gvd[i] = new GraphViewData(thatTime, speeds[i]);
			}
			vLabel[0] = Math.round(maxValue(speeds)) + "m/s";
			vLabel[1] = Math.round(avgValue(speeds)) + "m/s";
			vLabel[2] = Math.round(minValue(speeds)) + "m/s";
		} else {
			gvd = new GraphViewData[] { new GraphViewData(1, 2.0d), new GraphViewData(1, 3.0d) };
			vLabel = new String[] { "fast", "average", "slow" };
		}

		GraphViewSeries exampleSeries = new GraphViewSeries(gvd);
		GraphView graphView = new LineGraphView(this, "Speed / Time");
		graphView.addSeries(exampleSeries);
		graphView.setHorizontalLabels(new String[] { "Start", "Middle", "End" });
		graphView.setVerticalLabels(vLabel);
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph);
		layout.addView(graphView);

		MapView routeMap = (MapView) findViewById(R.id.route);
		MapController mMapController;
		routeMap.setBuiltInZoomControls(true);
		mMapController = routeMap.getController();
		mMapController.setZoom(18);
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
		List b = Arrays.asList(myArray);
		return Collections.min(b);
	}

	private double maxValue(Double[] myArray) {
		List b = Arrays.asList(myArray);
		return Collections.max(b);
	}

	private double avgValue(Double[] myArray) {
		return (maxValue(myArray) + minValue(myArray)) / 2;
	}

}
