package com.satou.materialcatalog.presenter.contract;

import android.widget.TextView;

import com.satou.materialcatalog.entity.SaveStruct;

/**
 * Created by Mitsuki on 2018/3/8.
 */

public interface MainContract {
    interface View{
        void showLoading();
        void hideLoading();
        void showToast(String str);
        SaveStruct checkTextView();
        void clickTextView(TextView tv, int id, boolean isSelf);
    }
    interface Presenter{
        void sub();
    }
}
