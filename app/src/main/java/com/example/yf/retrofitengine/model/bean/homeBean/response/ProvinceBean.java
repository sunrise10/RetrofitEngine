package com.example.yf.retrofitengine.model.bean.homeBean.response;

import java.util.List;

/**
 * Created by yf on 2018/6/25.
 * 描述：
 */
public class ProvinceBean {

    /**
     * code : 0
     * data : [{"depth":1,"depthStr":"省份","firstLetter":"b","id":110000,"jianpin":"bjs","name":"北京市","parentId":0,"pinyin":"beijingshi"},{"depth":1,"depthStr":"省份","firstLetter":"t","id":120000,"jianpin":"tjs","name":"天津市","parentId":0,"pinyin":"tianjinshi"},{"depth":1,"depthStr":"省份","firstLetter":"h","id":130000,"jianpin":"hbs","name":"河北省","parentId":0,"pinyin":"hebeisheng"},{"depth":1,"depthStr":"省份","firstLetter":"s","id":140000,"jianpin":"sxs","name":"山西省","parentId":0,"pinyin":"shanxisheng"},{"depth":1,"depthStr":"省份","firstLetter":"n","id":150000,"jianpin":"nmgzzq","name":"内蒙古自治区","parentId":0,"pinyin":"neimengguzizhiqu"},{"depth":1,"depthStr":"省份","firstLetter":"l","id":210000,"jianpin":"lns","name":"辽宁省","parentId":0,"pinyin":"liaoningsheng"},{"depth":1,"depthStr":"省份","firstLetter":"j","id":220000,"jianpin":"jls","name":"吉林省","parentId":0,"pinyin":"jilinsheng"},{"depth":1,"depthStr":"省份","firstLetter":"h","id":230000,"jianpin":"hljs","name":"黑龙江省","parentId":0,"pinyin":"heilongjiangsheng"},{"depth":1,"depthStr":"省份","firstLetter":"s","id":310000,"jianpin":"shs","name":"上海市","parentId":0,"pinyin":"shanghaishi"},{"depth":1,"depthStr":"省份","firstLetter":"j","id":320000,"jianpin":"jss","name":"江苏省","parentId":0,"pinyin":"jiangsusheng"},{"depth":1,"depthStr":"省份","firstLetter":"z","id":330000,"jianpin":"zjs","name":"浙江省","parentId":0,"pinyin":"zhejiangsheng"},{"depth":1,"depthStr":"省份","firstLetter":"a","id":340000,"jianpin":"ahs","name":"安徽省","parentId":0,"pinyin":"anhuisheng"},{"depth":1,"depthStr":"省份","firstLetter":"f","id":350000,"jianpin":"fjs","name":"福建省","parentId":0,"pinyin":"fujiansheng"},{"depth":1,"depthStr":"省份","firstLetter":"j","id":360000,"jianpin":"jxs","name":"江西省","parentId":0,"pinyin":"jiangxisheng"},{"depth":1,"depthStr":"省份","firstLetter":"s","id":370000,"jianpin":"sds","name":"山东省","parentId":0,"pinyin":"shandongsheng"},{"depth":1,"depthStr":"省份","firstLetter":"h","id":410000,"jianpin":"hns","name":"河南省","parentId":0,"pinyin":"henansheng"},{"depth":1,"depthStr":"省份","firstLetter":"h","id":420000,"jianpin":"hbs","name":"湖北省","parentId":0,"pinyin":"hubeisheng"},{"depth":1,"depthStr":"省份","firstLetter":"h","id":430000,"jianpin":"hns","name":"湖南省","parentId":0,"pinyin":"hunansheng"},{"depth":1,"depthStr":"省份","firstLetter":"g","id":440000,"jianpin":"gds","name":"广东省","parentId":0,"pinyin":"guangdongsheng"},{"depth":1,"depthStr":"省份","firstLetter":"g","id":450000,"jianpin":"gxzzzzq","name":"广西壮族自治区","parentId":0,"pinyin":"guangxizhuangzuzizhiqu"},{"depth":1,"depthStr":"省份","firstLetter":"h","id":460000,"jianpin":"hns","name":"海南省","parentId":0,"pinyin":"hainansheng"},{"depth":1,"depthStr":"省份","firstLetter":"c","id":500000,"jianpin":"cqs","name":"重庆市","parentId":0,"pinyin":"chongqingshi"},{"depth":1,"depthStr":"省份","firstLetter":"s","id":510000,"jianpin":"scs","name":"四川省","parentId":0,"pinyin":"sichuansheng"},{"depth":1,"depthStr":"省份","firstLetter":"g","id":520000,"jianpin":"gzs","name":"贵州省","parentId":0,"pinyin":"guizhousheng"},{"depth":1,"depthStr":"省份","firstLetter":"y","id":530000,"jianpin":"yns","name":"云南省","parentId":0,"pinyin":"yunnansheng"},{"depth":1,"depthStr":"省份","firstLetter":"x","id":540000,"jianpin":"xzzzq","name":"西藏自治区","parentId":0,"pinyin":"xizangzizhiqu"},{"depth":1,"depthStr":"省份","firstLetter":"s","id":610000,"jianpin":"sxs","name":"陕西省","parentId":0,"pinyin":"shanxisheng"},{"depth":1,"depthStr":"省份","firstLetter":"g","id":620000,"jianpin":"gss","name":"甘肃省","parentId":0,"pinyin":"gansusheng"},{"depth":1,"depthStr":"省份","firstLetter":"q","id":630000,"jianpin":"qhs","name":"青海省","parentId":0,"pinyin":"qinghaisheng"},{"depth":1,"depthStr":"省份","firstLetter":"n","id":640000,"jianpin":"nxhzzzq","name":"宁夏回族自治区","parentId":0,"pinyin":"ningxiahuizuzizhiqu"},{"depth":1,"depthStr":"省份","firstLetter":"x","id":650000,"jianpin":"xjwwezzq","name":"新疆维吾尔自治区","parentId":0,"pinyin":"xinjiangweiwuerzizhiqu"},{"depth":1,"depthStr":"省份","firstLetter":"t","id":710000,"jianpin":"tws","name":"台湾省","parentId":0,"pinyin":"taiwansheng"},{"depth":1,"depthStr":"省份","firstLetter":"x","id":810000,"jianpin":"xgtbxzq","name":"香港特别行政区","parentId":0,"pinyin":"xianggangtebiexingzhengqu"},{"depth":1,"depthStr":"省份","firstLetter":"a","id":820000,"jianpin":"amtbxzq","name":"澳门特别行政区","parentId":0,"pinyin":"aomentebiexingzhengqu"}]
     * msg : success
     */

    public int code;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * depth : 1
         * depthStr : 省份
         * firstLetter : b
         * id : 110000
         * jianpin : bjs
         * name : 北京市
         * parentId : 0
         * pinyin : beijingshi
         */

        public int depth;
        public String depthStr;
        public String firstLetter;
        public int id;
        public String jianpin;
        public String name;
        public int parentId;
        public String pinyin;
    }
}
