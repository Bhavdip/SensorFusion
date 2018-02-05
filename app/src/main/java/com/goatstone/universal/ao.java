package com.goatstone.universal;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import java.util.ArrayList;
import java.util.Iterator;

public class ao {
    private ArrayList a = new ArrayList();
    private boolean b = false;
    private boolean c = false;
    public boolean d = false;
    public float e = 0.0f;
    private long f = Long.MAX_VALUE;
    private Context g;
    private SensorEventListener h = new ap(this);
    private SensorEventListener i = new aq(this);

    public ao(Context context) {
        this.g = context;
    }

    public void a(float f) {
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            ((ar) it.next()).a(f);
        }
    }

    public void b(float f) {
        float abs = Math.abs(f - this.e);
        while (abs > 180.0f) {
            abs = Math.abs(abs - 360.0f);
        }
        if (abs < 145.0f) {
            this.f = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - this.f > 800) {
            d();
            c();
        }
    }

    private void c() {
        SensorManager sensorManager = (SensorManager) this.g.getSystemService(Context.SENSOR_SERVICE);
        Sensor defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor defaultSensor2 = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor defaultSensor3 = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        this.d = false;
        if (defaultSensor3 != null) {
            this.d = true;
        } else if (defaultSensor2 == null) {
            return;
        } else {
            if (defaultSensor == null) {
                return;
            }
        }
        if (this.d) {
            sensorManager.registerListener(this.i, defaultSensor3, 33000);
            sensorManager.registerListener(this.h, defaultSensor, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this.h, defaultSensor2, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            sensorManager.registerListener(this.h, defaultSensor, SensorManager.SENSOR_DELAY_UI);
            sensorManager.registerListener(this.h, defaultSensor2, SensorManager.SENSOR_DELAY_UI);
        }
        this.b = true;
    }

    public void d() {
        if (this.b) {
            SensorManager sensorManager = (SensorManager) this.g.getSystemService(Context.SENSOR_SERVICE);
            sensorManager.unregisterListener(this.h, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
            sensorManager.unregisterListener(this.h, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
            sensorManager.unregisterListener(this.i, sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR));
            this.f = Long.MAX_VALUE;
            this.b = false;
        }
    }

    public void a() {
        if (!this.b) {
            c();
        }
        this.c = true;
    }

    public void a(ar arVar) {
        this.a.add(arVar);
        if (this.c && !this.b) {
            c();
        }
    }

    public void b() {
        if (this.b) {
            d();
        }
        this.c = false;
    }
}