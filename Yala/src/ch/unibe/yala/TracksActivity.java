package ch.unibe.yala;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.maps.GeoPoint;

public class TracksActivity extends ListActivity {

	static String[] values;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_tracks, values));

		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				DataLayer datLay = new DataLayer(getBaseContext());
				String points = datLay.getStats("point", values[position]);
				String times = datLay.getStats("time", values[position]);
				String alti = datLay.getStats("alti", values[position]);
				String pausePoints = datLay.getStats("pausePoint", values[position]);
				String pauseTimes = datLay.getStats("pauseTime", values[position]);
				FinishActivity.setPoints(convertGeoPointStringToArray(points));
				FinishActivity.setTimes(convertLongStringToArray(times));
				FinishActivity.setAltitude(convertDoubleStringToArray(alti));
				FinishActivity.setPausePoints(convertGeoPointStringToArray(pausePoints));
				FinishActivity.setPauseTimes(convertLongStringToArray(pauseTimes));
				FinishActivity.save = false;
				Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
				startActivity(intent);
			}
		});

	}

	public static void setValues(String[] v) {
		values = v;
	}

	public static Long[] convertLongStringToArray(String s) {
		if (s.length() < 1)
			return new Long[0];
		String[] arr = s.split(",");
		Long[] longs = new Long[arr.length];
		for (int i = 0; i < arr.length; i++)
			longs[i] = Long.parseLong(arr[i]);
		return longs;
	}

	public static Double[] convertDoubleStringToArray(String s) {
		if (s.length() < 1)
			return new Double[0];
		String[] arr = s.split(",");
		Double[] doubles = new Double[arr.length];
		for (int i = 0; i < arr.length; i++)
			doubles[i] = Double.parseDouble(arr[i]);
		return doubles;
	}

	public static GeoPoint[] convertGeoPointStringToArray(String s) {
		if (s.length() < 1)
			return new GeoPoint[0];
		String[] arr = s.split(",");
		GeoPoint[] geoPoints = new GeoPoint[arr.length];
		String[] point = new String[2];
		for (int i = 0; i < arr.length; i++) {
			point = arr[i].split(";");
			geoPoints[i] = new GeoPoint(Integer.parseInt(point[0]), Integer.parseInt(point[1]));
		}
		return geoPoints;
	}

}
