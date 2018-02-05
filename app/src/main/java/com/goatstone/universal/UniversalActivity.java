package com.goatstone.universal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.goatstone.R;

/**
 * Created by bhavdip on 2/6/18.
 */

public class UniversalActivity extends AppCompatActivity {
    private CompassFragment d = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universal);
        if (findViewById(R.id.compass_fragment_container) != null && savedInstanceState == null) {
            this.d = new CompassFragment();
            this.d.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.compass_fragment_container, this.d, getString(R.string.tag_compass_fragment)).commit();
        }
    }
}
