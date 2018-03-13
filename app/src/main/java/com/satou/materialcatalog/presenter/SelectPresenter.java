package com.satou.materialcatalog.presenter;

import android.app.Activity;

import com.satou.materialcatalog.presenter.contract.SelectContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Mitsuki on 2018/3/12.
 */

public class SelectPresenter implements SelectContract.Presenter {
    private Activity activity;
    private SelectContract.View mView;

    public SelectPresenter(Activity ctx, SelectContract.View view) {
        this.activity = ctx;
        this.mView = view;
    }

    @Override
    public void delItem(String str) {
        if (str == null) return;
        mView.showLoading();
        //一系列数据库操作
        mView.hideLoading();
        mView.showToast("操作结果");
    }

    @Override
    public void addItem(String str) {
        if (str == null) return;
        mView.showLoading();
        //一系列数据库操作
        mView.hideLoading();
        mView.showToast("操作结果");
    }

    @Override
    public void loadData() {
        mView.showLoading();
        //一阵数据库读取获取数据
        //模拟数据
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("NEW " + i);
        }
        mView.refreshData(data);
        mView.hideLoading();
        mView.showToast("操作结果");
    }
}
