package com.satou.materialcatalog.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.satou.materialcatalog.R;

/**
 * Created by Mitsuki on 2018/3/13.
 */

public class ConfirmDialog extends Dialog {

    private TextView message;
    private Button positiveBtn;
    private Button negativeBtn;
    private PositiveOnClick positiveOnClick;

    public ConfirmDialog(Context context) {
        super(context, R.style.DialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);
        setCanceledOnTouchOutside(false);

        message = findViewById(R.id.tv_message);
        positiveBtn = findViewById(R.id.positiveButton);
        negativeBtn = findViewById(R.id.negativeButton);

        negativeBtn.setOnClickListener(view -> dismiss());
        positiveBtn.setOnClickListener(view -> {
            dismiss();
            if (positiveOnClick != null)
                positiveOnClick.onClick();
        });
    }

    public void setPositiveOnClick(PositiveOnClick positiveOnClick) {
        this.positiveOnClick = positiveOnClick;
    }

    public void setText(String str) {
        if (str != null)
            message.setText(str);
    }

    interface PositiveOnClick {
        void onClick();
    }
}
