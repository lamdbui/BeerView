package app.com.lamdbui.android.beerview.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;

import app.com.lamdbui.android.beerview.BeerNavigationActivity;
import app.com.lamdbui.android.beerview.R;
import app.com.lamdbui.android.beerview.data.BreweryContract;

/**
 * Created by lamdbui on 6/24/17.
 */

public class BeerMapperWidgetProvider extends AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        updateData(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        updateData(context);
    }

    public void updateData(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, BeerMapperWidgetProvider.class));

        Cursor mBreweryData = null;
        Cursor mBeerData = null;
        mBreweryData = context.getContentResolver().query(
                BreweryContract.BreweryTable.CONTENT_URI,
                null,
                null,
                null,
                null);
        mBeerData = context.getContentResolver().query(
                BreweryContract.BeerTable.CONTENT_URI,
                null,
                null,
                null,
                null);

        for(int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_favorites_counter);

            int favoriteBreweryCount = mBreweryData.getCount();
            int favoriteBeerCount = mBeerData.getCount();
            if(favoriteBeerCount > 0)
                views.setTextViewText(R.id.widget_favorite_breweries_count, Integer.toString(favoriteBreweryCount));
            else
                views.setTextViewText(R.id.widget_favorite_breweries_count, context.getString(R.string.info_none));
            if(favoriteBeerCount > 0)
                views.setTextViewText(R.id.widget_favorite_beers_count, Integer.toString(favoriteBeerCount));
            else
                views.setTextViewText(R.id.widget_favorite_beers_count, context.getString(R.string.info_none));

            // Launch our main Activity on click
            Intent intent = new Intent(context, BeerNavigationActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        mBreweryData.close();
        mBeerData.close();
    }
}
