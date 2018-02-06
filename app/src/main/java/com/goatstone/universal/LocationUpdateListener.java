package com.goatstone.universal;

import android.location.Location;

/**
 * Created by bhavdip on 2/5/18.
 */
public interface LocationUpdateListener {
    void onLocationUpdateValue(Location location);
}