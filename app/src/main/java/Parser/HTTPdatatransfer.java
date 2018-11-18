package Parser;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import Sources.Sources;

import static Sources.Sources.*;

public class HTTPdatatransfer {

    public String takeweatherinfo(String location) throws IOException {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        urlConnection = (HttpURLConnection) (new URL(Sources.API_URL + location)).openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoInput(true);

        urlConnection.setDoOutput(true);
        urlConnection.connect();
        StringBuffer buffer = new StringBuffer();

        inputStream = urlConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while((line = reader.readLine()) !=  null){
            buffer.append(line+ "/r/n");


        }
        inputStream.close();
        urlConnection.disconnect();
        String l = buffer.toString();
        Log.i("bufferfromjson", l);
        return buffer.toString();


    }
}
