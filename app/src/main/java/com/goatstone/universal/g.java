package com.goatstone.universal;

/**
 * Created by bhavdip on 2/5/18.
 */

import java.util.Comparator;

class g implements Comparator {
    g() {
    }

    public int a(CompassLocation compassLocation, CompassLocation compassLocation2) {
        return compassLocation.f().compareToIgnoreCase(compassLocation2.f());
    }

    public /* synthetic */ int compare(Object obj, Object obj2) {
        return a((CompassLocation) obj, (CompassLocation) obj2);
    }
}