package com.satou.materialcatalog.presenter;

import android.content.Context;

import com.satou.materialcatalog.entity.SaveStruct;
import com.satou.materialcatalog.presenter.contract.MainContract;

/**
 * Created by Mitsuki on 2018/3/8.
 */

public class MainPresenter implements MainContract.Presenter {
    private Context context;
    private MainContract.View mView;

    public MainPresenter(Context ctx, MainContract.View view) {
        this.context = ctx;
        this.mView = view;
    }

    @Override
    public void sub(SaveStruct saveStruct) {
        mView.showLoading();

        //以下数据正常
        //向数据库插入数据
        //插入结果
        mView.hideLoading();
        mView.showToast("插入结果");
    }

}
