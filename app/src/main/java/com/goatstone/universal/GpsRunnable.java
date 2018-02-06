package com.goatstone.universal;

/**
 * Created by bhavdip on 2/5/18.
 */
class GpsRunnable implements Runnable {
    final LocationHelper mLocationHelper;

    GpsRunnable(LocationHelper locationHelperVar) {
        this.mLocationHelper = locationHelperVar;
    }

    public void run() {
        if (System.currentTimeMillis() - this.mLocationHelper.lastLocationReqTime > 30000) {
            if (this.mLocationHelper.isNetworkEnable && !this.mLocationHelper.g) {
                this.mLocationHelper.requestForNetworkLocation();
            }
        } else if (this.mLocationHelper.g) {
            this.mLocationHelper.resetNetworkListener();
        }
        this.mLocationHelper.mHandler.postDelayed(this, 7500);
    }
}