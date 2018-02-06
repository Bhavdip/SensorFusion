package com.goatstone.universal;

/**
 * Created by bhavdip on 2/5/18.
 */

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

class RotationVectorListener implements SensorEventListener {
  final CompassHelper mCompassHelper;
  private float[] rationMatrix = new float[16];
  private float[] orientation = new float[3];
  private float d = 0.0f;

  RotationVectorListener(CompassHelper compassHelperVar) {
    this.mCompassHelper = compassHelperVar;
  }

  public void onAccuracyChanged(Sensor sensor, int i) {
  }

  public void onSensorChanged(SensorEvent sensorEvent) {
    SensorManager.getRotationMatrixFromVector(this.rationMatrix, sensorEvent.values);
    SensorManager.getOrientation(this.rationMatrix, this.orientation);
    float toDegrees = (float) Math.toDegrees((double) this.orientation[0]); //azmith
    while (toDegrees < 0.0f) {
      toDegrees += 360.0f;
    }
    if (toDegrees < 90.0f && this.d > 270.0f) {
      toDegrees += 360.0f;
    } else if (this.d < 90.0f && toDegrees > 270.0f) {
      this.d += 360.0f;
    }
    toDegrees = FilterSensor.a(this.d, toDegrees, 0.1f);
    while (toDegrees < 0.0f) {
      toDegrees += 360.0f;
    }
    while (toDegrees >= 360.0f) {
      toDegrees -= 360.0f;
    }
    this.d = toDegrees;
    this.mCompassHelper.e = toDegrees;
    this.mCompassHelper.notifyValue(toDegrees);
  }
}