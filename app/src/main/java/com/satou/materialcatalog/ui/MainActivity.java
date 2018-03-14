package com.satou.materialcatalog.ui;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.satou.materialcatalog.Constant;
import com.satou.materialcatalog.R;
import com.satou.materialcatalog.presenter.MainPresenter;
import com.satou.materialcatalog.presenter.contract.MainContract;
import com.satou.materialcatalog.widget.ConfirmDialog;
import com.satou.materialcatalog.widget.DelDialog;
import com.satou.materialcatalog.widget.LoadingDialog;

import java.util.List;

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


    private MainContract.Presenter mPresenter;
    private LoadingDialog loadingDialog;
    private ConfirmDialog confirmDialog;
    private DelDialog delDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this, this);
        init();
        setListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            String result = data.getExtras().getString("key");
            if (result != null)
                switch (requestCode) {
                    case Constant.REQUEST_CODE_ADD_TYPE:
                        mPresenter.addType(result);
//                        typeList.add(result);
//                        //以下还要重载view
//                        refreshTypeList(typeList);
                        break;
                    case Constant.REQUEST_CODE_ADD_SCENE:
                        mPresenter.addScene(result);
//                        sceneList.add(result);
//                        //以下还要重载view
//                        refreshSceneList(sceneList);
                        break;
                    default:
                        break;
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoading() {
        if (loadingDialog != null) loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (loadingDialog != null) loadingDialog.dismiss();
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean checkTextView() {
        if (etName != null) {
            if (etName.getText() == null || etName.getText().toString().length() <= 0) {
                showToast("请输入名字");
                return false;
            } else {
                mPresenter.setName(etName.getText().toString());
            }
        } else return false;
        if (etS != null) {
            if (etS.getText() == null || etS.getText().toString().length() <= 0) {
                if (isCheck(tvOVA, Constant.OVA)) {
                    mPresenter.setSeason(Constant.getName(Constant.OVA));
                } else {
                    showToast("请输入第几季");
                    return false;
                }
            } else {
                mPresenter.setSeason(etS.getText().toString());
            }
        } else return false;
        if (etEp != null) {
            if (etEp.getText() == null || etEp.getText().toString().length() <= 0) {
                if (isCheck(tvOP, Constant.OP)) {
                    mPresenter.setEpisode(Constant.getName(Constant.OP));
                } else if (isCheck(tvED, Constant.ED)) {
                    mPresenter.setEpisode(Constant.getName(Constant.ED));
                } else {
                    showToast("请输入第几集");
                    return false;
                }
            } else {
                mPresenter.setSeason(etEp.getText().toString());
            }
        } else return false;

        if (mPresenter.haveType()) {
            mPresenter.setType();
        } else {
            showToast("未添加类型");
            return false;
        }

        if (mPresenter.haveScene()) {
            mPresenter.setScene();
        } else {
            showToast("未添加场景");
            return false;
        }

        if (etTime != null) {
            if (etTime.getText() == null || etTime.getText().toString().length() <= 0) {
                showToast("请输入分钟");
                return false;
            } else {
                try {
                    mPresenter.setTime(etTime.getText().toString() + "分" + tvTime.getText().toString() + "秒");
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

        } else return false;

        return true;
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

    @Override
    public void showConfirmDialog(String str, ConfirmDialog.PositiveOnClick click) {
        if (confirmDialog != null) {
            confirmDialog.show();
            confirmDialog.setPositiveOnClick(click);
            confirmDialog.setText(str);
        }
    }

    @Override
    public void refreshTypeList(List<String> data) {
        llTypeList.removeAllViews();
        for (String str : data) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_data, null);
            TextView tvData = view.findViewById(R.id.tv_data);
            tvData.setText(str + "");
            llTypeList.addView(view);

            view.setLongClickable(true);
            view.setOnLongClickListener(v -> {
                showDel(() -> {
                    mPresenter.removeType(tvData.getText().toString());
                    return;
                });
                return true;
            });
        }
    }

    @Override
    public void refreshSceneList(List<String> data) {
        llSceneAdd.removeAllViews();
        for (String str : data) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_data, null);
            TextView tvData = view.findViewById(R.id.tv_data);
            tvData.setText(str + "");
            llSceneAdd.addView(view);

            view.setLongClickable(true);
            view.setOnLongClickListener(v -> {
                showDel(() -> {
                    mPresenter.removeScene(tvData.getText().toString());
                    return;
                });
                return true;
            });
        }
    }

    @Override
    public void clearPage() {
        //插入成功后清空页面数据
        try {
            etName.setText("");
            etS.setText("");
            etEp.setText("");
            etTime.setText("");
            tvTime.setText("00");
            clickTextView(tvOVA, Constant.OVA, true);
            clickTextView(tvOP, Constant.OP, true);
            clickTextView(tvED, Constant.ED, true);
            sbTimeLine.setProgress(0);
            mPresenter.clearData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showDel(DelDialog.DelClick click) {
        if (delDialog != null) {
            delDialog.show();
            delDialog.setDelClick(click);
        }
    }

    private Boolean isCheck(TextView tv, int id) {
        if (tv == null) return false;
        return !tv.getText().toString().equals(Constant.getName(id));
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

        loadingDialog = new LoadingDialog(this);
        confirmDialog = new ConfirmDialog(this);
        delDialog = new DelDialog(this);
    }

    private void setListener() {
        btnSub.setOnClickListener(view -> {
            if (checkTextView())
                showConfirmDialog("确认提交数据？",
                        () -> mPresenter.sub());
        });
        tvOVA.setOnClickListener(view -> clickTextView((TextView) view, Constant.OVA, false));
        tvOP.setOnClickListener(view -> clickTextView((TextView) view, Constant.OP, false));
        tvED.setOnClickListener(view -> clickTextView((TextView) view, Constant.ED, false));
        tvTypeAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this, SelectActivity.class);
            intent.putExtra("value", Constant.REQUEST_CODE_ADD_TYPE);
            startActivityForResult(intent, Constant.REQUEST_CODE_ADD_TYPE);
        });
        tvSceneAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this, SelectActivity.class);
            intent.putExtra("value", Constant.REQUEST_CODE_ADD_SCENE);
            startActivityForResult(intent, Constant.REQUEST_CODE_ADD_SCENE);
        });
        sbTimeLine.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvTime.setText((i < 10 ? "0" + i : i) + "");
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
