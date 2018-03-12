package com.satou.materialcatalog.presenter;

import android.app.Activity;

import com.satou.materialcatalog.presenter.contract.SelectContract;

/**
 * Created by Mitsuki on 2018/3/12.
 */

public class SelectPresenter implements SelectContract.Presenter{
    private Activity activity;
    private SelectContract.View mView;
    public SelectPresenter(Activity ctx, SelectContract.View view){
        this.activity = ctx;
        this.mView = view;
    }
}
