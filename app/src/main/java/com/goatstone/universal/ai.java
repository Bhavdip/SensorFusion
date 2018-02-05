package com.goatstone.universal;

/**
 * Created by bhavdip on 2/5/18.
 */

class ai implements Runnable {
    final /* synthetic */ ae a;
    private long b = Long.MAX_VALUE;

    ai(ae aeVar) {
        this.a = aeVar;
    }

    public void run() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.a.j > 30000 && this.a.f) {
            this.a.h();
            this.b = currentTimeMillis;
        } else if (currentTimeMillis - this.b > 90000 && this.a.h) {
            this.a.f();
            this.b = Long.MAX_VALUE;
        }
        this.a.o.postDelayed(this, 7500);
    }
}