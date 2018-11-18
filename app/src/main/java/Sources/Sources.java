package Sources;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class Sources {
    public static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    public static final String IMAGE_URL = " http://openweathermap.org/img/w/";

    public static JSONObject getObject(String name, JSONObject jsonObject) throws JSONException {
        JSONObject obj = jsonObject.getJSONObject(name);
        return obj;

    }
    public static double getdouble(String name, JSONObject jsonObject) throws JSONException {
        return  jsonObject.getDouble(name);
    }
    public static String getString(String name, JSONObject jsonObject) throws JSONException {
        return jsonObject.getString(name);
    }
    public static float getfloat(String name, JSONObject jsonObject) throws JSONException {
        return (float) jsonObject.getDouble(name);

    }
    public static int getint(String name, JSONObject jsonObject) throws JSONException {
        try {
            return jsonObject.getInt(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
