package Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Collector.Weather;
import Collector.Location;
import Sources.Sources;


public class JSONParser {

    public static Weather getWeather(String data) throws JSONException {
        Weather weather = new Weather();

        JSONObject jsonObject = new JSONObject(data);

        Location location = new Location();


        JSONObject coordinates = Sources.getObject("coord",jsonObject);
        location.setLon(Sources.getfloat("lon",coordinates));
        location.setLat(Sources.getfloat("lat",coordinates));

        JSONObject  sys = Sources.getObject("sys",jsonObject);
        location.setCity(Sources.getString("name",jsonObject));
        location.setCountry(Sources.getString("country",sys));
        location.setSunrise((long)Sources.getfloat("sunrise",sys));
        location.setSunset((long)Sources.getfloat("sunset",sys));
        location.setLastupdate((long)Sources.getfloat("dt", jsonObject));

        weather.location = location;

        JSONArray weath= jsonObject.getJSONArray("weather");
        JSONObject jsonWeather = weath.getJSONObject(0);
        weather.cond.setDescription(Sources.getString("description", jsonWeather));
        weather.cond.setWeatherid(Sources.getint("id" ,jsonWeather));
        weather.cond.setIcon(Sources.getString("icon", jsonWeather));
        weather.cond.setVisibility(Sources.getint("visibility",jsonObject));
        weather.cond.setMainweather(Sources.getString("main",jsonWeather));


        JSONObject mainObject =Sources.getObject("main",jsonObject);
        weather.cond.setPressure(Sources.getint("pressure" , mainObject));
        weather.cond.setHumidity(Sources.getfloat("humidity",mainObject));
        weather.cond.setTemp(Sources.getdouble("temp",mainObject));
        weather.cond.setMaxtemp(Sources.getfloat("temp_max",mainObject));
        weather.cond.setMintemp(Sources.getfloat("temp_min",mainObject));

        JSONObject cloudobject = Sources.getObject("clouds",jsonObject);
        weather.clouds.setPerception(Sources.getint("all",cloudobject));

        JSONObject windobject = Sources.getObject("wind", jsonObject);
        weather.wind.setDegrees(Sources.getint("deg", windobject));
        weather.wind.setSpeed(Sources.getfloat("speed", windobject));

        return weather;
    }

}
