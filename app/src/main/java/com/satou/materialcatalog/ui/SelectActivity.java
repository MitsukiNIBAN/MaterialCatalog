package com.satou.materialcatalog.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.satou.materialcatalog.R;
import com.satou.materialcatalog.presenter.SelectPresenter;
import com.satou.materialcatalog.presenter.contract.SelectContract;

/**
 * Created by Mitsuki on 2018/3/12.
 */

public class SelectActivity extends AppCompatActivity implements SelectContract.View{
    private SelectContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        mPresenter = new SelectPresenter(this, this);
    }
}
