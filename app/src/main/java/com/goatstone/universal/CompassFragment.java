package com.goatstone.universal;

import android.app.Fragment;
import android.hardware.GeomagneticField;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goatstone.sensorFusion.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by bhavdip on 2/5/18.
 */

public class CompassFragment extends Fragment {
    public Location a;
    public CompassView b;
    //private y c;
    private ae d;
    private ao e;
    private ArrayList f = new ArrayList();
    private HashMap g = new HashMap();
    public float h = -1.0f;
    private boolean i = false;
    public boolean j = false;
    public float k;
    private boolean l = false;
    public Handler m = new Handler();
    private Runnable n = new b(this);
    private aj o = new c(this);
    private ar p = new d(this);

    public void a() {
       /* if (getActivity() != null) {
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            this.i = defaultSharedPreferences.getBoolean(getResources().getString(R.string.pref_key_gps), false);
            this.l = defaultSharedPreferences.getBoolean("KEY_LOCATION_INFO_VISIBLE", false);
        }*/
    }

    public void a(float f) {
        int round = Math.round(((float) this.b.getCorrectiveAngle()) + f);
        while (round < 0) {
            round += 360;
        }
        int i = round;
        while (i >= 360) {
            i -= 360;
        }
        Log.d("tag",new StringBuilder(String.valueOf(i)).append("°").toString());
    }

    public void a(Location location) {
        this.k = new GeomagneticField((float) location.getLatitude(), (float) location.getLongitude(), (float) location.getAltitude(), location.getTime()).getDeclination();
        this.j = true;
    }

    private void b() {
      /*  Editor edit = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
        edit.putBoolean("KEY_LOCATION_INFO_VISIBLE", this.l);
        edit.commit();*/
    }

    public void b(float f) {
        if (this.a != null) {
            Iterator it = this.f.iterator();
            while (it.hasNext()) {
                float f2;
                CompassLocation compassLocation = (CompassLocation) it.next();
                float bearing = compassLocation.getBearing();
                float bearingTo = this.a.bearingTo(compassLocation) - f;
                while (bearingTo < 0.0f) {
                    bearingTo += 360.0f;
                }
                if (bearingTo < 90.0f && bearing > 270.0f) {
                    f2 = bearing;
                    bearing = bearingTo + 360.0f;
                } else if (bearing >= 90.0f || bearingTo <= 270.0f) {
                    f2 = bearing;
                    bearing = bearingTo;
                } else {
                    f2 = bearing + 360.0f;
                    bearing = bearingTo;
                }
                bearingTo = (((Float) this.g.get(Long.valueOf(compassLocation.a()))).floatValue() * (bearing - f2)) + f2;
                while (bearingTo > 360.0f) {
                    bearingTo -= 360.0f;
                }
                compassLocation.setBearing(bearingTo);
            }
        }
    }

    public void c() {
        /*View view = getView();
        if (view != null) {
            view = view.findViewById(R.id.compass_fragment_location_info);
            if (view == null) {
                return;
            }
            if (view.getVisibility() == 0) {
                view.setVisibility(8);
                this.l = false;
                return;
            }
            view.setVisibility(0);
            this.l = true;
        }*/
    }

    private void c(CompassLocation compassLocation) {
        Random random = new Random();
        float nextFloat = random.nextFloat() * (random.nextFloat() * random.nextFloat());
        if (nextFloat < 0.05f) {
            nextFloat += 0.05f;
        }
        this.g.put(Long.valueOf(compassLocation.a()), Float.valueOf(nextFloat));
    }

    public void d() {
       /* View view = getView();
        if (view != null && this.a != null && view.findViewById(R.id.compass_fragment_location_info) != null) {
            TextView textView = (TextView) view.findViewById(R.id.compass_fragment_location_info_accuracy);
            TextView textView2 = (TextView) view.findViewById(R.id.compass_fragment_location_info_latitude);
            TextView textView3 = (TextView) view.findViewById(R.id.compass_fragment_location_info_longitude);
            TextView textView4 = (TextView) view.findViewById(R.id.compass_fragment_location_info_provided_by);
            TextView textView5 = (TextView) view.findViewById(R.id.compass_fragment_location_info_time_since_update);
            i iVar = new i(getActivity());
            m mVar = new m(getActivity());
            textView2.setText(iVar.a(this.a.getLatitude(), l.LATITUDE));
            textView3.setText(iVar.a(this.a.getLongitude(), l.LONGITUDE));
            textView.setText(mVar.a(this.a.getAccuracy()));
            textView4.setText(this.a.getProvider() != null ? this.a.getProvider() : getString(R.string.na));
            textView5.setText(String.format(getString(R.string.formatstring_time_seconds), new Object[]{Integer.valueOf(Math.round((float) ((System.currentTimeMillis() - this.a.getTime()) / 1000)))}));
        }*/
    }

    public void e() {
        if (this.a != null) {
            Iterator it = this.f.iterator();
            float f = Float.MAX_VALUE;
            while (it.hasNext()) {
                CompassLocation compassLocation = (CompassLocation) it.next();
                float distanceTo = this.a.distanceTo(compassLocation);
                compassLocation.a(distanceTo);
                f = Math.min(f, distanceTo);
            }
            /*this.c.sort(CompassLocation.b);
            this.c.notifyDataSetChanged();*/
            if (this.i) {
                this.d.a(true);
            } else if (f < this.h) {
                this.d.a(true);
            } else if (f > 2.0f * this.h) {
                this.d.a(false);
            }
        }
    }

    public void a(CompassLocation compassLocation) {
        if (!this.f.contains(compassLocation)) {
            this.f.add(compassLocation);
            c(compassLocation);
        }
        //this.c.notifyDataSetChanged();
    }

    public void b(CompassLocation compassLocation) {
        this.f.remove(compassLocation);
        this.g.remove(Long.valueOf(compassLocation.a()));
      //  this.c.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.d = new ae(getActivity());
        this.d.a(this.o);
        this.e = new ao(getActivity());
        this.e.a(this.p);
        //this.c = new y(getActivity(), R.layout.key_list_item, this.f);
        a();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.compass_fragment, container, false);
        //this.b = (CompassView) inflate.findViewById(R.id.compass_fragment_compassview);
        //this.b.a(this.f);
        return inflate;
    }

    public void onStart() {
        super.onStart();
        this.j = false;
        this.d.a();
        this.e.a();
        this.m.post(this.n);
        a();
        e();
    }

    public void onStop() {
        super.onStop();
        this.d.b();
        this.e.b();
        this.m.removeCallbacks(this.n);
        b();
    }
}
