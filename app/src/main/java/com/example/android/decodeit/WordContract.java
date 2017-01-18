package com.example.android.decodeit;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created with Android Studio
 * User: pulkitkumar190@gmail.com
 * Date: 17-01-2017
 * Time: 14:30
 */

public class WordContract {
    public static final String CONTENT_AUTHORITY = "com.example.android.words";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public final static String WORDS_PATH = "words";


    public WordContract(){
    }

    public static final class WordEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI ,WORDS_PATH);

        public final static String TABLE_NAME = "History";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_WORD_NAME = "Words";

    }
}
