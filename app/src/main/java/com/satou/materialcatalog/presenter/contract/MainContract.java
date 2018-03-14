package com.satou.materialcatalog.presenter.contract;

import android.widget.TextView;

import com.satou.materialcatalog.model.entity.SaveStruct;
import com.satou.materialcatalog.widget.ConfirmDialog;
import com.satou.materialcatalog.widget.DelDialog;

import java.util.List;

/**
 * Created by Mitsuki on 2018/3/8.
 */

public interface MainContract {
    interface View{
        void showLoading();
        void hideLoading();
        void showToast(String str);
        boolean checkTextView();
        void clickTextView(TextView tv, int id, boolean isSelf);
        void showConfirmDialog(String str, ConfirmDialog.PositiveOnClick click);
        void refreshTypeList(List<String> data);
        void refreshSceneList(List<String> data);
        void clearPage();
        void showDel(DelDialog.DelClick click);
    }
    interface Presenter{
        void sub();
        void addType(String data);
        void addScene(String data);
        void removeType(String data);
        void removeScene(String data);
        Boolean haveType();
        Boolean haveScene();

        void setName(String name);
        void setSeason(String s);
        void setEpisode(String ep);
        void setType();
        void setScene();
        void setTime(String time);

        void clearData();
    }
}
