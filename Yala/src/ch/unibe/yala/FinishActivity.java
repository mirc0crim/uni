package ch.unibe.yala;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;

public class FinishActivity extends MapActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finish);

		MapView routeMap = (MapView) findViewById(R.id.route);
		routeMap.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(final View v, final MotionEvent motionEvent) {
				if (v.getId() == R.id.route) {
					v.getParent().requestDisallowInterceptTouchEvent(true);
					switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
					case MotionEvent.ACTION_UP:
						v.getParent().requestDisallowInterceptTouchEvent(false);
						break;
					}
				}
				return false;
			}
		});

		GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
				new GraphViewData(1, 2.0), new GraphViewData(2, 1.5), new GraphViewData(3, 2.5),
				new GraphViewData(4, 4.0), new GraphViewData(5, 1), new GraphViewData(6, 1.5),
				new GraphViewData(7, 3.0), new GraphViewData(8, 1.0), new GraphViewData(9, 3.5),
				new GraphViewData(10, 2.0), new GraphViewData(11, 3.5), new GraphViewData(12, 2.0),
				new GraphViewData(13, 1.0) });
		GraphView graphView = new BarGraphView(this, "Speed / Time");
		graphView.addSeries(exampleSeries);
		graphView.setHorizontalLabels(new String[] { "Start", "Middle", "End" });
		graphView.setVerticalLabels(new String[] { "fast", "average", "slow" });
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph);
		layout.addView(graphView);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
