package com.goatstone.universal;

/**
 * Created by bhavdip on 2/5/18.
 */


class b implements Runnable {
    final CompassFragment a;

    b(CompassFragment compassFragment) {
        this.a = compassFragment;
    }

    public void run() {
        this.a.d();
        this.a.m.postDelayed(this, 1000);
    }
}