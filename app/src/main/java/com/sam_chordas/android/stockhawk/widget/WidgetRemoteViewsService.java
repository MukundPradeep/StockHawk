package com.sam_chordas.android.stockhawk.widget;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

/**
 * Created by Admin on 01-04-2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {
                // Nothing to do
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                // This method is called by the app hosting the widget (e.g., the launcher)
                // However, our ContentProvider is not exported so it doesn't have access to the
                // data. Therefore we need to clear (and finally restore) the calling identity so
                // that calls use our process and permission
                final long identityToken = Binder.clearCallingIdentity();
                //String location = Utility.getPreferredLocation(DetailWidgetRemoteViewsService.this);
//                Uri weatherForLocationUri = WeatherContract.WeatherEntry
//                        .buildWeatherLocationWithStartDate(location, System.currentTimeMillis());
                final String[] projection = new String[]{QuoteColumns.SYMBOL, QuoteColumns.PERCENT_CHANGE};
                data = getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI, projection,
                        QuoteColumns.ISCURRENT + "= ?", new String[]{"1"}, null);
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_collection_item);

                String symbol = data.getString(0);
                String change = data.getString(1);

                views.setTextViewText(R.id.stock_symbol, symbol);
                views.setTextViewText(R.id.change, change);

                return views;
            }

            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            private void setRemoteContentDescription(RemoteViews views, String description) {
                views.setContentDescription(R.id.widget, description);
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_collection_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }


            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
