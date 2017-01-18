package com.example.android.decodeit;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

/**
 * Created with Android Studio
 * User: pulkitkumar190@gmail.com
 * Date: 17-01-2017
 * Time: 14:54
 */

public class WidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_data);
        remoteViews.setTextViewText(R.id.wtext,null);
    }
}
