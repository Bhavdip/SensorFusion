package com.goatstone.universal;


import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

class af implements LocationListener {
    final /* synthetic */ ae a;

    af(ae aeVar) {
        this.a = aeVar;
    }

    public void onLocationChanged(Location location) {
        if (location == null) {
            return;
        }
        if (location.getLatitude() != 0.0d || location.getLongitude() != 0.0d) {
            this.a.j = location.getTime();
            this.a.a(location);
        }
    }

    public void onProviderDisabled(String str) {
    }

    public void onProviderEnabled(String str) {
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
    }
}