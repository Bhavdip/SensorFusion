package com.goatstone.universal;

/**
 * Created by bhavdip on 2/5/18.
 */

class LocationInfoUpdate implements Runnable {
  final CompassFragment fragment;

  LocationInfoUpdate(CompassFragment compassFragment) {
    this.fragment = compassFragment;
  }

  public void run() {
    this.fragment.updateLocationInfoUI();
    this.fragment.handler.postDelayed(this, 1000);
  }
}