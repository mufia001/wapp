package Collector;

public class Condition {
    private float mintemp;
    private float maxtemp;
    private long visibility;
    private int weatherid;
    private String description;
    private String icon;
    private String mainweather;
    private double temp;

    public double getTemp() {
          return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getMainweather() {
        return mainweather;
    }

    public void setMainweather(String mainweather) {
        this.mainweather = mainweather;
    }

    public float getMintemp() {
        return mintemp;
    }

    public String getDescription() {
        return description;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public long getVisibility() {
        return visibility;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public String getIcon() {

        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setVisibility(long visibility) {
        this.visibility = visibility;

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeatherid() {
        return weatherid;
    }

    public void setWeatherid(int weatherid) {
        this.weatherid = weatherid;
    }

    public float getMaxtemp() {

        return maxtemp;
    }

    public void setMaxtemp(float maxtemp) {
        this.maxtemp = maxtemp;
    }

    public void setMintemp(float mintemp) {
        this.mintemp = mintemp;

    }

    private float pressure;
    private float humidity;
    private String wind;
}
