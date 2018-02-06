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
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.goatstone.R;

import java.util.ArrayList;

import static android.view.Surface.ROTATION_0;
import static android.view.Surface.ROTATION_180;
import static android.view.Surface.ROTATION_270;
import static android.view.Surface.ROTATION_90;

/**
 * Created by bhavdip on 2/5/18.
 */

public class CompassView extends View {
  private float bearingValue;
  private ArrayList countryLocationList;
  private float px;
  private float py;
  private float padding;
  private Paint textRedColor;
  private Paint textWhiteColor;
  private Paint textScaleStyle;
  private Paint dotCirceStyle;
  private Paint paintBaseColor;

  private Paint countryNeedleStyle;
  private Path countryPath;

  private Paint linePaint;
  private Path linePath;

  private float radius;
  private int displayRotation;

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
    canvas.rotate(-this.bearingValue, this.px, this.py);
    canvas.save();

    float dimension = this.radius * resources.getDimension(R.dimen.compass_dot_radius);
    float textSize = ((this.py - this.padding) + this.textWhiteColor.getTextSize()) - dimension;
    for (int i = 10; i <= 360; i += 10) {
      canvas.rotate(10.0f, this.px, this.py);
      //canvas.drawCircle(this.px, textSize, dimension, this.lastLocationReqTime);
      canvas.drawText("" + i, this.px, textSize + this.textScaleStyle.getTextSize(), this.textScaleStyle);
      //this will print init circle dot
            /*if (resetNetworkListener % 90 != 0) {
                //canvas.drawCircle(this.px, textSize, dimension, this.lastLocationReqTime);
                canvas.drawText(""+resetNetworkListener, this.px, textSize + this.locationAccuracy.getTextSize(), this.locationAccuracy);
            }*/
    }
    /**
     * canvas.restore() is saying that I want to revert my Canvas's adjustments back to the last time I called cavas.save()
     */
    canvas.restore();
    canvas.save();

    canvas.drawText(resources.getString(R.string.direction_n), this.px,
        (this.py - this.padding) + this.textRedColor.getTextSize(), this.textRedColor);
    canvas.rotate(90.0f, this.px, this.py);
    canvas.drawText(resources.getString(R.string.direction_e), this.px,
        (this.py - this.padding) + this.textWhiteColor.getTextSize(), this.textWhiteColor);
    canvas.rotate(90.0f, this.px, this.py);
    canvas.drawText(resources.getString(R.string.direction_s), this.px,
        (this.py - this.padding) + this.textWhiteColor.getTextSize(), this.textWhiteColor);
    canvas.rotate(90.0f, this.px, this.py);
    canvas.drawText(resources.getString(R.string.direction_w), this.px,
        (this.py - this.padding) + this.textWhiteColor.getTextSize(), this.textWhiteColor);
    canvas.restore();

    float textSize2 = ((-this.padding) + this.textScaleStyle.getTextSize()) + (resources.getDimension(
        R.dimen.compass_outer_circle_strokewidth) * this.radius);
    canvas.rotate(45.0f, this.px, this.py);
    canvas.drawText(
        resources.getString(R.string.direction_n) + resources.getString(R.string.direction_e),
        this.px, this.py + textSize2, this.textScaleStyle);
    canvas.rotate(90.0f, this.px, this.py);
    canvas.drawText(
        resources.getString(R.string.direction_s) + resources.getString(R.string.direction_e),
        this.px, this.py + textSize2, this.textScaleStyle);
    canvas.rotate(90.0f, this.px, this.py);
    canvas.drawText(
        resources.getString(R.string.direction_s) + resources.getString(R.string.direction_w),
        this.px, this.py + textSize2, this.textScaleStyle);
    canvas.rotate(90.0f, this.px, this.py);
    canvas.drawText(
        resources.getString(R.string.direction_n) + resources.getString(R.string.direction_w),
        this.px, textSize2 + this.py, this.textScaleStyle);
    canvas.restore();

    canvas.save();
    canvas.rotate(this.displayRotation, this.px, this.py);
    canvas.drawPath(this.linePath, this.linePaint);
    canvas.restore();

    return canvas;
  }

  private void init() {
    Resources resources = getResources();
    this.paintBaseColor = new Paint(1);
    this.paintBaseColor.setColor(resources.getColor(R.color.compass_base_color));
    this.paintBaseColor.setStyle(Style.FILL);


    this.dotCirceStyle = new Paint(1);
    this.dotCirceStyle.setColor(resources.getColor(R.color.compass_base_color));
    this.dotCirceStyle.setStyle(Style.STROKE);

    this.textRedColor = new Paint(1);
    this.textRedColor.setColor(resources.getColor(R.color.text_red));
    this.textRedColor.setTextAlign(Align.CENTER);
    this.textRedColor.setTypeface(Typeface.DEFAULT_BOLD);

    this.textWhiteColor = new Paint(1);
    this.textWhiteColor.setColor(resources.getColor(R.color.compass_base_color));
    this.textWhiteColor.setTextAlign(Align.CENTER);

    this.textScaleStyle = new Paint(this.textWhiteColor);

    this.countryNeedleStyle = new Paint(1);
    this.countryNeedleStyle.setStyle(Style.FILL);
    this.countryNeedleStyle.setColor(-1);

    this.countryPath = new Path();

    linePath = new Path();
    linePaint = new Paint();
    linePaint.setColor(Color.RED); // Set the color
    linePaint.setStyle(Style.FILL); // We will change it at the end.
    linePaint.setAntiAlias(true);
    linePaint.setShadowLayer(8.0f, 0.1f, 0.1f, Color.GRAY); // Shadow of the needle

    findDisplayRotation();

    calculateTextSize();
  }

  private Canvas calculateTextSize(Canvas canvas) {
    float dimension =
        getResources().getDimension(R.dimen.compass_outer_circle_strokewidth) * this.radius;
    float f = this.padding - (dimension / 2.0f);
    this.paintBaseColor.setStrokeWidth(dimension);
    this.paintBaseColor.setStyle(Style.STROKE);
    canvas.drawCircle(this.px, this.py, f, this.paintBaseColor);
    this.paintBaseColor.setStyle(Style.FILL);
    return canvas;
  }

  private void calculateTextSize() {
    Resources resources = getResources();
    this.radius = this.padding / resources.getDimension(R.dimen.compass_base_radius);
    this.textRedColor.setTextSize(resources.getDimension(R.dimen.compass_main_fontsize) * this.radius);
    this.textWhiteColor.setTextSize(resources.getDimension(R.dimen.compass_main_fontsize) * this.radius);
    this.textScaleStyle.setTextSize(resources.getDimension(R.dimen.compass_sub_fontsize) * this.radius);
    this.dotCirceStyle.setStrokeWidth(resources.getDimension(R.dimen.compass_dot_radius) * this.radius);
    calcNeedlePosition();
  }

  private Canvas c(Canvas canvas) {
    if (this.countryLocationList != null) {
      for (int size = this.countryLocationList.size() - 1; size >= 0; size--) {
        CompassLocation compassLocation = (CompassLocation) this.countryLocationList.get(size);
        this.countryNeedleStyle.setColor(compassLocation.b());
        canvas.save();
        canvas.rotate(compassLocation.getBearing(), this.px, this.py);
        canvas.drawPath(this.countryPath, this.countryNeedleStyle);
        canvas.restore();
      }
    }
    return canvas;
  }

  private void calcNeedlePosition() {

    Resources resources = getResources();
    float dimension = resources.getDimension(R.dimen.compass_needle_length) * this.radius;
    float dimension2 = resources.getDimension(R.dimen.compass_needle_width) * this.radius;
    float dimension3 = resources.getDimension(R.dimen.compass_center_radius) * this.radius;
    float f = (dimension2 - dimension3) / 2.0f;

    this.countryPath.reset();
    this.countryPath.moveTo(this.px, this.py - dimension);
    this.countryPath.lineTo(this.px + (dimension2 / 2.0f), this.py);
    this.countryPath.arcTo(new RectF(this.px + dimension3, this.py, this.px + (dimension2 / 2.0f),
        this.py + (f / 2.0f)), 0.0f, 180.0f);
    this.countryPath.lineTo(this.px, this.py - (dimension / 2.0f));
    this.countryPath.lineTo(this.px - dimension3, this.py);
    this.countryPath.arcTo(new RectF(this.px - (dimension2 / 2.0f), this.py, this.px - dimension3,
        (f / 2.0f) + this.py), 0.0f, 180.0f);
    this.countryPath.close();

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

  private void findDisplayRotation() {
    switch (((Activity) getContext()).getWindowManager().getDefaultDisplay().getRotation()) {
      case ROTATION_90:
        this.displayRotation = 90;
        return;
      case ROTATION_180:
        this.displayRotation = 180;
        return;
      case ROTATION_270:
        this.displayRotation = 270;
        return;
      default:
        this.displayRotation = ROTATION_0;
        return;
    }
  }

  private void calculateDegree(Canvas canvas) {
    canvas.drawCircle(this.px, this.py,
        getResources().getDimension(R.dimen.compass_center_radius) * this.radius, this.paintBaseColor);
  }

  public CompassView setBearing(float bearingValue) {
    this.bearingValue = bearingValue;
    findDisplayRotation();
    invalidate();
    return this;
  }

  public CompassView addCompassLocationList(ArrayList arrayList) {
    this.countryLocationList = arrayList;
    return this;
  }

  public int getCorrectiveAngle() {
    return this.displayRotation;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.save();
    canvas.rotate((float) (-this.displayRotation), this.px, this.py);
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
    this.padding = Math.min(this.px - paddingLeft, this.py - paddingTop);
    calculateTextSize();
  }
}
