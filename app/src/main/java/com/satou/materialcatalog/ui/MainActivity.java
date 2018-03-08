package com.satou.materialcatalog.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.satou.materialcatalog.R;
import com.satou.materialcatalog.presenter.MainPresenter;
import com.satou.materialcatalog.presenter.contract.MainContract;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private EditText etName;
    private EditText etS;
    private EditText etEp;
    private EditText etType;
    private EditText etEnv;
    private EditText etTime;
    private TextView tvTime;

    private SeekBar sbTimeLine;
    private Button btnSub;

    private String str = "";

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this, this);
        init();
        setListener();
    }

    private void init() {
        etName = findViewById(R.id.et_name);
        etS = findViewById(R.id.et_s);
        etEp = findViewById(R.id.et_ep);
        etType = findViewById(R.id.et_type);
        etEnv = findViewById(R.id.et_env);
        etTime = findViewById(R.id.et_time);
        tvTime = findViewById(R.id.tv_time);

        sbTimeLine = findViewById(R.id.sb_time_line);
        btnSub = findViewById(R.id.btn_sub);
    }

    private void setListener() {
        btnSub.setOnClickListener(view -> mPresenter.sub());

        sbTimeLine.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvTime.setText(":" + (i<10?"0"+i:i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }
}
