package com.example.mylib.GPSAdmin;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by sergio on 29/1/18.
 */

public class GPSTrackerEvents implements LocationListener {

    GPSTracker gpsTracker;

    public GPSTrackerEvents(GPSTracker gpsTracker){
        this.gpsTracker = gpsTracker;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("GPSTrackerEvents","HA CAMBIADO DE POSICION: "+location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
