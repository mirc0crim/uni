package ch.unibe.yala;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MainActivity extends MapActivity implements LocationListener {

	LocationManager locationManager;
	LocationListener locListener;
	Geocoder geocoder;
	TextView locationText;
	MapView map;
	MapController mapController;
	Drawable startPin;
	List<Overlay> mapOverlays;
	MyItemOverlay startOverlay;
	Boolean started;
	List<GeoPoint> myPoints;
	List<Long> myTimes;
	List<Double> myAlti;
	AlertDialog.Builder builder;
	static GeoPoint[] points;
	static Long[] times;
	static Double[] alti;
	Boolean first;
	GeoPoint lastPoint;
	Double lastAlti;
	Button startButton;
	Button finishButton;
	NotificationManager mNotificationManager;
	Boolean calledFinish;
	Long pauseTime;
	Long pauseBegin;
	int startState;
	Boolean paused;
	MyMapOverlay seg;
	GeoPoint startPoint;
	Drawable endPin;
	MyItemOverlay endOverlay;
	List<GeoPoint> myPausePoints;
	static GeoPoint[] pausePoints;
	List<Long> myPauseTimes;
	static Long[] pauseTimes;
	Long lastTime;
	final long minTime = 0;
	final float minDistance = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

		locationText = (TextView) findViewById(R.id.myText);
		map = (MapView) findViewById(R.id.map);
		map.setSatellite(false);

		startButton = (Button) findViewById(R.id.buttonStart);
		finishButton = (Button) findViewById(R.id.buttonFinish);
		finishButton.setEnabled(false);

		lastPoint = new GeoPoint(46953700, 7439700);
		startPoint = new GeoPoint(46953700, 7439700);
		lastAlti = 0d;
		startState = 0;
		pauseTime = 0l;
		lastTime = new Date().getTime();

		locListener = this;

		mapController = map.getController();
		mapController.setZoom(10);
		mapController.animateTo(lastPoint);
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance,
				locListener);
		geocoder = new Geocoder(this);
		startPin = getResources().getDrawable(R.drawable.startpin);
		endPin = getResources().getDrawable(R.drawable.endpin);
		startOverlay = new MyItemOverlay(startPin, this);
		endOverlay = new MyItemOverlay(endPin, this);
		mapOverlays = map.getOverlays();

		started = false;
		paused = false;
		first = true;
		calledFinish = false;
		myPoints = new ArrayList<GeoPoint>();
		myTimes = new ArrayList<Long>();
		myAlti = new ArrayList<Double>();
		myPausePoints = new ArrayList<GeoPoint>();
		myPauseTimes = new ArrayList<Long>();

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
		builder.setMessage("Reset and Delete Track?").setPositiveButton("Yes", dialogClickListener)
		.setNegativeButton("No", dialogClickListener);
	}

	@Override
	public void onLocationChanged(Location location) {

		if (paused)
			return;

		int latitude = (int) (location.getLatitude() * 1000000);
		int longitude = (int) (location.getLongitude() * 1000000);

		String text = (float) location.getLatitude() + " / " + (float) location.getLongitude()
				+ " / " + (int) location.getAltitude();
		locationText.setText(text);

		lastPoint = new GeoPoint(latitude, longitude);
		GeoPoint point = new GeoPoint(latitude, longitude);
		mapController.animateTo(point);

		if (!started) {
			mapController.setZoom(18);
			return;
		}

		if (new Date().getTime() - lastTime < 2500)
			return;
		lastTime = new Date().getTime();

		myPoints.add(new GeoPoint(latitude, longitude));
		myTimes.add(new Date().getTime() - pauseTime);
		if (location.getAltitude() > 1) {
			myAlti.add(location.getAltitude());
			lastAlti = location.getAltitude();
		} else
			myAlti.add(lastAlti);

		if (first) {
			startPoint = point;
			OverlayItem startItem = new OverlayItem(startPoint, "Start", "");
			startOverlay.addOverlay(startItem);
			mapOverlays.add(startOverlay);
			first = false;
		}

		if (myPoints.size() > 2)
			finishButton.setEnabled(true);

		if (myPoints.size() > 1) {
			map.getOverlays().clear();
			map.removeAllViews();
			startOverlay.clear();
			endOverlay.clear();
			GeoPoint[] segments = new GeoPoint[myPoints.size()];
			segments = myPoints.toArray(segments);
			seg = new MyMapOverlay(segments);
			map.getOverlays().add(seg);
			OverlayItem startItem = new OverlayItem(startPoint, "Start", "");
			startOverlay.addOverlay(startItem);
			mapOverlays.add(startOverlay);
			OverlayItem endItem = new OverlayItem(point, "Current Location", "");
			endOverlay.addOverlay(endItem);
			mapOverlays.add(endOverlay);
		}
		if (myPausePoints.size() > 0) {
			Drawable pausePin = getResources().getDrawable(R.drawable.pausepin);
			MyItemOverlay pauseOverlay = new MyItemOverlay(pausePin, this);
			for (int i = 0; i < myPausePoints.size(); i++) {
				OverlayItem pauseItem = new OverlayItem(myPausePoints.get(i), "Paused", "");
				pauseOverlay.addOverlay(pauseItem);
				map.getOverlays().add(pauseOverlay);
			}
		}

	}

	public void doReset() {
		map.getOverlays().clear();
		map.removeAllViews();
		started = false;
		paused = false;
		first = true;
		finishButton.setEnabled(false);
		startButton.setText("Start");
		startState = 0;
		myPoints.clear();
		myTimes.clear();
		myAlti.clear();
		myPausePoints.clear();
		myPauseTimes.clear();
	}

	public void start(View view) {
		switch (startState) {
		case 0:
			startButton.setText("Pause");
			startState = 1;
			locationText.setText("Waiting for GPS signal");
			lastTime = new Date().getTime();
			break;
		case 1:
			startButton.setText("Resume");
			pauseBegin = new Date().getTime();
			paused = true;
			startState = 2;
			locationText.setText("Paused");
			break;
		case 2:
			startButton.setText("Pause");
			pauseTime += new Date().getTime() - pauseBegin;
			paused = false;
			myPauseTimes.add(pauseTime);
			myPausePoints.add(lastPoint);
			startState = 1;
			locationText.setText("Resumed");
			break;
		default:
			break;
		}
		calledFinish = false;
		started = true;
	}

	public void finish(View view) {
		calledFinish = true;
		started = false;
		paused = false;
		points = new GeoPoint[myPoints.size()];
		points = myPoints.toArray(points);
		FinishActivity.setPoints(points);
		times = new Long[myTimes.size()];
		times = myTimes.toArray(times);
		FinishActivity.setTimes(times);
		alti = new Double[myAlti.size()];
		alti = myAlti.toArray(alti);
		FinishActivity.setAltitude(alti);
		pausePoints = new GeoPoint[myPausePoints.size()];
		pausePoints = myPausePoints.toArray(pausePoints);
		FinishActivity.setPausePoints(pausePoints);
		pauseTimes = new Long[myPauseTimes.size()];
		pauseTimes = myPauseTimes.toArray(pauseTimes);
		FinishActivity.setPauseTimes(pauseTimes);
		FinishActivity.save = true;
		if (points.length > 0)
			lastPoint = points[points.length - 1];
		doReset();
		locationManager.removeUpdates(locListener);
		removeNotification();
		Intent intent = new Intent(this, FinishActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		calledFinish = false;
		mapController.animateTo(lastPoint);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance,
				locListener);
		removeNotification();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			locationManager.removeUpdates(locListener);
			removeNotification();
		} else
			showRunningNotification();
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

	@Override
	protected void onStop() {
		super.onStop();
		if (isFinishing()) {
			locationManager.removeUpdates(locListener);
			removeNotification();
		} else
			showRunningNotification();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		locationManager.removeUpdates(locListener);
		removeNotification();
		finish();
	}

	private void showRunningNotification() {
		if (calledFinish)
			return;
		Notification noti = new Notification(R.drawable.ic_launcher, "Use back key to end Yala",
				System.currentTimeMillis());
		Intent notifyIntent = new Intent(this, MainActivity.class);
		notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notifyIntent,
				Notification.FLAG_ONGOING_EVENT);
		noti.flags = Notification.FLAG_ONGOING_EVENT + Notification.FLAG_AUTO_CANCEL;
		noti.setLatestEventInfo(this, "Yala", "Yala is still running", contentIntent);
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNotificationManager.notify(1, noti);
	}

	public void removeNotification() {
		if (mNotificationManager != null)
			mNotificationManager.cancel(1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.reset:
			if (!started || startOverlay == null) {
				Toast.makeText(this, "No recorded Data", Toast.LENGTH_LONG).show();
				break;
			}
			builder.show();
			break;
		case R.id.load:
			DataLayer datLay = new DataLayer(getBaseContext());
			String[] dates = convertStringToArray(datLay.getAllDates());
			if (dates[0] == "1") {
				Toast.makeText(this, "No recent Tracks", Toast.LENGTH_LONG).show();
				break;
			}
			List<String> lSt = new LinkedList<String>(Arrays.asList(dates));
			TracksActivity.setValues(lSt);
			calledFinish = true;
			locationManager.removeUpdates(locListener);
			removeNotification();
			Intent intent = new Intent(this, TracksActivity.class);
			startActivity(intent);
			break;
		case R.id.share:
			Intent intentShare = new Intent(Intent.ACTION_SEND);
			intentShare.setType("text/plain");
			intentShare.putExtra(Intent.EXTRA_TEXT, "Track, measure, and improve your fitness.");
			startActivity(Intent.createChooser(intentShare, "Share using"));
			break;
		case R.id.support:
			Intent intSupp = new Intent(Intent.ACTION_SEND);
			intSupp.setType("message/rfc822");
			intSupp.putExtra(Intent.EXTRA_EMAIL, new String[] { "mircokocher@gmail.com" });
			intSupp.putExtra(Intent.EXTRA_SUBJECT, "Request for support for Yala");
			String sText = collectInfos();
			intSupp.putExtra(Intent.EXTRA_TEXT, sText);
			startActivity(Intent.createChooser(intSupp, "Send mail..."));
			break;
		default:
			break;
		}
		return true;
	}

	private String collectInfos() {
		String text = "I have a question about...\n\n\n\n\n\n\nYala\n";
		try {
			PackageInfo pI = getPackageManager().getPackageInfo(getPackageName(), 0);
			text += "v: " + pI.versionName + " (" + pI.versionCode + ")\n";
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		text += "Device: " + android.os.Build.MODEL + " (" + android.os.Build.DEVICE + ") SDK"
				+ android.os.Build.VERSION.SDK_INT;
		return text;
	}

	public static String[] convertStringToArray(String s) {
		String[] arr = s.split(",");
		return arr;
	}
}
