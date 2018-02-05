package com.goatstone.universal;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by bhavdip on 2/5/18.
 */

class ap implements SensorEventListener {
    final /* synthetic */ ao a;
    private float[] b = new float[3];
    private float[] c = new float[3];
    private float[] d = new float[3];
    private float[] e = new float[3];
    private float[] f = new float[16];
    private float[] g = new float[3];

    ap(ao aoVar) {
        this.a = aoVar;
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case 1:
                this.e = x.a(this.c, sensorEvent.values, this.e, 0.1f);
                break;
            case 2:
                this.d = x.a(this.b, sensorEvent.values, this.d, 0.1f);
                break;
        }
        SensorManager.getRotationMatrix(this.f, null, this.e, this.d);
        SensorManager.getOrientation(this.f, this.g);
        float toDegrees = (float) Math.toDegrees((double) this.g[0]);
        while (toDegrees < 0.0f) {
            toDegrees += 360.0f;
        }
        while (toDegrees >= 360.0f) {
            toDegrees -= 360.0f;
        }
        if (this.a.d) {
            this.a.b(toDegrees);
        } else {
            this.a.a(toDegrees);
        }
    }
}