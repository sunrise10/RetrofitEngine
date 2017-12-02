package com.example.yf.retrofitengine;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yf.retrofitengine.model.bean.homeBean.response.MovieBean;
import com.example.yf.retrofitengine.model.net.home.HomeModel;
import com.example.yf.retrofitengine.net.CallBack;
import com.example.yf.retrofitengine.net.util.RxUtil;
import com.example.yf.retrofitengine.base.BaseActivity;

import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private HomeModel homeModel;
    private TextView tv;
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeModel = new HomeModel(MainActivity.this);
        tv = (TextView) findViewById(R.id.tv);
        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        homeModel.getMovies(toUtf8String("黄渤")).compose(RxUtil.lifecycle(this)).subscribe(new CallBack<MovieBean>() {
            @Override
            public void onSuccess(MovieBean movieBean) {
                if (movieBean != null) {
                    String movieName = "";
                    List<MovieBean.SubjectsBean> subjects = movieBean.subjects;
                    for (MovieBean.SubjectsBean subject : subjects) {
                        movieName += subject.title + "\n";
                    }
                    tv.setText(movieName);
                }

            }

            @Override
            public void onFail(int code, String message) {

            }
        });
    }

    /**
     * 转换为%E4%BD%A0形式
     */
    public String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = String.valueOf(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }
}
