package com.goatstone.universal;

import android.location.Location;
import android.location.LocationManager;

/**
 * Created by bhavdip on 2/6/18.
 */

class LocationUpdateImpl implements LocationUpdateListener {
  final CompassFragment fragment;

  LocationUpdateImpl(CompassFragment compassFragment) {
    this.fragment = compassFragment;
  }

  public void onLocationUpdateValue(Location location) {
    if (location != null) {
      //if declination is not available
      if (!this.fragment.isDeclinationAvailable) {
        this.fragment.latestLocation(location);
      }
      location.setTime(System.currentTimeMillis());
      this.fragment.mLocation = location;
      if (LocationManager.NETWORK_PROVIDER.equals(location.getProvider())) {
        this.fragment.locationAccuracy = location.getAccuracy();
      }
      this.fragment.distanceCalculation();
    }
  }
}