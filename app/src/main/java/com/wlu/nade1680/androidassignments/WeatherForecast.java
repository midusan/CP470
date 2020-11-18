package com.wlu.nade1680.androidassignments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class WeatherForecast extends AppCompatActivity {
    ProgressBar pb;
    TextView min;
    TextView max;
    TextView curr_temp;
    ImageView curr_weather_pic;
    Spinner drop_down;
    List<String> cities;
    TextView city;
    protected static final String ACTIVITY_NAME = "WeatherForecast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        drop_down = findViewById(R.id.spinner);
        curr_temp = findViewById(R.id.current_temp);
        min = findViewById(R.id.min_temp);
        max = findViewById(R.id.max_temp);
        curr_weather_pic = findViewById(R.id.current_weather);
        city = findViewById(R.id.city_name);
        pb = findViewById(R.id.pb);
        pb.setVisibility(View.VISIBLE);
        cities = Arrays.asList(getResources().getStringArray(R.array.cities));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drop_down.setAdapter(adapter);
        drop_down.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                new ForecastQuery(cities.get(position)).execute();
                String new_city = cities.get(position) + " Weather";
                city.setText(new_city);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public class ForecastQuery extends AsyncTask<String,Integer,String> {
        String city;
        String new_min;
        String new_max;
        String new_curr_temp;
        Bitmap bm;
        ForecastQuery(String city){
            this.city = city;
        }
        @Override
        protected String doInBackground(String... args) {
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + this.city + ",ca&APPID=79cecf493cb6e52d25bb7b7050ff723c&mode=xml&units=metric");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                try (InputStream in = conn.getInputStream()) {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(in, null);
                    while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                        if (parser.getEventType() == XmlPullParser.START_TAG) {
                            if (parser.getName().equals("temperature")) {
                                new_curr_temp = parser.getAttributeValue(null, "value");
                                publishProgress(25);
                                new_min = parser.getAttributeValue(null, "min");
                                publishProgress(50);
                                new_max = parser.getAttributeValue(null, "max");
                                publishProgress(75);
                            } else if (parser.getName().equals("weather")) {
                                String icon_name = parser.getAttributeValue(null, "icon");
                                String icon_file = icon_name + ".png";
                                Log.i(ACTIVITY_NAME, "Looking for file " + icon_file);
                                if (fileExistence(icon_file)) {
                                    FileInputStream fis = null;
                                    try {
                                        fis = openFileInput(icon_file);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    Log.i(ACTIVITY_NAME, "Found the image locally");
                                    bm = BitmapFactory.decodeStream(fis);
                                } else {
                                    String icon_URL = "https://openweathermap.org/img/w/" + icon_file;
                                    bm = getImage(new URL(icon_URL));
                                    FileOutputStream outputStream = openFileOutput(icon_file, Context.MODE_PRIVATE);
                                    bm.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    Log.i(ACTIVITY_NAME, "Downloaded File");
                                    outputStream.flush();
                                    outputStream.close();
                                }
                                publishProgress(100);
                            }
                        }
                        parser.next();
                    }
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
            return "";
        }

        public boolean fileExistence(String f_name) {
            File file = getBaseContext().getFileStreamPath(f_name);
            return file.exists();
        }
        public Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        protected void onProgressUpdate(Integer... value){
            pb.setProgress(value[0]);
        }
        protected void onPostExecute(String a){
            pb.setVisibility(View.INVISIBLE);
            curr_weather_pic.setImageBitmap(bm);
            String new_temp = new_curr_temp + "C\u00b0";
            curr_temp.setText(new_temp);
            String newer_min = new_min + "C\u00b0";
            min.setText(newer_min);
            String newer_max = new_max + "C\u00b0";
            max.setText(newer_max);
        }
        }

    }

