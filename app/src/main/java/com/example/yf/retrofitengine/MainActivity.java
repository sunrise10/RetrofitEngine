package com.example.yf.retrofitengine;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yf.retrofitengine.base.BaseActivity;
import com.example.yf.retrofitengine.model.bean.homeBean.response.ProvinceBean;
import com.example.yf.retrofitengine.model.net.home.HomeModel;
import com.example.yf.retrofitengine.net.CallBack;
import com.example.yf.retrofitengine.net.util.RxUtil;
import com.example.yf.retrofitengine.net.util.UpdateUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private HomeModel homeModel;
    private TextView tv;
    private Button bt;
    private Button bt1;
    private UpdateUtil updateUtil;
    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeModel = new HomeModel(this);
        updateUtil = new UpdateUtil(this);
        rxPermissions = new RxPermissions(this);
        tv = findViewById(R.id.tv);
        bt = findViewById(R.id.bt);
        bt1 = findViewById(R.id.bt1);
        bt.setOnClickListener(this);
        bt1.setOnClickListener(this);
        updateUtil.showUpdateNotifation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                getProvinces();
                break;
            case R.id.bt1:
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                // I can control the camera now
                                updateUtil.download();
                            } else {
                                // Oups permission denied
                            }
                        });
                break;
        }
    }

    private void getProvinces() {
        homeModel.getProvinces().compose(RxUtil.lifecycle(this)).subscribe(new CallBack<ProvinceBean>() {
            @Override
            public void onSuccess(ProvinceBean provinceBean) {
                tv.setText(provinceBean.data.toString());
            }

            @Override
            public void onFail(int code, String message) {

            }
        });
    }
}
