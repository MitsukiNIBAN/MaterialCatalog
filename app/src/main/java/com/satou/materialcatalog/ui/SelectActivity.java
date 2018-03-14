package com.satou.materialcatalog.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.satou.materialcatalog.R;
import com.satou.materialcatalog.presenter.SelectPresenter;
import com.satou.materialcatalog.presenter.contract.SelectContract;
import com.satou.materialcatalog.widget.ConfirmDialog;
import com.satou.materialcatalog.widget.DelDialog;
import com.satou.materialcatalog.widget.InputDialog;
import com.satou.materialcatalog.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitsuki on 2018/3/12.
 */

public class SelectActivity extends AppCompatActivity implements SelectContract.View {

    private SelectContract.Presenter mPresenter;

    private ListView listView;
    private Button addBtn;
    private Button cancelBtn;
    private ArrayAdapter<String> adapter;
    private int page;

    private LoadingDialog loadingDialog;
    private InputDialog inputDialog;
    private DelDialog delDialog;
    private ConfirmDialog confirmDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        Intent in = getIntent();
        page = in.getIntExtra("value", Integer.MIN_VALUE);

        loadingDialog = new LoadingDialog(this);
        inputDialog = new InputDialog(this);
        delDialog = new DelDialog(this);
        confirmDialog = new ConfirmDialog(this);

        mPresenter = new SelectPresenter(this, this);
        listView = findViewById(R.id.lv_data);
        addBtn = findViewById(R.id.btn_add);
        cancelBtn = findViewById(R.id.btn_cancel);

        cancelBtn.setOnClickListener(view -> cancel());
        addBtn.setOnClickListener(view -> showInputDialog(str -> mPresenter.addItem(str)));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, new ArrayList<>());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            showConfirmDialog("确认添加？", () -> {
                //这里设置数据返回MainAcitivty
                Intent intent = new Intent();
                intent.putExtra("key", adapter.getItem(position).toString() + "");
                setResult(RESULT_OK, intent);
                finish();
            });
        });
        listView.setOnItemLongClickListener((adapterView, view, position, id) -> {
            showDelDialog(() -> mPresenter.delItem(adapter.getItem(position).toString()));
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancel();
    }

    @Override
    public void cancel() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public void refreshData(List<String> data) {
        if (data != null && data.size() > 0) {
            adapter.clear();
            adapter.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInputDialog(InputDialog.ConfirmClick click) {
        if (inputDialog != null) {
            inputDialog.setConfirmClick(click);
            inputDialog.show();
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
    public void showDelDialog(DelDialog.DelClick click) {
        if (delDialog != null) {
            delDialog.setDelClick(click);
            delDialog.show();
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

}
