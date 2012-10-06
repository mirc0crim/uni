package ch.unibe.yala;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Projection;

public class MyMapOverlay extends com.google.android.maps.Overlay {

	private GeoPoint[] myPoints;

	protected MyMapOverlay(GeoPoint[] points) {
		myPoints = new GeoPoint[points.length];
		myPoints = points;
	}

	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
		super.draw(canvas, mapView, shadow);
		Paint paint;
		paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(4);
		Projection projection = mapView.getProjection();
		for (int i = 0; i < myPoints.length - 1; i++) {
			Point pt1 = new Point();
			Point pt2 = new Point();
			projection.toPixels(myPoints[i], pt1);
			projection.toPixels(myPoints[i + 1], pt2);
			canvas.drawLine(pt1.x, pt1.y, pt2.x, pt2.y, paint);
		}
		return true;
	}
}