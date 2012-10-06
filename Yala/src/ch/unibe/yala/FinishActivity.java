package ch.unibe.yala;

import java.util.Random;

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

		GraphViewData[] gvd = new GraphViewData[100];
		for (int i = 0; i < 100; i++){
			Random ran = new Random();
			gvd[i] = new GraphViewData(i, ran.nextDouble());
		}
		GraphViewSeries exampleSeries = new GraphViewSeries(gvd);
		GraphView graphView = new LineGraphView(this, "Speed / Time");
		graphView.addSeries(exampleSeries);
		graphView.setHorizontalLabels(new String[] { "Start", "Middle", "End" });
		graphView.setVerticalLabels(new String[] { "fast", "average", "slow" });
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
}
