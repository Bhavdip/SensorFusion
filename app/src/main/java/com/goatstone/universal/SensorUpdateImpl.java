package com.goatstone.universal;

/**
 * Created by bhavdip on 2/6/18.
 */

class SensorUpdateImpl implements SensorUpdateCallback {
  final CompassFragment compassFragment;

  SensorUpdateImpl(CompassFragment compassFragment) {
    this.compassFragment = compassFragment;
  }

  public void onBearingValue(float f) {
    if (this.compassFragment.isDeclinationAvailable) {
      f += this.compassFragment.declination;
    }
    this.compassFragment.calcDistance(f);
    this.compassFragment.setBearing(f);
    this.compassFragment.mCompassView.setBearing(f);
  }
}