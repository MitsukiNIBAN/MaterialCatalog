package com.satou.materialcatalog.base;

import com.satou.materialcatalog.helper.DatabaseHelper;

/**
 * Created by Mitsuki on 2018/3/14.
 */

public class Application extends android.app.Application {
    private static Application INSTANCE;

    public synchronized static Application getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
//        DatabaseHelper.getInstance().getReadableDatabase();
    }
}
