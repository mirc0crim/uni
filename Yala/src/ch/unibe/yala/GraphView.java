package ch.unibe.yala;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.view.View;

public class GraphView extends View {

	private final Paint paint = new Paint();
	private final Paint paintBackground = new Paint();
	private GraphViewData[] values;
	private String[] horlabels;
	private String[] verlabels;
	private String title;
	private float speed;

	public GraphView(Context context, GraphViewData[] values, String title, String[] horlabels, String[] verlabels, float speed) {
		super(context);
		if (values == null)
			values = new GraphViewData[0];
		else
			this.values = values;
		if (title == null)
			title = "";
		else
			this.title = title;
		if (horlabels == null)
			this.horlabels = new String[0];
		else
			this.horlabels = horlabels;
		if (verlabels == null)
			this.verlabels = new String[0];
		else
			this.verlabels = verlabels;

		this.speed = speed;
		paintBackground.setARGB(255, 10, 20, 30);
		paintBackground.setStrokeCap(Paint.Cap.ROUND);
		paintBackground.setStrokeWidth(4);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		paint.setStrokeWidth(0);

		float border = 25;
		float horstart = border * 3;
		float height = getHeight();
		float width = getWidth() - 1;
		float graphheight = height - 2 * border ;
		float graphwidth = width - (horstart + border);

		// vertical labels + lines
		paint.setTextAlign(Align.LEFT);
		int vers = verlabels.length - 1;
		for (int i = 0; i < verlabels.length; i++) {
			paint.setColor(Color.DKGRAY);
			float y = graphheight / vers * i + border;
			canvas.drawLine(horstart, y, width - border, y, paint);
			paint.setARGB(255, 23, 123, 189);//Color(#177bbd);
			paint.setTextSize(16);
			canvas.drawText(verlabels[vers - i], 0, y, paint);
		}

		// horizontal labels + lines
		int hors = horlabels.length - 1;
		for (int i = 0; i < horlabels.length; i++) {
			paint.setColor(Color.DKGRAY);
			float x = graphwidth / hors * i + horstart;
			canvas.drawLine(x, graphheight+border, x, border, paint);
			paint.setTextAlign(Align.CENTER);
			if (i==horlabels.length-1)
				paint.setTextAlign(Align.RIGHT);
			if (i==0)
				paint.setTextAlign(Align.LEFT);
			canvas.drawLine(width-border, graphheight+border, width-border, border, paint);
			paint.setARGB(255, 23, 123, 189);//Color(#177bbd);
			paint.setTextSize(18);
			canvas.drawText(horlabels[i], x,  height, paint);
		}

		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(22);
		canvas.drawText(title, graphwidth / 2 + horstart, border - 4, paint);
		paint.setARGB(255, 0, 119, 204);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(3);

		//draw connecting lines
		float startX = 0;
		float startY = 0;
		float endX = 0;
		float endY = 0;

		double unitX = graphwidth / values[values.length-1].valueX;
		double unitY = graphheight / speed;
		for (int i = 0; i < values.length-1; i++){
			startX = (float) (horstart + values[i].valueX * unitX);
			endX = (float) (horstart + values[i+1].valueX * unitX);
			startY = (float) (graphheight + border - values[i].valueY * unitY);
			endY = (float) (graphheight + border - values[i + 1].valueY * unitY);
			canvas.drawLine(startX, startY, endX, endY, paint);
		}
	}
}