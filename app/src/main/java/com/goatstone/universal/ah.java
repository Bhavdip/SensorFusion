package com.goatstone.universal;

/**
 * Created by bhavdip on 2/5/18.
 */

class ah implements Runnable {
    final /* synthetic */ ae a;

    ah(ae aeVar) {
        this.a = aeVar;
    }

    public void run() {
        if (System.currentTimeMillis() - this.a.j > 30000) {
            if (this.a.l && !this.a.g) {
                this.a.g();
            }
        } else if (this.a.g) {
            this.a.i();
        }
        this.a.o.postDelayed(this, 7500);
    }
}