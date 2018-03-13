package com.satou.materialcatalog.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;


import com.satou.materialcatalog.R;

/**
 * Created by Mitsuki on 2018/3/13.
 */

public class LoadingDialog extends Dialog{
    public LoadingDialog(Context context) {
        super(context, R.style.DialogStyle);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(false);
    }
}
