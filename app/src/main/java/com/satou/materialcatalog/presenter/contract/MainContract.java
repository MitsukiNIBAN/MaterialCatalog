package com.satou.materialcatalog.presenter.contract;

import android.widget.TextView;

import com.satou.materialcatalog.entity.SaveStruct;
import com.satou.materialcatalog.widget.ConfirmDialog;

import java.util.List;

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
        void showConfirmDialog(String str, ConfirmDialog.PositiveOnClick click);
        void refreshTypeList(List<String> data);
        void refreshSceneList(List<String> data);
        void clearPage();
    }
    interface Presenter{
        void sub(SaveStruct saveStruct);
    }
}
