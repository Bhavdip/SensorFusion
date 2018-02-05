package com.goatstone.universal;

import android.location.Location;

/**
 * Created by bhavdip on 2/6/18.
 */


class c implements aj {
    final /* synthetic */ CompassFragment a;

    c(CompassFragment compassFragment) {
        this.a = compassFragment;
    }

    public void a(Location location) {
        if (location != null) {
            if (!this.a.j) {
                this.a.a(location);
            }
            location.setTime(System.currentTimeMillis());
            this.a.a = location;
            if ("network".equals(location.getProvider())) {
                this.a.h = location.getAccuracy();
            }
            this.a.e();
        }
    }
}