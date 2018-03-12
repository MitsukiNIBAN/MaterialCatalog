package com.satou.materialcatalog.ui;

import android.app.usage.ConfigurationStats;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.satou.materialcatalog.Constant;
import com.satou.materialcatalog.R;
import com.satou.materialcatalog.entity.SaveStruct;
import com.satou.materialcatalog.helper.DialogHelper;
import com.satou.materialcatalog.presenter.MainPresenter;
import com.satou.materialcatalog.presenter.contract.MainContract;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private EditText etName;

    private EditText etS;
    private TextView tvOVA;

    private EditText etEp;
    private TextView tvOP;
    private TextView tvED;

    private TextView tvTypeAdd;
    private LinearLayout llTypeList;

    private TextView tvSceneAdd;
    private LinearLayout llSceneAdd;

    private EditText etTime;
    private TextView tvTime;
    private SeekBar sbTimeLine;

    private Button btnSub;

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this, this);
        init();
        setListener();
    }

    @Override
    public void showLoading() {
        DialogHelper.toShow();
    }

    @Override
    public void hideLoading() {
        DialogHelper.dismiss();
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public SaveStruct checkTextView() {
        SaveStruct saveStruct = new SaveStruct();
        if (etName != null) {
            if (etName.getText() == null || etName.getText().toString().length() <= 0) {
                showToast("请输入名字");
                return null;
            } else {
                saveStruct.setName(etName.getText().toString());
            }
        }
        if (etS != null) {
            if (etS.getText() == null || etS.getText().toString().length() <= 0) {
                showToast("请输入第几季");
                return null;
            } else {
                saveStruct.setSeason(etS.getText().toString());
            }
        }
        return null;
    }

    @Override
    public void clickTextView(TextView tv, int id, boolean isSelf) {
        if (tv.getText().toString().equals(Constant.getName(id))) {
            if (isSelf) return;
            //未被选中,进行选中变换
            Log.i("clickTextView", "进入选中状态");
            tv.setText(Constant.getName(id) + " √");
            tv.setBackground(getDrawable(R.drawable.activity_main_tv_bg_press));
            tv.setTextColor(ContextCompat.getColor(this, R.color.wihte));
            switch (id) {
                case Constant.OVA:
                    etS.setText("");
                    etS.setEnabled(false);
                    break;
                case Constant.OP:
                    clickTextView(tvED, Constant.ED, true);
                    etEp.setText("");
                    etEp.setEnabled(false);
                    break;
                case Constant.ED:
                    clickTextView(tvOP, Constant.OP, true);
                    etEp.setText("");
                    etEp.setEnabled(false);
                    break;
                default:
                    break;
            }
        } else {
            //被选中，进行未选中变换
            Log.i("clickTextView", "进入未选中状态");
            tv.setText(Constant.getName(id));
            tv.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            tv.setBackground(getDrawable(R.drawable.activity_main_tv_bg_normal));
            switch (id) {
                case Constant.OVA:
                    etS.setEnabled(true);
                    break;
                case Constant.OP:
                    etEp.setEnabled(true);
                case Constant.ED:
                    etEp.setEnabled(true);
                    break;
                default:
                    break;
            }
        }
    }

    private void init() {
        etName = findViewById(R.id.et_name);

        etS = findViewById(R.id.et_s);
        tvOVA = findViewById(R.id.tv_ova);

        etEp = findViewById(R.id.et_ep);
        tvOP = findViewById(R.id.tv_op);
        tvED = findViewById(R.id.tv_ed);

        tvTypeAdd = findViewById(R.id.tv_type_add);
        llTypeList = findViewById(R.id.ll_type_list);

        tvSceneAdd = findViewById(R.id.tv_scene_add);
        llSceneAdd = findViewById(R.id.ll_scene_list);

        etTime = findViewById(R.id.et_time);
        tvTime = findViewById(R.id.tv_time);
        sbTimeLine = findViewById(R.id.sb_time_line);

        btnSub = findViewById(R.id.btn_sub);
    }

    private void setListener() {
        btnSub.setOnClickListener(view -> mPresenter.sub());
        tvOVA.setOnClickListener(view -> clickTextView((TextView) view, Constant.OVA, false));
        tvOP.setOnClickListener(view -> clickTextView((TextView) view, Constant.OP, false));
        tvED.setOnClickListener(view -> clickTextView((TextView) view, Constant.ED, false));
        tvTypeAdd.setOnClickListener(view -> startActivity(new Intent(this, SelectActivity.class)));
        tvSceneAdd.setOnClickListener(view -> startActivity(new Intent(this, SelectActivity.class)));
        sbTimeLine.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvTime.setText(":" + (i < 10
                        ? "0" + i : i));
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
