package ch.unibe.yala;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MainActivity extends MapActivity implements LocationListener {

	LocationManager locationManager;
	Geocoder geocoder;
	TextView locationText;
	MapView map;
	MapController mapController;
	Drawable mapPin;
	List<Overlay> mapOverlays;
	MyItemOverlay itemizedoverlay;
	Boolean started;
	List<GeoPoint> myPoints;
	List<Date> myDates;
	AlertDialog.Builder builder;
	static GeoPoint[] points;
	static Date[] times;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		locationText = (TextView) findViewById(R.id.myText);
		map = (MapView) findViewById(R.id.map);
		map.setBuiltInZoomControls(true);

		mapController = map.getController();

		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		geocoder = new Geocoder(this);

		mapPin = getResources().getDrawable(R.drawable.androidmarker);
		itemizedoverlay = new MyItemOverlay(mapPin, this);
		mapOverlays = map.getOverlays();

		started = false;
		myPoints = new ArrayList<GeoPoint>();
		myPoints.clear();
		myDates = new ArrayList<Date>();
		myDates.clear();

		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					doReset();
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		};
		builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
		.setNegativeButton("No", dialogClickListener);
	}

	@Override
	public void onLocationChanged(Location location) {
		String text = String.format("%f / %f / %f", location.getLatitude(),
				location.getLongitude(), location.getAltitude());
		locationText.setText(text);

		if (!started)
			return;

		int latitude = (int) (location.getLatitude() * 1000000);
		int longitude = (int) (location.getLongitude() * 1000000);

		myPoints.add(new GeoPoint(latitude, longitude));
		myDates.add(new Date());

		GeoPoint point = new GeoPoint(latitude, longitude);
		OverlayItem overlayitem = new OverlayItem(point, "Position: "
				+ (itemizedoverlay.size() + 1), "Lat: " + location.getLatitude() + "\nLong: "
				+ location.getLongitude());
		itemizedoverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedoverlay);
		mapController.animateTo(point);
	}

	public void doReset() {
		map.getOverlays().clear();
		map.removeAllViews();
		itemizedoverlay.clear();
		mapOverlays.add(itemizedoverlay);
		started = false;
		myPoints.clear();
		myDates.clear();
	}

	public void start(View view) {
		started = true;
		mapController.setZoom(20);
		locationText.setText("Waiting for GPS signal");
	}

	public void reset(View view) {
		if (!started || itemizedoverlay == null)
			return;
		builder.show();
	}

	public void finish(View view) {
		started = false;
		points = new GeoPoint[myPoints.size()];
		points = myPoints.toArray(points);
		times = new Date[myDates.size()];
		times = myDates.toArray(times);
		Intent intent = new Intent(this, FinishActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		doReset();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
