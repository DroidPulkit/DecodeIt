package com.example.android.decodeit;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    EditText searchData;
    Spinner fromLang, toLang;
    ArrayAdapter<CharSequence> fromLangAdapter, toLangAdapter;
    ProgressBar pb;
    String URL_STRING;
    String jsonResponse = "";
    PublisherInterstitialAd mPublisherInterstitialAd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchData = (EditText) findViewById(R.id.searchData);
        fromLang = (Spinner) findViewById(R.id.fromLangSpinner);
        toLang = (Spinner) findViewById(R.id.toLangSpinner);
        pb = (ProgressBar) findViewById(R.id.pb);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        Button btnTranslate = (Button) findViewById(R.id.btnTranslateData);

        // Create an ArrayAdapter using the string array and a default spinner layout
        fromLangAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.fromLang,
                android.R.layout.simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        fromLangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        fromLang.setAdapter(fromLangAdapter);

        //Language to convert into

        // Create an ArrayAdapter using the string array and a default spinner layout
        toLangAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.toLang,
                android.R.layout.simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        toLangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        toLang.setAdapter(toLangAdapter);

        //Set up for pre-fetching interstitial ad request
        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId("ca-app-pub-7867604826748291/8122977163");

        mPublisherInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                //process the joke Request
                pb.setVisibility(View.VISIBLE);
                translateData();

                //pre-fetch the next ad
                requestNewInterstitial();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);

                Log.i(LOG_TAG, "onAdFailedToLoad: ad Failed to load. Reloading...");

                //prefetch the next ad
                requestNewInterstitial();

            }

            @Override
            public void onAdLoaded() {
                Log.i(LOG_TAG, "onAdLoaded: interstitial is ready!");
                super.onAdLoaded();
            }
        });

        //Kick off the fetch
        requestNewInterstitial();

        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPublisherInterstitialAd.isLoaded()) {
                    Log.i(LOG_TAG, "onClick: interstitial was ready");
                    mPublisherInterstitialAd.show();
                } else {
                    Log.i(LOG_TAG, "onClick: interstitial was not ready.");
                    pb.setVisibility(View.VISIBLE);
                    translateData();
                }
            }
        });

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

    }

    private void requestNewInterstitial() {
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("EA27D37DF5448BF42AA5F7A6D4F11A9B")
                .build();

        mPublisherInterstitialAd.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.History:
                Intent intent = new Intent(MainActivity.this, History.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void translateData() {
        String from = fromLang.getSelectedItem().toString();
        String to = toLang.getSelectedItem().toString();
        String data = searchData.getText().toString();
        String LangFrom = "";
        String LangTo = "";
        switch (from) {
            case "Auto Detect":
                LangFrom = "auto";
                break;
            case "English":
                LangFrom = "en";
                break;
            case "Hindi":
                LangFrom = "hi";
                break;
            case "Punjabi":
                LangFrom = "pa";
                break;
            case "Tamil":
                LangFrom = "ta";
                break;
            case "Urdu":
                LangFrom = "ur";
                break;
            case "German":
                LangFrom = "de";
                break;
            case "French":
                LangFrom = "fr";
                break;
        }
        switch (to) {
            case "English":
                LangTo = "en";
                break;
            case "Hindi":
                LangTo = "hi";
                break;
            case "Punjabi":
                LangTo = "pa";
                break;
            case "Tamil":
                LangTo = "ta";
                break;
            case "Urdu":
                LangTo = "ur";
                break;
            case "German":
                LangTo = "de";
                break;
            case "French":
                LangTo = "fr";
                break;
        }
        String lang = "";

        if (LangFrom.equalsIgnoreCase("auto")) {
            lang = LangTo;
        } else {
            lang = LangFrom + "-" + LangTo;
        }
        Log.d(LOG_TAG, "lang: " + lang);
        if (data.equalsIgnoreCase("")) {
            Toast.makeText(this, "Enter Some Data first", Toast.LENGTH_SHORT).show();

        } else {
            TranslateAsyncTask translate = new TranslateAsyncTask();
            translate.execute(data, lang);
            insertForHistory(data.trim());
        }
    }

    public void insertForHistory(String data) {
        ContentValues values = new ContentValues();
        values.put(WordContract.WordEntry.COLUMN_WORD_NAME, data);
        Uri uri = getContentResolver().insert(WordContract.WordEntry.CONTENT_URI, values);
    }

    public class TranslateAsyncTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
            RelativeLayout mainActivity = (RelativeLayout) findViewById(R.id.activity_main);
            //mainActivity.setVisibility(View.INVISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(String... params) {
            String apiKey = "trnsl.1.1.20170109T071840Z.e371c952c2ceac97.e798323149da36037145805995c9785bd33a6b09";

            String BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
            String API_KEY = "key";
            String TEXT = "text";
            String LANG = "lang";
            String result[] = new String[4];

            try {
                Uri buildUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY, apiKey)
                        .appendQueryParameter(TEXT, params[0])
                        .appendQueryParameter(LANG, params[1])
                        .build();

                URL_STRING = buildUri.toString();
                Log.d(LOG_TAG, "Build URI " + buildUri.toString());

                URL url = new URL(buildUri.toString());
                HttpHandler sh = new HttpHandler();

                // Making a request to url and getting response
                jsonResponse = sh.makeServiceCall(url.toString());
                Log.d(LOG_TAG, "JSON: " + jsonResponse);

                JSONObject root = new JSONObject(jsonResponse);
                String code = root.getString("code");
                String lang = root.getString("lang");

                JSONArray text = root.getJSONArray("text");
                String translation = (String) text.get(0);

                Log.d(LOG_TAG, "Status: " + code + ", Language: " + lang + ", Translation: " + translation);
                result[0] = params[0];
                result[1] = code;
                result[2] = lang;
                result[3] = translation;


            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            Intent intent = new Intent(MainActivity.this, Translated.class);
            intent.putExtra("translatedInfo", strings);
            startActivity(intent);

            pb.setVisibility(View.GONE);

            super.onPostExecute(strings);
        }
    }
}
