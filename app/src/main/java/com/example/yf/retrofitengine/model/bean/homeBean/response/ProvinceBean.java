package com.example.yf.retrofitengine.model.bean.homeBean.response;

import java.util.List;

/**
 * Created by yf on 2018/6/25.
 * 描述：
 */
public class ProvinceBean {

    public int code;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        public int depth;
        public String depthStr;
        public String firstLetter;
        public int id;
        public String jianpin;
        public String name;
        public int parentId;
        public String pinyin;

        @Override
        public String toString() {
            return "{"+
                    "'depth':" + depth +
                    ", "+"\n"+"'depthStr':'" + depthStr + '\'' +
                    ", "+"\n"+"'firstLetter':'" + firstLetter + '\'' +
                    ", "+"\n"+"'id':" + id +
                    ", "+"\n"+"'jianpin':'" + jianpin + '\'' +
                    ", "+"\n"+"'name':'" + name + '\'' +
                    ", "+"\n"+"'parentId':" + parentId +
                    ", "+"\n"+"'pinyin':" + pinyin + '\'' +
                    '}'+"\n";
        }
    }
}
