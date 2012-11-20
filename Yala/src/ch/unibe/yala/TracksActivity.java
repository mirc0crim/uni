package ch.unibe.yala;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.maps.GeoPoint;

public class TracksActivity extends Activity {

	static List<String> values;
	AlertDialog.Builder builder;
	int pos;
	ArrayAdapter<String> aAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tracks);

		ListView listView = (ListView) findViewById(R.id.list);
		aAdapter = new ArrayAdapter<String>(this, R.layout.activity_tracks, R.id.info, values);
		listView.setAdapter(aAdapter);
		listView.setTextFilterEnabled(true);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				DataLayer datLay = new DataLayer(getBaseContext());
				String points = datLay.getStats("point", position);
				String times = datLay.getStats("time", position);
				String alti = datLay.getStats("alti", position);
				String pausePoints = datLay.getStats("pausePoint", position);
				String pauseTimes = datLay.getStats("pauseTime", position);
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

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				pos = position;
				builder.show();
				return true;
			}
		});

		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					doDelete();
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		};
		builder = new AlertDialog.Builder(this);
		builder.setMessage("Delete Track?").setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener);
	}

	private void doDelete() {
		DataLayer datLay = new DataLayer(getBaseContext());
		datLay.deleteRun(pos);
		aAdapter.remove(values.get(pos));
		aAdapter.notifyDataSetChanged();
	}

	public static void setValues(List<String> v) {
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
