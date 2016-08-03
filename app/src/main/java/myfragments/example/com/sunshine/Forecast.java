package myfragments.example.com.sunshine;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by mpudota on 7/20/16.
 */
public class Forecast extends Fragment {

    public ArrayAdapter<String> mForecastAdapter;

    String TAG = "sunShine";
    EditText editText;

    public Forecast () {
    }


    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;




    public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                // Add this line in order for this fragment to handle menu events.


                        setHasOptionsMenu(true);
            }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflator) {

        inflator.inflate(R.menu.forecast, menu);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.action_refresh) {

            Log.d(TAG, "It's getting executed");
            WeatherForecast weatherForecast = new WeatherForecast();
            weatherForecast.execute("94043");
            Log.d(TAG, "Is it too?");
        }

        return super.onOptionsItemSelected(item);
    }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            // Create some dummy data for the ListView.  Here's a sample weekly forecast
                                String[] data = {
                                "Mon 6/23 - Sunny - 31/17",
                               "Tue 6/24 - Foggy - 21/8", "Wed 6/25 - Cloudy - 22/17",
                                "Thurs 6/26 - Rainy - 18/11",
                                "Fri 6/27 - Foggy - 21/10",
                                "Sat 6/28 - TRAPPED IN WEATHER STATION - 23/18",
                                "Sun 6/29 - Sunny - 20/7"
                                };
                List<String> weekForecast = new ArrayList<String>(Arrays.asList(data));

                        // Now that we have some dummy forecast data, create an ArrayAdapter.
                                // The ArrayAdapter will take data from a source (like our dummy forecast) and
                                        // use it to populate the ListView it's attached to.
                                                mForecastAdapter =
                                new ArrayAdapter<String>(
                                                getActivity(), // The current context (this activity)
                                                R.layout.list_item_forecast, // The name of the layout ID.
                                                R.id.list_item_forecase_textview, // The ID of the textview to populate.
                                                weekForecast);

                       View rootView = inflater.inflate(R.layout.fragment, container, false);

                        // Get a reference to the ListView, and attach this adapter to it.
                                ListView listView = (ListView) rootView.findViewById(R.id.list_week_items);
               listView.setAdapter(mForecastAdapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String forecast = mForecastAdapter.getItem(position);
//                    Toast.makeText(getActivity(), forecast, Toast.LENGTH_SHORT).show();
                    Intent detialactivity_intent = new Intent(getActivity(), Detail_Activity.class);
//                            .putExtra(Intent.EXTRA_TEXT, forecast);
                    startActivity(detialactivity_intent);
                }
            });



                        return rootView;
    // Will contain the raw JSON response as a string.
}

 class WeatherForecast extends AsyncTask<String, Void, String[]> {


     String forecastJsonStr = null;

     private String getReadabledatestring() {
         SimpleDateFormat shorteneddateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         return shorteneddateformat.format(new Date());
     }

     private String formatHighLow (Double high, Double low) {
         long roundedhigh = Math.round(high);
         long roundedlow = Math.round(low);

         String highlowstring = roundedhigh + " / " + roundedlow ;
         return  highlowstring;
     }

     private String[] getweatherdatafromjson(String forecastJsonStr, int numDays) throws JSONException {

         final String OWM_List = "list";
         final String OWM_Weather = "weather";
         final String OWM_TEMPARATURE = "temp";
         final String OWM_MAX = "max";
         final String OWM_MIN = "min";
         final String OWM_DESCRIPTION = "main";

         JSONObject forecasejson = new JSONObject(forecastJsonStr);
         JSONArray weatherArray = forecasejson.getJSONArray(OWM_List);

         String[] daydata = new String[numDays];

         for(int i =0; i < weatherArray.length(); i++) {
             String day;
             String description;
             String highlow;

             JSONObject dayForecast = weatherArray.getJSONObject(i);

             day = getReadabledatestring();

             JSONObject weatherObject = dayForecast.getJSONArray(OWM_Weather).getJSONObject(0);
             description = weatherObject.getString(OWM_DESCRIPTION);

             JSONObject temparatureobject = dayForecast.getJSONObject(OWM_TEMPARATURE);
             double high = temparatureobject.getDouble(OWM_MAX);
             double low = temparatureobject.getDouble(OWM_MIN);

             highlow = formatHighLow(high,low);

             daydata[i] = day + " - " + description + " - " + highlow ;
         }
         for (String s : daydata) {
             Log.v(TAG, "Forecast entry : " + s);

         }
         return daydata;
     }


     @Override
     protected String[] doInBackground(String... params) {


         if (params.length == 0) {
             return null;
         }

         String apikey = "48c58a9aa540fc470bf186871e168d68";


         String format = "json";
         String units = "metric";
         int numDays = 7;

         try {

            String baseUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?";

            final String QUERY_PARAM = "q";
            final String FORMAT_PARAM = "mode";
            final String UNIT_FORMAT ="units";
            final String DAYS_PARAM = "cnt";
            final String APPID_PARAM = "APPID";;
             String apiKey = apikey;

             Uri buildUri = Uri.parse(baseUrl).buildUpon()
                     .appendQueryParameter(QUERY_PARAM, params[0])
                     .appendQueryParameter(FORMAT_PARAM, format)
                     .appendQueryParameter(UNIT_FORMAT, units)
                     .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                     .appendQueryParameter(APPID_PARAM, apiKey)
                     .build();

             URL url = new URL(buildUri.toString());

             Log.v(TAG, buildUri.toString());

             // Create the request to OpenWeatherMap, and open the connection
             urlConnection = (HttpURLConnection) url.openConnection();
             urlConnection.setRequestMethod("GET");
             urlConnection.connect();

             // Read the input stream into a String
             InputStream inputStream = urlConnection.getInputStream();
             StringBuffer buffer = new StringBuffer();
             if (inputStream == null) {
                 // Nothing to do.
             }
             reader = new BufferedReader(new InputStreamReader(inputStream));
             String line;
             while ((line = reader.readLine()) != null) {
                 // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                 // But it does make debugging a *lot* easier if you print out the completed
                 // buffer for debugging.
                 buffer.append(line + "\n");
             }
             if (buffer.length() == 0) {
                 // Stream was empty.  No point in parsing.
             }
             forecastJsonStr = buffer.toString();
             Log.v(TAG, forecastJsonStr);
         } catch (IOException e) {
             Log.e("PlaceholderFragment", "Error ", e);
             // If the code didn't successfully get the weather data, there's no point in attempting
             // to parse it.
         } finally {
             if (urlConnection != null) {
                 urlConnection.disconnect();
             }
             if (reader != null) {
                 try {
                     reader.close();
                 } catch (final IOException e) {
                     Log.e("PlaceholderFragment", "Error closing stream", e);
                 }
             }
         }
         try
         {
             return getweatherdatafromjson(forecastJsonStr, numDays);
         } catch (JSONException e) {
             Log.e(TAG, e.getMessage(), e);
             e.printStackTrace();
         }
         return null;
     }

     @Override
     protected void onPostExecute(String[] result) {
         if (result != null) {
             mForecastAdapter.clear();
         }
         for (String daystringdata : result) {
             mForecastAdapter.addAll(daystringdata);
         }
     }
 }
}