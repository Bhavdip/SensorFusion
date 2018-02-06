package com.goatstone.universal;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by bhavdip on 2/5/18.
 */

class MagnaticAccelListener implements SensorEventListener {
  final CompassHelper mHelper;
  private float[] b = new float[3];
  private float[] c = new float[3];
  private float[] geomagnetic = new float[3];
  private float[] gravity = new float[3];
  private float[] rotation = new float[16];
  private float[] orientation = new float[3];

  MagnaticAccelListener(CompassHelper compassHelperVar) {
    this.mHelper = compassHelperVar;
  }

  public void onAccuracyChanged(Sensor sensor, int i) {
  }

  public void onSensorChanged(SensorEvent sensorEvent) {
    switch (sensorEvent.sensor.getType()) {
      case 1:
        this.gravity = FilterSensor.a(this.c, sensorEvent.values, this.gravity, 0.1f);
        break;
      case 2:
        this.geomagnetic = FilterSensor.a(this.b, sensorEvent.values, this.geomagnetic, 0.1f);
        break;
    }

    SensorManager.getRotationMatrix(this.rotation, null, this.gravity, this.geomagnetic);
    SensorManager.getOrientation(this.rotation, this.orientation);

    float toDegrees = (float) Math.toDegrees((double) this.orientation[0]);
    while (toDegrees < 0.0f) {
      toDegrees += 360.0f;
    }
    while (toDegrees >= 360.0f) {
      toDegrees -= 360.0f;
    }
    if (this.mHelper.threeSensorPresent) {
      this.mHelper.b(toDegrees);
    } else {
      this.mHelper.notifyValue(toDegrees);
    }
  }
}