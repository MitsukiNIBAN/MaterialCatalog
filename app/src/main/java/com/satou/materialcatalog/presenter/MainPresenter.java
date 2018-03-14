package com.satou.materialcatalog.presenter;

import android.content.Context;

import com.satou.materialcatalog.helper.DatabaseHelper;
import com.satou.materialcatalog.model.entity.SaveStruct;
import com.satou.materialcatalog.presenter.contract.MainContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mitsuki on 2018/3/8.
 */

public class MainPresenter implements MainContract.Presenter {
    private Context context;
    private MainContract.View mView;
    private List<String> typeList;
    private List<String> sceneList;
    private SaveStruct saveStruct;

    public MainPresenter(Context ctx, MainContract.View view) {
        this.context = ctx;
        this.mView = view;
        typeList = new ArrayList<>();
        sceneList = new ArrayList<>();
        saveStruct = new SaveStruct();
    }

    @Override
    public void sub() {
        mView.showLoading();
        Observable.just(DatabaseHelper.getInstance().subData(saveStruct))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> {
                    mView.hideLoading();
                    if (l == 1) {
                        mView.showToast("插入成功");
                        mView.clearPage();
                    } else if (l == -1) {
                        mView.showToast("插入失败");
                    } else {
                        mView.showToast("出现异常");
                    }
                });
    }

    @Override
    public void addType(String data) {
        typeList.add(data);
        mView.refreshTypeList(typeList);
    }

    @Override
    public void addScene(String data) {
        sceneList.add(data);
        mView.refreshSceneList(sceneList);
    }

    @Override
    public void removeType(String data) {
        try {
            typeList.remove(data);
            mView.refreshTypeList(typeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeScene(String data) {
        try {
            sceneList.remove(data);
            mView.refreshSceneList(sceneList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean haveType() {
        return typeList.size() > 0;
    }

    @Override
    public Boolean haveScene() {
        return sceneList.size() > 0;
    }

    @Override
    public void setName(String name) {
        saveStruct.setName(name);
    }

    @Override
    public void setSeason(String s) {
        saveStruct.setSeason(s);
    }

    @Override
    public void setEpisode(String ep) {
        saveStruct.setEpisode(ep);
    }

    @Override
    public void setType() {
        saveStruct.setType(typeList);
    }

    @Override
    public void setScene() {
        saveStruct.setScene(sceneList);
    }

    @Override
    public void setTime(String time) {
        saveStruct.setTime(time);
    }

    @Override
    public void clearData() {
        saveStruct.reset();
        typeList.clear();
        sceneList.clear();
        mView.refreshTypeList(typeList);
        mView.refreshSceneList(sceneList);
    }
}
