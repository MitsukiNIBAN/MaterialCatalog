package com.satou.materialcatalog.presenter;

import android.app.Activity;
import android.util.Log;

import com.satou.materialcatalog.helper.DatabaseHelper;
import com.satou.materialcatalog.presenter.contract.SelectContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mitsuki on 2018/3/12.
 */

public class SelectPresenter implements SelectContract.Presenter {
    private final String TAG = "SelectPresenter";
    private Activity activity;
    private SelectContract.View mView;

    private List<String> data;

    public SelectPresenter(Activity ctx, SelectContract.View view) {
        this.activity = ctx;
        this.mView = view;
        data = new ArrayList<>();
    }

    @Override
    public void delItem(String str, int page) {
        if (str == null) return;
        mView.showLoading();
        //一系列数据库操作
        Observable.just(DatabaseHelper.getInstance().delNormalData(str, page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> {
                    mView.hideLoading();
                    if (i == 0) {
                        mView.showToast("删除失败");
                    } else {
                        data.remove(str);
                        mView.refreshData(data);
                        mView.showToast("删除成功");
                    }
                });

    }

    @Override
    public void addItem(String str, int page) {
        Log.e(TAG, "addItem:" + str);
        if (str == null) return;
        mView.showLoading();
        //一系列数据库操作
        Observable.just(DatabaseHelper.getInstance().addNormalData(str, page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> {
                    mView.hideLoading();
                    if (l < 0) {
                        mView.showToast("添加失败");
                    } else {
                        data.add(str);
                        mView.refreshData(data);
                        mView.showToast("添加成功");
                    }
                });
    }

    @Override
    public void loadData(int page) {
        mView.showLoading();
        Observable.just(DatabaseHelper.getInstance().getNormalData(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d -> {
                    mView.hideLoading();
                    if (d.size() <= 0) {
                        mView.showToast("暂无数据");
                    } else {
                        data.addAll(d);
                        mView.refreshData(data);
                    }
                });
    }
}
