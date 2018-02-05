package com.goatstone.universal;

/**
 * Created by bhavdip on 2/5/18.
 */


import android.graphics.Color;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Comparator;

public class CompassLocation extends Location implements Parcelable {
    public static final Creator CREATOR = new f();
    public static Comparator a = new g();
    public static Comparator b = new h();
    public long c = -1;
    public boolean d = true;
    public boolean e = false;
    public String f = "";
    public int g = -1;
    public float h;

    public CompassLocation() {
        super("gps");
    }

    public CompassLocation(String str) {
        super(str);
    }

    public static Number[] a(double d) {
        int i = 2;
        Number[] numberArr = new Number[4];
        double abs = Math.abs(d);
        int i2 = (int) abs;
        abs = (abs - ((double) i2)) * 60.0d;
        int i3 = (int) abs;
        abs = (abs - ((double) i3)) * 60.0d;
        numberArr[0] = Integer.valueOf(i2);
        numberArr[1] = Integer.valueOf(i3);
        numberArr[2] = Double.valueOf(abs);
        if (d >= 0.0d) {
            i = 1;
        }
        numberArr[3] = Integer.valueOf(i);
        return numberArr;
    }

    public static ArrayList g() {
        ArrayList arrayList = new ArrayList();
        CompassLocation compassLocation = new CompassLocation();
        compassLocation.a("New York City");
        compassLocation.a(Color.parseColor("#AA66CC"));
        compassLocation.setLatitude(40.71426d);
        compassLocation.setLongitude(-74.005973d);
        compassLocation.b(true);
        arrayList.add(compassLocation);
        compassLocation = new CompassLocation();
        compassLocation.a("Tokyo");
        compassLocation.a(Color.parseColor("#FF4444"));
        compassLocation.setLatitude(35.689488d);
        compassLocation.setLongitude(139.691706d);
        compassLocation.b(true);
        arrayList.add(compassLocation);
        compassLocation = new CompassLocation();
        compassLocation.a("Dubai");
        compassLocation.a(Color.parseColor("#FFBB33"));
        compassLocation.setLatitude(25.25d);
        compassLocation.setLongitude(55.3d);
        compassLocation.b(true);
        arrayList.add(compassLocation);
        compassLocation = new CompassLocation();
        compassLocation.a("Sydney");
        compassLocation.a(Color.parseColor("#99CC00"));
        compassLocation.setLatitude(-33.86713d);
        compassLocation.setLongitude(151.207114d);
        compassLocation.b(true);
        arrayList.add(compassLocation);
        compassLocation = new CompassLocation();
        compassLocation.a("Paris");
        compassLocation.a(Color.parseColor("#33B5E5"));
        compassLocation.setLatitude(48.856667d);
        compassLocation.setLongitude(2.350987d);
        compassLocation.b(true);
        arrayList.add(compassLocation);
        return arrayList;
    }

    public long a() {
        return this.c;
    }

    public CompassLocation a(float f) {
        this.h = f;
        return this;
    }

    public CompassLocation a(int i) {
        this.g = i;
        return this;
    }

    public CompassLocation a(long j) {
        this.c = j;
        return this;
    }

    public CompassLocation a(String str) {
        this.f = str;
        return this;
    }

    public CompassLocation a(boolean z) {
        this.d = z;
        return this;
    }

    public int b() {
        return this.g;
    }

    public CompassLocation b(boolean z) {
        this.e = z;
        return this;
    }

    public float c() {
        return this.h;
    }

    public boolean d() {
        return this.d;
    }

    public int describeContents() {
        return 0;
    }

    public boolean e() {
        return this.e;
    }

    public String f() {
        return this.f;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getProvider());
        parcel.writeLong(getTime());
        parcel.writeDouble(getLatitude());
        parcel.writeDouble(getLongitude());
        parcel.writeDouble(getAltitude());
        parcel.writeFloat(getSpeed());
        parcel.writeFloat(getBearing());
        parcel.writeFloat(getAccuracy());
        parcel.writeBundle(getExtras());
        parcel.writeLong(this.c);
        parcel.writeInt(this.d ? 1 : 0);
        parcel.writeString(this.f);
        parcel.writeInt(this.g);
        parcel.writeFloat(this.h);
    }
}