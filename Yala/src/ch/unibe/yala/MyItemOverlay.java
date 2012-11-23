package ch.unibe.yala;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MyItemOverlay extends ItemizedOverlay<OverlayItem> {
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;

	public MyItemOverlay(Drawable marker) {
		super(boundCenterBottom(marker));
		populate();
	}

	public MyItemOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}

	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}

	public void clear() {
		mOverlays.clear();
		setLastFocusedIndex(-1);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	@Override
	protected boolean onTap(int index) {
		OverlayItem item = mOverlays.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(item.getTitle());
		final String snippet = item.getSnippet();
		if (snippet.toString().length() > 0) {
			dialog.setMessage(snippet);
			dialog.setPositiveButton("More Info", new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					String[] snippetArray = snippet.replace(" ", "+").split("\n");
					Uri uri = Uri.parse("https://www.google.com/search?q=" + snippetArray[0] + "+"
							+ snippetArray[1]);
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					mContext.startActivity(intent);
				}
			});
		}
		dialog.show();
		return true;
	}

}
