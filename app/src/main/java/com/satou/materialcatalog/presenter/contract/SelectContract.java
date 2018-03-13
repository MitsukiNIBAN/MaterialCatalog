package com.satou.materialcatalog.presenter.contract;

import com.satou.materialcatalog.widget.ConfirmDialog;
import com.satou.materialcatalog.widget.DelDialog;
import com.satou.materialcatalog.widget.InputDialog;

import java.util.List;

/**
 * Created by Mitsuki on 2018/3/12.
 */

public interface SelectContract {
    interface View{
        void cancel();
        void showInputDialog(InputDialog.ConfirmClick click);
        void showLoading();
        void hideLoading();
        void showDelDialog(DelDialog.DelClick click);
        void showConfirmDialog(String str, ConfirmDialog.PositiveOnClick click);
        void refreshData(List<String> data);
        void showToast(String str);
    }
    interface Presenter{
        void delItem(String str);
        void addItem(String str);
        void loadData();
    }
}
