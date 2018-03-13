package com.satou.materialcatalog.presenter.contract;

import java.util.List;

/**
 * Created by Mitsuki on 2018/3/12.
 */

public interface SelectContract {
    interface View{
        void cancel();
        void showInputDialog();
        void showLoading();
        void hideLoading();
        void showDelDialog();
        void hideDelDialog();
        void showConfirmDialog();
        void hideConfirmDialog();
        void refreshData(List<String> data);
    }
    interface Presenter{
        void delItem();
        void addItem();
        void loadData();
    }
}
