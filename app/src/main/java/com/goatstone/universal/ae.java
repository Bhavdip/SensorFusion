package com.goatstone.universal;

/**
 * Created by bhavdip on 2/5/18.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import java.util.ArrayList;
import java.util.Iterator;

public class ae {
    private final int a = 0;
    private final int b = 15000;
    private final int c = 30000;
    private ArrayList d = new ArrayList();
    private boolean e = false;
    public boolean f = false;
    public boolean g = false;
    public boolean h = false;
    private boolean i = false;
    public long j = Long.MAX_VALUE;
    private boolean k = false;
    public boolean l = false;
    private Context m;
    private LocationManager n;
    public Handler o = new Handler();
    private LocationListener p = new af(this);
    private LocationListener q = new ag(this);
    private Runnable r = new ah(this);
    private Runnable s = new ai(this);

    public ae(Context context) {
        this.m = context;
        this.n = (LocationManager) this.m.getSystemService(Context.LOCATION_SERVICE);
        d();
    }

    public void a(Location location) {
        if (this.i) {
            Iterator it = this.d.iterator();
            while (it.hasNext()) {
                ((aj) it.next()).a(location);
            }
        }
    }

    @SuppressLint("MissingPermission")
    private Location c() {
        Location lastKnownLocation = this.n.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Location lastKnownLocation2 = this.n.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return (lastKnownLocation == null || lastKnownLocation2 == null) ? lastKnownLocation != null ? lastKnownLocation : lastKnownLocation2 == null ? null : lastKnownLocation2 : lastKnownLocation2.getTime() > lastKnownLocation.getTime() ? lastKnownLocation2 : lastKnownLocation;
    }

    private void d() {
        try {
            this.k = this.n.isProviderEnabled("gps");
        } catch (Exception e) {
        }
        try {
            this.l = this.n.isProviderEnabled("network");
        } catch (Exception e2) {
        }
    }

    private void e() {
        if (this.h || !this.l) {
            f();
        }
        g();
    }

    @SuppressLint("MissingPermission")
    public void f() {
        boolean z = true;
        if (!this.f && this.k) {
            this.f = true;
            this.j = System.currentTimeMillis();
            if (!(this.f || this.g)) {
                z = false;
            }
            this.e = z;
            this.n.requestLocationUpdates("gps", 15000, 0.0f, this.p);
            this.o.postDelayed(this.r, 7500);
            this.o.postDelayed(this.s, 7500);
        }
    }

    @SuppressLint("MissingPermission")
    public void g() {
        boolean z = true;
        if (!this.g && this.l) {
            this.g = true;
            if (!(this.f || this.g)) {
                z = false;
            }
            this.e = z;
            this.n.requestLocationUpdates("network", 15000, 0.0f, this.q);
        }
    }

    public void h() {
        boolean z = false;
        this.o.removeCallbacks(this.r);
        this.o.removeCallbacks(this.s);
        this.n.removeUpdates(this.p);
        this.j = Long.MAX_VALUE;
        this.f = false;
        if (this.f || this.g) {
            z = true;
        }
        this.e = z;
    }

    public void i() {
        boolean z = false;
        this.n.removeUpdates(this.q);
        this.g = false;
        if (this.f || this.g) {
            z = true;
        }
        this.e = z;
    }

    private void j() {
        h();
        i();
    }

    public void a() {
        this.i = true;
        d();
        if (this.d.size() > 0) {
            e();
        }
        a(c());
    }

    public void a(aj ajVar) {
        this.d.add(ajVar);
        if (this.i) {
            ajVar.a(c());
            if (!this.e) {
                e();
            }
        }
    }

    public void a(boolean z) {
        if (this.h != z) {
            this.h = z;
            if (!this.i) {
                return;
            }
            if (this.h) {
                f();
            } else {
                h();
            }
        }
    }

    public void b() {
        this.i = false;
        j();
    }
}