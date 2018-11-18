package com.example.firstcash.weatherapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.icu.text.TimeZoneFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import Collector.*;
import Parser.EditCity;
import Parser.HTTPdatatransfer;
import Parser.JSONParser;
import Sources.Sources;

public class MainActivity extends AppCompatActivity {

    private TextView cityText;
    private TextView tempText;
    private TextView condText;
    private TextView windText;
    private TextView humidityText;
    private TextView sunriseText;
    private TextView sunsetText;
    private TextView pressureText;

    private TextView lastUpdate;
    private ImageView imageView;
    private TextView highlow;
    private Weather   weather;
    private Switch aSwitch;
    private Boolean citynotfound;
    private Toast toast;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       cityText = (TextView) findViewById(R.id.cityText);
        tempText = (TextView)findViewById(R.id.tempText);
        condText = (TextView) findViewById(R.id.condtext);
        windText = (TextView)findViewById(R.id.windtext);
        humidityText = (TextView)findViewById(R.id.cloudtext);
        sunriseText =(TextView) findViewById(R.id.sunrisetext);
        sunsetText = (TextView)findViewById(R.id.sunset);
        pressureText = (TextView)findViewById(R.id.pressure);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        citynotfound = false;
        CharSequence text = "Error: City Not Found";
        Log.i("Toast is made", ".");
        int duration = Toast.LENGTH_LONG;
        toast = Toast.makeText(MainActivity.this,text,duration);

        lastUpdate = (TextView)findViewById(R.id.LastUpdated);
        imageView = (ImageView) findViewById(R.id.imagetext);
        highlow = (TextView) findViewById(R.id.high);
        weather = new Weather();
        aSwitch = (Switch) findViewById(R.id.switchunits);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                 int celci = MainActivity.this.celcius();
                 int celcihigh = MainActivity.this.highcelcius();
                 int celcilow = MainActivity.this.lowcelcius();

                 String celci2 = Integer.toString(celci);
                 String celcihigh2 = Integer.toString(celcihigh);
                 String celcilow2 = Integer.toString(celcilow);
                 tempText.setText(celci2 + "°C");
                 highlow.setText("High/Low: " + celcihigh2 + "°C/" + celcilow2 + "°C" );

                }else{
                    int fahrn = MainActivity.this.fahrenheight();
                    int fahrenhigh = MainActivity.this.highfahrenheight();
                    int fahrenlow = MainActivity.this.lowfahrenheight();
                    String fahrn2 = Integer.toString(fahrn);
                    tempText.setText(fahrn2 + "°F");
                    highlow.setText("High/Low: " + fahrenhigh + "°F/" + fahrenlow + "°F" );
                }
            }
        });

       // EditCity searchcity = new EditCity(MainActivity.this);

       // seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


          //  @Override
         //   public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


               // tempText.setText(Integer.toString(seekBar.getProgress()) + "GrandPa");


           // }

          //  @Override
            //public void onStartTrackingTouch(SeekBar seekBar) {



           // }

          //  @Override
          //  public void onStopTrackingTouch(SeekBar seekBar) {
          //      tempText.setText(Integer.toString(MainActivity.this.fahrenheight()) + "°F");
          //  }
        //});
        this.getthedata("Baltimore");

    }
    private int celcius(){
        double temptext = weather.cond.getTemp();
        int celc= (int) temptext-273;
        return celc;
    }
    private int highcelcius(){
        double temptext = weather.cond.getMaxtemp();
        int celc = (int) temptext-273;
        return celc;
    }
    private int lowcelcius(){
        double temptext = weather.cond.getMintemp();
        int celc = (int) temptext-273;
        return celc;
    }

    private int fahrenheight(){
        double temptext = weather.cond.getTemp();
        int fahren = (int) (((temptext - 273) * 9/5) + 32);
        return fahren;
    }
    private int lowfahrenheight(){
        double temptext = weather.cond.getMintemp();
        int fahren = (int) (((temptext - 273) * 9/5) + 32);
        return fahren;
    }
    private int highfahrenheight(){
        double temptext = weather.cond.getMaxtemp();
        int fahren = (int) (((temptext - 273) * 9/5) + 32);
        return fahren;
    }


    private void getthedata(String cityname){

        DisplayData displayData = new DisplayData();

        displayData.execute(new String[]{ cityname + "&APPID=98940fca00cacb8165d4e9d772d2f13f"});


    }

    protected class DisplayData extends AsyncTask<String,Void,Weather>{

        @Override
        protected Weather doInBackground(String... strings) {
            HTTPdatatransfer data = new HTTPdatatransfer();
            try {
                String getdata =  data.takeweatherinfo(strings[0]);
                weather = JSONParser.getWeather(getdata);
                weather.iconinfo = weather.cond.getIcon();
                new imagedownload().execute(weather.iconinfo);
                Log.v("DAATA: " , weather.location.getCity());
            } catch (IOException e) {



                    toast.show();
                    Log.i("Toast:", "was made");




                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return weather;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Weather weather) {

            super.onPostExecute(weather);
            cityText.setText(weather.location.getCity() + "," + weather.location.getCountry());
            double temptext = weather.cond.getTemp();
            int farhenheight = (int)(((temptext - 273) * 9/5) + 32);

            String tempdouble = Integer.toString(farhenheight);
            tempText.setText(tempdouble + "°F");
            condText.setText("Condition: " + weather.cond.getDescription());
            double windtext = weather.wind.getSpeed()  * 2.237 ;
            String winddouble= Double.toString(windtext);
            String substring = winddouble.substring(0,4);


            windText.setText("Wind: " + substring + " mph");


            humidityText.setText("Humidity: " + weather.cond.getHumidity() + "%");
            long sunrise = weather.location.getSunrise();
            Date date = new Date(sunrise * 1000);
            SimpleDateFormat sfd = new SimpleDateFormat("HH:mm:ss");
            long  sunset = weather.location.getSunset();
            Date date2 = new Date(sunset * 1000);
            SimpleDateFormat sfd2 = new SimpleDateFormat("HH:mm:ss");
            long  lastupdated = weather.location.getLastupdate();
            Date date3 = new Date(lastupdated * 1000);
            SimpleDateFormat sfd3 = new SimpleDateFormat("HH:mm:ss");
            sunriseText.setText("Sunrise: " + sfd.format(date));
            sunsetText.setText("Sunset: " + sfd2.format(date2));
            int pressure = (int) weather.cond.getPressure();
            pressureText.setText("Pressure: " +pressure + " hPa");
            lastUpdate.setText("Last Updated: " + sfd3.format(date3));
            double high = weather.cond.getMaxtemp();
            double low = weather.cond.getMintemp();
            int high1 = (int)(((high - 273) * 9/5) + 32);
            int low1 = (int)(((low - 273) * 9/5) + 32);
            highlow.setText("High/Low: " + high1 + "°F/" + low1 + "°F" );


        }
    }
    protected class imagedownload extends AsyncTask<String, Void, Bitmap>{


        @Override
        protected Bitmap doInBackground(String... strings) {

            return downloadimage(strings[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
            super.onPostExecute(bitmap);
        }
        private Bitmap downloadimage(String icon){
            HttpURLConnection conn = null;
            InputStream stream = null;
            Bitmap map = null;
            try {
                conn = (HttpURLConnection) (new URL(Sources.IMAGE_URL + icon + ".png")).openConnection();
                conn.setDoInput(true);

                conn.connect();
                stream = conn.getInputStream();
                map = BitmapFactory.decodeStream(stream);

            } catch (IOException e) {



                e.printStackTrace();
            }

            return map;

        }
    }
    public void dialog(){
        AlertDialog.Builder newdialog = new AlertDialog.Builder(MainActivity.this);
        newdialog.setTitle("Edit City");
       final EditText typecity = new EditText(MainActivity.this);
       typecity.setInputType(InputType.TYPE_CLASS_TEXT);
       typecity.setHint("Baltimore,US");
       newdialog.setView(typecity);
       newdialog.setPositiveButton("Search",new  DialogInterface.OnClickListener(){

           @Override
           public void onClick(DialogInterface dialog, int which) {
               EditCity newcity = new EditCity(MainActivity.this);
               newcity.editcity(typecity.getText().toString());
               String getcity = newcity.cityname();
               getthedata(getcity);


           }
       });



        newdialog.show();



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
            if(id == R.id.searchcity){
                dialog();

            }
            return super.onOptionsItemSelected(item);
    }
}

