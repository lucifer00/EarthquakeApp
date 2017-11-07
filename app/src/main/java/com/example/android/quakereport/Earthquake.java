package com.example.android.quakereport;

/**
 * Created by Prashant on 8/22/2017.
 */

public class Earthquake {
    private Double magnitude;
    private String location;
    private Long timeInmilliseconds;
    String earthquakeurl;
    public Earthquake(Double mag,String loc,Long timeinmili,String url)
    {
        magnitude=mag;
        location=loc;
        earthquakeurl=url;
        timeInmilliseconds=timeinmili;
    }
    public Double getMagnitude()
    {
        return magnitude;
    }
    public String getLocation()
    {
        return location;
    }
    public Long getTimeInMilliSeconds()
    {
        return timeInmilliseconds;
    }
    public String getEarthquakeurl(){return earthquakeurl;}
}
