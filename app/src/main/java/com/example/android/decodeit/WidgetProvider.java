package com.example.android.decodeit;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.database.Cursor;
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
        //remoteViews.setTextViewText(R.id.wtext,null);
        String data = "";
        String mProjection [] = {
          WordContract.WordEntry.COLUMN_WORD_NAME
        };

        Cursor mCursor;

        mCursor = context.getContentResolver().query(
                WordContract.WordEntry.CONTENT_URI,
                mProjection,
                null,
                null,
                null
                );

        // Determine the column index of the column named "word"

        if (null == mCursor){
            //Some error
        } else if (mCursor.getCount() < 1){
            //Some error
        } else {
            int index = mCursor.getColumnIndex(WordContract.WordEntry.COLUMN_WORD_NAME);
            mCursor.moveToLast();
            data = mCursor.getString(index);
            remoteViews.setTextViewText(R.id.wtext1,data);
            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        }
    }
}
