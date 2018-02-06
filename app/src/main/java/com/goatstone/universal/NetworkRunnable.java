package com.goatstone.universal;

/**
 * Created by bhavdip on 2/5/18.
 */

class NetworkRunnable implements Runnable {
    final LocationHelper mLocationHelper;
    private long b = Long.MAX_VALUE;

    NetworkRunnable(LocationHelper locationHelperVar) {
        this.mLocationHelper = locationHelperVar;
    }

    public void run() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mLocationHelper.lastLocationReqTime > 30000 && this.mLocationHelper.f) {
            this.mLocationHelper.resetGPSListener();
            this.b = currentTimeMillis;
        } else if (currentTimeMillis - this.b > 90000 && this.mLocationHelper.h) {
            this.mLocationHelper.requestForGPSLocation();
            this.b = Long.MAX_VALUE;
        }
        this.mLocationHelper.mHandler.postDelayed(this, 7500);
    }
}