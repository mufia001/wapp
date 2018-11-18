package Parser;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.Map;
import java.util.Set;

public class EditCity {
    SharedPreferences preferences;
    public EditCity(Activity activity){
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
    }
    public String cityname(){
        return preferences.getString("city","Baltimore,US");

    }
    public void editcity(String city){
        preferences.edit().putString("city",city).commit();

    }

}
