package com.satou.materialcatalog.presenter.contract;

import com.satou.materialcatalog.entity.SaveStruct;

/**
 * Created by Mitsuki on 2018/3/8.
 */

public interface MainContract {
    interface View{
        void showLoading();
        void dismissLoading();
        void showToast(String str);
        SaveStruct checkTextView();
    }
    interface Presenter{
        void sub();
    }
}
