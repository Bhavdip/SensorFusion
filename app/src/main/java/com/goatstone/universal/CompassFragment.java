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
import android.widget.TextView;

import com.goatstone.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import static com.goatstone.universal.LatLong.LATITUDE;
import static com.goatstone.universal.LatLong.LONGITUDE;

/**
 * Created by bhavdip on 2/5/18.
 */
public class CompassFragment extends Fragment {
  public Location mLocation;
  public CompassView mCompassView;
  private LocationHelper mLocHelper;
  private CompassHelper mCompassHelper;
  private ArrayList<CompassLocation> compassLocationList = new ArrayList<>();
  private HashMap g = new HashMap();
  public float locationAccuracy = -1.0f;
  private boolean i = false;
  public boolean isDeclinationAvailable = false;
  public float declination;
  public Handler handler = new Handler();
  private Runnable infoUpdate = new LocationInfoUpdate(this);
  private LocationUpdateListener locationUpdateListenerCallback = new LocationUpdateImpl(this);
  private SensorUpdateCallback sensorUpdateCallback = new SensorUpdateImpl(this);

  public void setBearing(float f) {
    int round = Math.round(((float) this.mCompassView.getCorrectiveAngle()) + f);
    while (round < 0) {
      round += 360;
    }
    int i = round;
    while (i >= 360) {
      i -= 360;
    }
    Log.d("tag", new StringBuilder(String.valueOf(i)).append("°").toString());
    ((TextView) (getView().findViewById(R.id.textViewBearing))).setText(
        new StringBuilder(String.valueOf(i)).append("°").toString());
  }

  public void latestLocation(Location location) {
    this.declination =
        new GeomagneticField((float) location.getLatitude(), (float) location.getLongitude(),
            (float) location.getAltitude(), location.getTime()).getDeclination();
    this.isDeclinationAvailable = true;
  }

  public void calcDistance(float f) {
    if (this.mLocation != null) {
      Iterator it = this.compassLocationList.iterator();
      while (it.hasNext()) {
        float f2;
        CompassLocation compassLocation = (CompassLocation) it.next();
        float bearing = compassLocation.getBearing();
        float bearingTo = this.mLocation.bearingTo(compassLocation) - f;
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
        bearingTo =
            (((Float) this.g.get(Long.valueOf(compassLocation.a()))).floatValue() * (bearing - f2))
                + f2;
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
                this.isNetworkEnable = false;
                return;
            }
            view.setVisibility(0);
            this.isNetworkEnable = true;
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

  public void updateLocationInfoUI() {
    if (this.mLocation != null) {
      ((TextView) getView().findViewById(R.id.textViewLat)).setText(
          formatLocationValue(this.mLocation.getLatitude(), LATITUDE, Measure.DECIMAL));
      ((TextView) getView().findViewById(R.id.textViewLang)).setText(
          formatLocationValue(this.mLocation.getLatitude(), LONGITUDE, Measure.DECIMAL));
      ((TextView) getView().findViewById(R.id.textViewProvider)).setText(
          this.mLocation.getProvider() != null ? this.mLocation.getProvider()
              : getString(R.string.na));
      ((TextView) getView().findViewById(R.id.textViewTimeFormat)).setText(
          String.format(getString(R.string.formatstring_time_seconds),
              Math.round((float) ((System.currentTimeMillis() - this.mLocation.getTime()) / 1000))));
    }
  }

  public void distanceCalculation() {
    if (this.mLocation != null) {
      Iterator it = this.compassLocationList.iterator();
      float f = Float.MAX_VALUE;
      while (it.hasNext()) {
        CompassLocation compassLocation = (CompassLocation) it.next();
        float distanceTo = this.mLocation.distanceTo(compassLocation);
        compassLocation.a(distanceTo);
        f = Math.min(f, distanceTo);
      }
      if (this.i) {
        this.mLocHelper.a(true);
      } else if (f < this.locationAccuracy) {
        this.mLocHelper.a(true);
      } else if (f > 2.0f * this.locationAccuracy) {
        this.mLocHelper.a(false);
      }
    }
  }

  public void addCompassLoc(CompassLocation compassLocation) {
    if (!this.compassLocationList.contains(compassLocation)) {
      this.compassLocationList.add(compassLocation);
      c(compassLocation);
    }
    //this.LocationUpdateImpl.notifyDataSetChanged();
  }

  public void removeCompassLoc(CompassLocation compassLocation) {
    this.compassLocationList.remove(compassLocation);
    this.g.remove(Long.valueOf(compassLocation.a()));
    //  this.LocationUpdateImpl.notifyDataSetChanged();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.mLocHelper = new LocationHelper(getActivity());
    this.mLocHelper.registerAndStartListen(this.locationUpdateListenerCallback);
    this.mCompassHelper = new CompassHelper(getActivity());
    this.mCompassHelper.registerAndStartListen(this.sensorUpdateCallback);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.compass_fragment, container, false);
    this.mCompassView = view.findViewById(R.id.compass_fragment_compassview);
    this.mCompassView.addCompassLocationList(this.compassLocationList);
    return view;
  }

  @Override
  public void onStart() {
    super.onStart();
    this.isDeclinationAvailable = false;
    this.mLocHelper.startListen();
    this.mCompassHelper.startListen();
    this.handler.post(this.infoUpdate);
    distanceCalculation();
  }

  @Override
  public void onStop() {
    super.onStop();
    this.mLocHelper.cleanUpLocation();
    this.mCompassHelper.stopListen();
    this.handler.removeCallbacks(this.infoUpdate);
  }

  public String formatLocationValue(double d, LatLong latLong, Measure measure) {
    String str = "";
    if (measure == Measure.DECIMAL) {
      return String.format(getString(R.string.formatstring_coordinate_decimal), d);
    }
    Number[] a = CompassLocation.a(d);
    str = "";
    str = latLong == LATITUDE ? a[3].intValue() == 1 ? getString(R.string.direction_n)
        : getString(R.string.direction_s)
        : a[3].intValue() == 1 ? getString(R.string.direction_e) : getString(R.string.direction_w);
    return String.format(getString(R.string.formatstring_coordinate_dms), a[0].intValue(),
        a[1].intValue(), a[2].doubleValue(), str);
  }
}
