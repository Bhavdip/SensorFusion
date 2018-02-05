package com.goatstone.universal;

/**
 * Created by bhavdip on 2/5/18.
 */


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

class aq implements SensorEventListener {
    final /* synthetic */ ao a;
    private float[] b = new float[16];
    private float[] c = new float[3];
    private float d = 0.0f;

    aq(ao aoVar) {
        this.a = aoVar;
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        SensorManager.getRotationMatrixFromVector(this.b, sensorEvent.values);
        SensorManager.getOrientation(this.b, this.c);
        float toDegrees = (float) Math.toDegrees((double) this.c[0]);
        while (toDegrees < 0.0f) {
            toDegrees += 360.0f;
        }
        if (toDegrees < 90.0f && this.d > 270.0f) {
            toDegrees += 360.0f;
        } else if (this.d < 90.0f && toDegrees > 270.0f) {
            this.d += 360.0f;
        }
        toDegrees = x.a(this.d, toDegrees, 0.1f);
        while (toDegrees < 0.0f) {
            toDegrees += 360.0f;
        }
        while (toDegrees >= 360.0f) {
            toDegrees -= 360.0f;
        }
        this.d = toDegrees;
        this.a.e = toDegrees;
        this.a.a(toDegrees);
    }
}