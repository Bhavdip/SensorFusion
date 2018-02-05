package com.goatstone.universal;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bhavdip on 2/5/18.
 */


class f implements Parcelable.Creator {
    f() {
    }

    public CompassLocation a(Parcel parcel) {
        CompassLocation compassLocation = new CompassLocation(parcel.readString());
        compassLocation.setTime(parcel.readLong());
        compassLocation.setLatitude(parcel.readDouble());
        compassLocation.setLongitude(parcel.readDouble());
        compassLocation.setAltitude(parcel.readDouble());
        compassLocation.setSpeed(parcel.readFloat());
        compassLocation.setBearing(parcel.readFloat());
        compassLocation.setAccuracy(parcel.readFloat());
        compassLocation.setExtras(parcel.readBundle());
        compassLocation.c = parcel.readLong();
        compassLocation.d = parcel.readInt() > 0;
        compassLocation.f = parcel.readString();
        compassLocation.g = parcel.readInt();
        compassLocation.h = parcel.readFloat();
        return compassLocation;
    }

    public CompassLocation[] a(int i) {
        return new CompassLocation[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return a(i);
    }
}