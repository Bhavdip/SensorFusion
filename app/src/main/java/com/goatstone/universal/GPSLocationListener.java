package com.goatstone.universal;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

class GPSLocationListener implements LocationListener {
  final /* synthetic */ LocationHelper mLocationHelper;

  GPSLocationListener(LocationHelper locationHelperVar) {
    this.mLocationHelper = locationHelperVar;
  }

  public void onLocationChanged(Location location) {
    if (location == null) {
      return;
    }
    if (location.getLatitude() != 0.0d || location.getLongitude() != 0.0d) {
      this.mLocationHelper.lastLocationReqTime = location.getTime();
      this.mLocationHelper.addNewLocation(location);
    }
  }

  public void onProviderDisabled(String str) {
  }

  public void onProviderEnabled(String str) {
  }

  public void onStatusChanged(String str, int i, Bundle bundle) {
  }
}