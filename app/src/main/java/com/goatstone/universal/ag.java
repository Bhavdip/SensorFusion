package com.goatstone.universal;

/**
 * Created by bhavdip on 2/5/18.
 */


import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

class ag implements LocationListener {
    final /* synthetic */ ae a;

    ag(ae aeVar) {
        this.a = aeVar;
    }

    public void onLocationChanged(Location location) {
        if (location == null) {
            return;
        }
        if (location.getLatitude() != 0.0d || location.getLongitude() != 0.0d) {
            if (!this.a.f) {
                this.a.a(location);
            } else if (location.getTime() - this.a.j > 30000) {
                this.a.a(location);
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