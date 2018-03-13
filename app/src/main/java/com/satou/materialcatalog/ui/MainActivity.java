package com.satou.materialcatalog.ui;

import android.content.Intent;
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
import com.satou.materialcatalog.widget.ConfirmDialog;
import com.satou.materialcatalog.widget.LoadingDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;
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

    private List<String> typeList;
    private List<String> sceneList;

    private MainPresenter mPresenter;
    private LoadingDialog loadingDialog;
    private ConfirmDialog confirmDialog;

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
                        typeList.add(result);
                        //以下还要重载view
                        refreshTypeList(typeList);
                        break;
                    case Constant.REQUEST_CODE_ADD_SCENE:
                        sceneList.add(result);
                        //以下还要重载view
                        refreshSceneList(sceneList);
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
    public SaveStruct checkTextView() {
        SaveStruct saveStruct = new SaveStruct();
        if (etName != null) {
            if (etName.getText() == null || etName.getText().toString().length() <= 0) {
                showToast("请输入名字");
                return null;
            } else {
                saveStruct.setName(etName.getText().toString());
            }
        } else return null;
        if (etS != null) {
            if (etS.getText() == null || etS.getText().toString().length() <= 0) {
                if (isCheck(tvOVA, Constant.OVA)) {
                    saveStruct.setSeason(Constant.getName(Constant.OVA));
                } else {
                    showToast("请输入第几季");
                    return null;
                }
            } else {
                saveStruct.setSeason(etS.getText().toString());
            }
        } else return null;
        if (etEp != null) {
            if (etEp.getText() == null || etEp.getText().toString().length() <= 0) {
                if (isCheck(tvOP, Constant.OP)) {
                    saveStruct.setEpisode(Constant.getName(Constant.OP));
                } else if (isCheck(tvED, Constant.ED)) {
                    saveStruct.setEpisode(Constant.getName(Constant.ED));
                } else {
                    showToast("请输入第几集");
                    return null;
                }
            } else {
                saveStruct.setSeason(etEp.getText().toString());
            }
        } else return null;

        if (typeList.size() <= 0) {
            showToast("未添加类型");
            return null;
        } else {
            saveStruct.setType(typeList);
        }

        if (sceneList.size() <= 0) {
            showToast("未添加场景");
            return null;
        } else {
            saveStruct.setScene(sceneList);
        }

        if (etTime != null) {
            if (etTime.getText() == null || etTime.getText().toString().length() <= 0) {
                showToast("请输入分钟");
                return null;
            } else {
                try {
                    saveStruct.setTime(etTime.getText().toString() + "分" + tvTime.getText().toString() + "秒");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

        } else return null;

        return saveStruct;
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
            confirmDialog.setPositiveOnClick(click);
            confirmDialog.show();
            confirmDialog.setText(str);
        }
    }

    @Override
    public void refreshTypeList(List<String> data) {

    }

    @Override
    public void refreshSceneList(List<String> data) {

    }

    @Override
    public void clearPage() {
        //插入成功后清空页面数据
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

        typeList = new ArrayList<>();
        sceneList = new ArrayList<>();
        loadingDialog = new LoadingDialog(this);
        confirmDialog = new ConfirmDialog(this);
    }

    private void setListener() {
        btnSub.setOnClickListener(view -> {
            SaveStruct saveStruct = checkTextView();
            if (saveStruct != null)
                showConfirmDialog("确认提交数据？",
                        () -> mPresenter.sub(saveStruct));
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
