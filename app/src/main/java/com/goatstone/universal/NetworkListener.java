package com.goatstone.universal;

/**
 * Created by bhavdip on 2/5/18.
 */


import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

class NetworkListener implements LocationListener {
    final LocationHelper mLocationHelper;

    NetworkListener(LocationHelper locationHelperVar) {
        this.mLocationHelper = locationHelperVar;
    }

    public void onLocationChanged(Location location) {
        if (location == null) {
            return;
        }
        if (location.getLatitude() != 0.0d || location.getLongitude() != 0.0d) {
            if (!this.mLocationHelper.f) {
                this.mLocationHelper.addNewLocation(location);
            } else if (location.getTime() - this.mLocationHelper.lastLocationReqTime > 30000) {
                this.mLocationHelper.addNewLocation(location);
            }
        }
    }

    public void onProviderDisabled(String str) {
    }

    public void onProviderEnabled(String str) {
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
    }
}