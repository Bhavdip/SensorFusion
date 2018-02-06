package com.goatstone.universal;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.goatstone.R;

import java.util.ArrayList;

/**
 * Created by bhavdip on 2/5/18.
 */

public class CompassView extends View {
  private float a;
  private ArrayList b;
  private float px;
  private float py;
  private float e;
  private Paint f;
  private Paint g;
  private Paint h;
  private Paint i;
  private Paint j;
  private Paint k;
  private Path l;

  private Paint linePaint;
  private Path linePath;

  private float m;
  private int n;

  public CompassView(Context context) {
    super(context);
    init();
  }

  public CompassView(Context context, AttributeSet attributeSet) {
    super(context, attributeSet);
    init();
  }

  private Canvas init(Canvas canvas) {
    Resources resources = getResources();
    /**
     * canvas.save() is saying that I want to save the state of the current Canvas's adjustments so that I can go back to it later.
     */
    canvas.save();
    canvas.rotate(-this.a, this.px, this.py);
    canvas.save();

    float dimension = this.m * resources.getDimension(R.dimen.compass_dot_radius);
    float textSize = ((this.py - this.e) + this.g.getTextSize()) - dimension;
    for (int i = 10; i <= 360; i += 10) {
      canvas.rotate(10.0f, this.px, this.py);
      //canvas.drawCircle(this.px, textSize, dimension, this.j);
      canvas.drawText("" + i, this.px, textSize + this.h.getTextSize(), this.h);
      //this will print init circle dot
            /*if (i % 90 != 0) {
                //canvas.drawCircle(this.px, textSize, dimension, this.j);
                canvas.drawText(""+i, this.px, textSize + this.h.getTextSize(), this.h);
            }*/
    }
    /**
     * canvas.restore() is saying that I want to revert my Canvas's adjustments back to the last time I called cavas.save()
     */
    canvas.restore();
    canvas.save();

    canvas.drawText(resources.getString(R.string.direction_n), this.px,
        (this.py - this.e) + this.f.getTextSize(), this.f);
    canvas.rotate(90.0f, this.px, this.py);
    canvas.drawText(resources.getString(R.string.direction_e), this.px,
        (this.py - this.e) + this.g.getTextSize(), this.g);
    canvas.rotate(90.0f, this.px, this.py);
    canvas.drawText(resources.getString(R.string.direction_s), this.px,
        (this.py - this.e) + this.g.getTextSize(), this.g);
    canvas.rotate(90.0f, this.px, this.py);
    canvas.drawText(resources.getString(R.string.direction_w), this.px,
        (this.py - this.e) + this.g.getTextSize(), this.g);
    canvas.restore();

    float textSize2 = ((-this.e) + this.h.getTextSize()) + (resources.getDimension(
        R.dimen.compass_outer_circle_strokewidth) * this.m);
    canvas.rotate(45.0f, this.px, this.py);
    canvas.drawText(
        resources.getString(R.string.direction_n) + resources.getString(R.string.direction_e),
        this.px, this.py + textSize2, this.h);
    canvas.rotate(90.0f, this.px, this.py);
    canvas.drawText(
        resources.getString(R.string.direction_s) + resources.getString(R.string.direction_e),
        this.px, this.py + textSize2, this.h);
    canvas.rotate(90.0f, this.px, this.py);
    canvas.drawText(
        resources.getString(R.string.direction_s) + resources.getString(R.string.direction_w),
        this.px, this.py + textSize2, this.h);
    canvas.rotate(90.0f, this.px, this.py);
    canvas.drawText(
        resources.getString(R.string.direction_n) + resources.getString(R.string.direction_w),
        this.px, textSize2 + this.py, this.h);
    canvas.restore();

    canvas.save();
    canvas.rotate(this.n, this.px, this.py);
    canvas.drawPath(this.linePath, this.linePaint);
    canvas.restore();

    return canvas;
  }

  private void init() {
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

    linePath = new Path();
    linePaint = new Paint();
    linePaint.setColor(Color.RED); // Set the color
    linePaint.setStyle(Style.FILL); // We will change it at the end.
    linePaint.setAntiAlias(true);
    linePaint.setShadowLayer(8.0f, 0.1f, 0.1f, Color.GRAY); // Shadow of the needle

    calculateDegree();
    calculateTextSize();
  }

  private Canvas calculateTextSize(Canvas canvas) {
    float dimension =
        getResources().getDimension(R.dimen.compass_outer_circle_strokewidth) * this.m;
    float f = this.e - (dimension / 2.0f);
    this.j.setStrokeWidth(dimension);
    this.j.setStyle(Style.STROKE);
    canvas.drawCircle(this.px, this.py, f, this.j);
    this.j.setStyle(Style.FILL);
    return canvas;
  }

  private void calculateTextSize() {
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
        canvas.rotate(compassLocation.getBearing(), this.px, this.py);
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
    this.l.moveTo(this.px, this.py - dimension);
    this.l.lineTo(this.px + (dimension2 / 2.0f), this.py);
    this.l.arcTo(new RectF(this.px + dimension3, this.py, this.px + (dimension2 / 2.0f),
        this.py + (f / 2.0f)), 0.0f, 180.0f);
    this.l.lineTo(this.px, this.py - (dimension / 2.0f));
    this.l.lineTo(this.px - dimension3, this.py);
    this.l.arcTo(new RectF(this.px - (dimension2 / 2.0f), this.py, this.px - dimension3,
        (f / 2.0f) + this.py), 0.0f, 180.0f);
    this.l.close();

    // draw path
    linePath.reset();
    linePath.moveTo(this.px, this.py - dimension);
    linePath.lineTo(this.px + (dimension2 / 2.0f), this.py);
    linePath.arcTo(new RectF(this.px + dimension3, this.py, this.px + (dimension2 / 2.0f),
        this.py + (f / 2.0f)), 0.0f, 180.0f);
    linePath.lineTo(this.px, this.py - (dimension / 2.0f)); // next end point
    linePath.lineTo(this.px - dimension3, this.py); // next end point
    linePath.arcTo(new RectF(this.px - (dimension2 / 2.0f), this.py, this.px - dimension3,
        (f / 2.0f) + this.py), 0.0f, 180.0f);
    linePath.close();
  }

  private void calculateDegree() {
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

  private void calculateDegree(Canvas canvas) {
    canvas.drawCircle(this.px, this.py,
        getResources().getDimension(R.dimen.compass_center_radius) * this.m, this.j);
  }

  public CompassView a(float f) {
    this.a = f;
    calculateDegree();
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

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.save();
    canvas.rotate((float) (-this.n), this.px, this.py);
    calculateTextSize(canvas);
    init(canvas);
    c(canvas);
    calculateDegree(canvas);
    canvas.restore();
  }

  protected void onSizeChanged(int i, int i2, int i3, int i4) {
    super.onSizeChanged(i, i2, i3, i4);
    float paddingLeft = (float) getPaddingLeft();
    float paddingTop = (float) getPaddingTop();
    float paddingBottom = (float) getPaddingBottom();
    this.px = (((((float) i) - paddingLeft) - ((float) getPaddingRight())) / 2.0f) + paddingLeft;
    this.py = (((((float) i2) - paddingTop) - paddingBottom) / 2.0f) + paddingTop;
    this.e = Math.min(this.px - paddingLeft, this.py - paddingTop);
    calculateTextSize();
  }
}
