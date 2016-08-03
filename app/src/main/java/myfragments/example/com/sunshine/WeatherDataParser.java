package myfragments.example.com.sunshine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mpudota on 8/1/16.
 */
public class WeatherDataParser {

    public static double getMaxTemp(String jsondatastring, int daysIndex) throws JSONException {

        JSONObject weather = new JSONObject(jsondatastring);
        JSONArray days = weather.getJSONArray("list");
        JSONObject daysInfo = days.getJSONObject(daysIndex);
        JSONObject temparatureInfo = daysInfo.getJSONObject("temp");
        return temparatureInfo.getDouble("max");
    }
}
