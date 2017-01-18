package com.example.android.decodeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.fontometrics.Fontometrics;

/**
 * Created with Android Studio
 * User: pulkitkumar190@gmail.com
 * Date: 17-01-2017
 * Time: 13:16
 */

public class Translated extends AppCompatActivity{

    private static final String LOG_TAG = Translated.class.getSimpleName();

    String [] translatedInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translated);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Log.d(LOG_TAG,"Inside Translated Class");

        Intent intent = getIntent();
        translatedInfo = intent.getStringArrayExtra("translatedInfo");
        Log.d(LOG_TAG, translatedInfo.toString());

        TextView textView = (TextView) findViewById(R.id.translation);
        textView.setText(translatedInfo[3]);
        //Using 3rd party Library to set fontface
        textView.setTypeface(Fontometrics.coffee_with_sugar(this));

    }
}
