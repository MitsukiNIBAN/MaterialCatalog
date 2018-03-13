package com.satou.materialcatalog.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;

import com.satou.materialcatalog.R;

/**
 * Created by Mitsuki on 2018/3/13.
 */

public class InputDialog extends Dialog {

    private EditText etInput;
    private Button cancelBtn;
    private Button confirmBtn;
    private ConfirmClick confirmClick;

    public InputDialog(Context context) {
        super(context, R.style.DialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input);
        setCanceledOnTouchOutside(false);

        etInput = findViewById(R.id.et_input);
        cancelBtn = findViewById(R.id.negativeButton);
        confirmBtn = findViewById(R.id.positiveButton);

        cancelBtn.setOnClickListener(view -> dismiss());
        confirmBtn.setOnClickListener(view -> {
            dismiss();
            if (confirmClick != null) {
                confirmClick.confirm(etInput.getText().toString());
            }
        });

    }

    public void setConfirmClick(ConfirmClick confirmClick) {
        this.confirmClick = confirmClick;
    }

    interface ConfirmClick {
        void confirm(String str);
    }
}
