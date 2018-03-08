package com.satou.materialcatalog.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Mitsuki on 2018/3/8.
 */

public class SplashActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
