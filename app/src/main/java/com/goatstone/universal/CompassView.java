package com.goatstone.universal;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.goatstone.sensorFusion.R;

import java.util.ArrayList;

/**
 * Created by bhavdip on 2/5/18.
 */

public class CompassView extends View {
    private float a;
    private ArrayList b;
    private float c;
    private float d;
    private float e;
    private Paint f;
    private Paint g;
    private Paint h;
    private Paint i;
    private Paint j;
    private Paint k;
    private Path l;
    private float m;
    private int n;

    public CompassView(Context context) {
        super(context);
        a();
    }

    public CompassView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    private Canvas a(Canvas canvas) {
        Resources resources = getResources();
        canvas.save();
        canvas.rotate(-this.a, this.c, this.d);
        canvas.save();
        float dimension = this.m * resources.getDimension(R.dimen.compass_dot_radius);
        float textSize = ((this.d - this.e) + this.g.getTextSize()) - dimension;
        for (int i = 10; i < 360; i += 10) {
            canvas.rotate(10.0f, this.c, this.d);
            if (i % 90 != 0) {
                canvas.drawCircle(this.c, textSize, dimension, this.j);
            }
        }
        canvas.restore();
        canvas.save();
        canvas.drawText(resources.getString(R.string.direction_n), this.c, (this.d - this.e) + this.f.getTextSize(), this.f);
        canvas.rotate(90.0f, this.c, this.d);
        canvas.drawText(resources.getString(R.string.direction_e), this.c, (this.d - this.e) + this.g.getTextSize(), this.g);
        canvas.rotate(90.0f, this.c, this.d);
        canvas.drawText(resources.getString(R.string.direction_s), this.c, (this.d - this.e) + this.g.getTextSize(), this.g);
        canvas.rotate(90.0f, this.c, this.d);
        canvas.drawText(resources.getString(R.string.direction_w), this.c, (this.d - this.e) + this.g.getTextSize(), this.g);
        canvas.restore();
        float textSize2 = ((-this.e) + this.h.getTextSize()) + (resources.getDimension(R.dimen.compass_outer_circle_strokewidth) * this.m);
        canvas.rotate(45.0f, this.c, this.d);
        canvas.drawText(resources.getString(R.string.direction_n) + resources.getString(R.string.direction_e), this.c, this.d + textSize2, this.h);
        canvas.rotate(90.0f, this.c, this.d);
        canvas.drawText(resources.getString(R.string.direction_s) + resources.getString(R.string.direction_e), this.c, this.d + textSize2, this.h);
        canvas.rotate(90.0f, this.c, this.d);
        canvas.drawText(resources.getString(R.string.direction_s) + resources.getString(R.string.direction_w), this.c, this.d + textSize2, this.h);
        canvas.rotate(90.0f, this.c, this.d);
        canvas.drawText(resources.getString(R.string.direction_n) + resources.getString(R.string.direction_w), this.c, textSize2 + this.d, this.h);
        canvas.restore();
        return canvas;
    }

    private void a() {
        Resources resources = getResources();
        this.j = new Paint(1);
        this.j.setColor(resources.getColor(R.color.compass_base_color));
        this.j.setStyle(Style.FILL);
        this.f = new Paint(1);
        this.f.setColor(resources.getColor(R.color.text_red));
        this.f.setTextAlign(Align.CENTER);
        this.f.setTypeface(Typeface.DEFAULT_BOLD);
        this.g = new Paint(1);
        this.g.setColor(resources.getColor(R.color.compass_base_color));
        this.g.setTextAlign(Align.CENTER);
        this.h = new Paint(this.g);
        this.i = new Paint(1);
        this.i.setColor(resources.getColor(R.color.compass_base_color));
        this.i.setStyle(Style.STROKE);
        this.k = new Paint(1);
        this.k.setStyle(Style.FILL);
        this.k.setColor(-1);
        this.l = new Path();
        d();
        b();
    }

    private Canvas b(Canvas canvas) {
        float dimension = getResources().getDimension(R.dimen.compass_outer_circle_strokewidth) * this.m;
        float f = this.e - (dimension / 2.0f);
        this.j.setStrokeWidth(dimension);
        this.j.setStyle(Style.STROKE);
        canvas.drawCircle(this.c, this.d, f, this.j);
        this.j.setStyle(Style.FILL);
        return canvas;
    }

    private void b() {
        Resources resources = getResources();
        this.m = this.e / resources.getDimension(R.dimen.compass_base_radius);
        this.f.setTextSize(resources.getDimension(R.dimen.compass_main_fontsize) * this.m);
        this.g.setTextSize(resources.getDimension(R.dimen.compass_main_fontsize) * this.m);
        this.h.setTextSize(resources.getDimension(R.dimen.compass_sub_fontsize) * this.m);
        this.i.setStrokeWidth(resources.getDimension(R.dimen.compass_dot_radius) * this.m);
        c();
    }

    private Canvas c(Canvas canvas) {
        if (this.b != null) {
            for (int size = this.b.size() - 1; size >= 0; size--) {
                CompassLocation compassLocation = (CompassLocation) this.b.get(size);
                this.k.setColor(compassLocation.b());
                canvas.save();
                canvas.rotate(compassLocation.getBearing(), this.c, this.d);
                canvas.drawPath(this.l, this.k);
                canvas.restore();
            }
        }
        return canvas;
    }

    private void c() {
        Resources resources = getResources();
        float dimension = resources.getDimension(R.dimen.compass_needle_length) * this.m;
        float dimension2 = resources.getDimension(R.dimen.compass_needle_width) * this.m;
        float dimension3 = resources.getDimension(R.dimen.compass_center_radius) * this.m;
        float f = (dimension2 - dimension3) / 2.0f;
        this.l.reset();
        this.l.moveTo(this.c, this.d - dimension);
        this.l.lineTo(this.c + (dimension2 / 2.0f), this.d);
        this.l.arcTo(new RectF(this.c + dimension3, this.d, this.c + (dimension2 / 2.0f), this.d + (f / 2.0f)), 0.0f, 180.0f);
        this.l.lineTo(this.c, this.d - (dimension / 2.0f));
        this.l.lineTo(this.c - dimension3, this.d);
        this.l.arcTo(new RectF(this.c - (dimension2 / 2.0f), this.d, this.c - dimension3, (f / 2.0f) + this.d), 0.0f, 180.0f);
        this.l.close();
    }

    private void d() {
        switch (((Activity) getContext()).getWindowManager().getDefaultDisplay().getRotation()) {
            case 1:
                this.n = 90;
                return;
            case 2:
                this.n = 180;
                return;
            case 3:
                this.n = 270;
                return;
            default:
                this.n = 0;
                return;
        }
    }

    private void d(Canvas canvas) {
        canvas.drawCircle(this.c, this.d, getResources().getDimension(R.dimen.compass_center_radius) * this.m, this.j);
    }

    public CompassView a(float f) {
        this.a = f;
        d();
        invalidate();
        return this;
    }

    public CompassView a(ArrayList arrayList) {
        this.b = arrayList;
        return this;
    }

    public int getCorrectiveAngle() {
        return this.n;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.rotate((float) (-this.n), this.c, this.d);
        b(canvas);
        a(canvas);
        c(canvas);
        d(canvas);
        canvas.restore();
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        float paddingLeft = (float) getPaddingLeft();
        float paddingTop = (float) getPaddingTop();
        float paddingBottom = (float) getPaddingBottom();
        this.c = (((((float) i) - paddingLeft) - ((float) getPaddingRight())) / 2.0f) + paddingLeft;
        this.d = (((((float) i2) - paddingTop) - paddingBottom) / 2.0f) + paddingTop;
        this.e = Math.min(this.c - paddingLeft, this.d - paddingTop);
        b();
    }
}
