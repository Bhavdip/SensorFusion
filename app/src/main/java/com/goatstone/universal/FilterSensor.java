package com.goatstone.universal;

/**
 * Created by bhavdip on 2/5/18.
 */

public class FilterSensor {
    public static float a(float f, float f2, float f3) {
        return ((f2 - f) * f3) + f;
    }

    public static float[] a(float[] fArr, float[] fArr2, float[] fArr3, float f) {
        if (fArr3 == null) {
            fArr3 = new float[fArr2.length];
        }
        for (int i = 0; i < fArr2.length; i++) {
            fArr3[i] = a(fArr[i], fArr2[i], f);
            fArr[i] = fArr3[i];
        }
        return fArr3;
    }
}