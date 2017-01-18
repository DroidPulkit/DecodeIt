package com.example.android.decodeit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with Android Studio
 * User: pulkitkumar190@gmail.com
 * Date: 17-01-2017
 * Time: 14:33
 */

public class WordDbHelper extends SQLiteOpenHelper {

    private static final String NAME ="History.db";
    private static final int VERSION = 1;

    public WordDbHelper(Context context){
        super(context, NAME, null, VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL_CREATE_HISTORY_TABLE = "CREATE TABLE "
                + WordContract.WordEntry.TABLE_NAME
                + "( " + WordContract.WordEntry._ID
                + " INTEGER AUTOINCREAMENT, "
                + WordContract.WordEntry.COLUMN_WORD_NAME
                + " TEXT NOT NULL );";

        sqLiteDatabase.execSQL(SQL_CREATE_HISTORY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
