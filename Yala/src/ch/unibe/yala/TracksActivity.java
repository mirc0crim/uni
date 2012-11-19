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

				DataLayer d = new DataLayer(getBaseContext());
				String points = d.getStats("point", values[position]);
				String times = d.getStats("time", values[position]);
				String alti = d.getStats("alti", values[position]);
				String pausePoints = d.getStats("pausePoint", values[position]);
				String pauseTimes = d.getStats("pauseTime", values[position]);
				FinishActivity.setPoints(convertGeoPointStringToArray(points));
				FinishActivity.setTimes(convertLongStringToArray(times));
				FinishActivity.setAltitude(convertDoubleStringToArray(alti));
				FinishActivity.setPausePoints(convertGeoPointStringToArray(pausePoints));
				FinishActivity.setPauseTimes(convertLongStringToArray(pauseTimes));
				Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
				startActivity(intent);
			}
		});

	}

	public static void setValues(String[] v) {
		values = v;
	}

	public static Long[] convertLongStringToArray(String str) {
		if (str.length() < 1)
			return new Long[0];
		String[] arr = str.split(",");
		Long[] r = new Long[arr.length];
		for (int i = 0; i < arr.length; i++)
			r[i] = Long.parseLong(arr[i]);
		return r;
	}

	public static Double[] convertDoubleStringToArray(String str) {
		if (str.length() < 1)
			return new Double[0];
		String[] arr = str.split(",");
		Double[] r = new Double[arr.length];
		for (int i = 0; i < arr.length; i++)
			r[i] = Double.parseDouble(arr[i]);
		return r;
	}

	public static GeoPoint[] convertGeoPointStringToArray(String str) {
		if (str.length() < 1)
			return new GeoPoint[0];
		String[] arr = str.split(",");
		GeoPoint[] r = new GeoPoint[arr.length];
		String[] pt = new String[2];
		for (int i = 0; i < arr.length; i++) {
			pt = arr[i].split(";");
			r[i] = new GeoPoint(Integer.parseInt(pt[0]), Integer.parseInt(pt[1]));
		}
		return r;
	}

}
