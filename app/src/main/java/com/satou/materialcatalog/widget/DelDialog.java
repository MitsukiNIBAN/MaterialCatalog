package com.satou.materialcatalog.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.satou.materialcatalog.R;

/**
 * Created by Mitsuki on 2018/3/13.
 */

public class DelDialog extends Dialog {
    private TextView tvDel;
    private DelClick delClick;

    public DelDialog(Context context) {
        super(context, R.style.DialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_del);
        setCanceledOnTouchOutside(false);

        tvDel = findViewById(R.id.tv_del);
        tvDel.setOnClickListener(view -> {
            dismiss();
            if (delClick != null) {
                delClick.del();
            }
        });
    }

    public void setDelClick(DelClick delClick) {
        this.delClick = delClick;
    }

    public interface DelClick {
        void del();
    }
}
