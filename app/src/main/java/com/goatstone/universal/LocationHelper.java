package com.goatstone.universal;

/**
 * Created by bhavdip on 2/5/18.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import java.util.ArrayList;

public class LocationHelper {

  private final int a = 0;
  private final int b = 15000;
  private final int c = 30000;

  private ArrayList<LocationUpdateListener> LocUpdateCallbackList = new ArrayList<>();
  private boolean isLocationServiceOn = false;
  public boolean f = false;
  boolean h = false;
  private boolean i = false;
  boolean g = false;

  long lastLocationReqTime = Long.MAX_VALUE;
  private boolean isGPSEnable = false;
  boolean isNetworkEnable = false;
  Handler mHandler = new Handler();

  private LocationManager mLocationManager;
  private LocationListener gpsListener = new GPSLocationListener(this);
  private LocationListener networkListener = new NetworkListener(this);

  private Runnable gpsRunnable = new GpsRunnable(this);
  private Runnable networkRunnable = new NetworkRunnable(this);

  LocationHelper(Context context) {
    if (context != null) {
      this.mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
      checkLocationProvider();
    }
  }

  void addNewLocation(Location location) {
    if (this.i) {
      for (Object aRegisterListenerCallback : this.LocUpdateCallbackList) {
        ((LocationUpdateListener)(aRegisterListenerCallback)).onLocationUpdateValue(location);
      }
    }
  }

  @SuppressLint("MissingPermission")
  private Location calculateLastKnowLocation() {
    Location lastKnownLocation =
        this.mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    Location lastKnownLocation2 =
        this.mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    return (lastKnownLocation == null || lastKnownLocation2 == null) ? lastKnownLocation != null
        ? lastKnownLocation : lastKnownLocation2
        : lastKnownLocation2.getTime() > lastKnownLocation.getTime() ? lastKnownLocation2
            : lastKnownLocation;
  }

  private void checkLocationProvider() {
    try {
      this.isGPSEnable = this.mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      this.isNetworkEnable =
          this.mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    } catch (Exception e2) {
      e2.printStackTrace();
    }
  }

  private void requestListenLocation() {
    //If network is off then use gps location
    //default is gps location update
    if (this.h || !this.isNetworkEnable) {
      requestForGPSLocation();
    }
    //other wise network location
    requestForNetworkLocation();
  }

  @SuppressLint("MissingPermission")
  void requestForGPSLocation() {
    boolean z = true;
    if (!this.f && this.isGPSEnable) {
      this.f = true;
      this.lastLocationReqTime = System.currentTimeMillis();
      if (!(this.f || this.g)) {
        z = false;
      }
      this.mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0.0f,this.gpsListener);
      this.mHandler.postDelayed(this.gpsRunnable, 7500);
      this.mHandler.postDelayed(this.networkRunnable, 7500);
      this.isLocationServiceOn = z;
    }
  }

  @SuppressLint("MissingPermission")
  void requestForNetworkLocation() {
    boolean z = true;
    if (!this.g && this.isNetworkEnable) {
      this.g = true;
      if (!(this.f || this.g)) {
        z = false;
      }
      this.mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 15000, 0.0f,
          this.networkListener);
      this.isLocationServiceOn = z;
    }
  }

  void resetGPSListener() {
    boolean z = false;
    this.mHandler.removeCallbacks(this.gpsRunnable);
    this.mHandler.removeCallbacks(this.networkRunnable);
    this.mLocationManager.removeUpdates(this.gpsListener);
    this.lastLocationReqTime = Long.MAX_VALUE;
    this.f = false;
    if (this.f || this.g) {
      z = true;
    }
    this.isLocationServiceOn = z;
  }

  public void resetNetworkListener() {
    boolean z = false;
    this.mLocationManager.removeUpdates(this.networkListener);
    this.g = false;
    if (this.f || this.g) {
      z = true;
    }
    this.isLocationServiceOn = z;
  }

  private void j() {
    resetGPSListener();
    resetNetworkListener();
  }

  public void startListen() {
    this.i = true;
    checkLocationProvider();
    if (this.LocUpdateCallbackList.size() > 0) {
      requestListenLocation();
    }
    addNewLocation(calculateLastKnowLocation());
  }

  public void registerAndStartListen(LocationUpdateListener locationUpdateListenerVar) {
    this.LocUpdateCallbackList.add(locationUpdateListenerVar);
    if (this.i) {
      locationUpdateListenerVar.onLocationUpdateValue(calculateLastKnowLocation());
      if (!this.isLocationServiceOn) { // location service is off then on it
        requestListenLocation();
      }
    }
  }

  public void a(boolean z) {
    if (this.h != z) {
      this.h = z;
      if (!this.i) {
        return;
      }
      if (this.h) {
        requestForGPSLocation();
      } else {
        resetGPSListener();
      }
    }
  }

  public void b() {
    this.i = false;
    j();
  }
}