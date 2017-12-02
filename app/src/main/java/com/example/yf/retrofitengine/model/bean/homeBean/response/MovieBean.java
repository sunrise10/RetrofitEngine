package com.example.yf.retrofitengine.model.bean.homeBean.response;

import java.util.List;

/**
 * Created by yf on 2017/11/29.
 */

public class MovieBean {

    public int count;
    public int start;
    public int total;
    public String title;
    public List<SubjectsBean> subjects;

    public static class SubjectsBean {

        public RatingBean rating;
        public String title;
        public int collect_count;
        public String original_title;
        public String subtype;
        public String year;
        public ImagesBean images;
        public String alt;
        public String id;
        public List<String> genres;
        public List<CastsBean> casts;
        public List<DirectorsBean> directors;

        public static class RatingBean {
            /**
             * max : 10
             * average : 7.2
             * stars : 35
             * min : 0
             */

            public int max;
            public double average;
            public String stars;
            public int min;
        }

        public static class ImagesBean {
            /**
             * small : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2455156816.jpg
             * large : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2455156816.jpg
             * medium : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2455156816.jpg
             */

            public String small;
            public String large;
            public String medium;
        }

        public static class CastsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1274242/
             * avatars : {"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1656.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1656.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1656.jpg"}
             * name : 黄渤
             * id : 1274242
             */

            public String alt;
            public AvatarsBean avatars;
            public String name;
            public String id;

            public static class AvatarsBean {
                /**
                 * small : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1656.jpg
                 * large : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1656.jpg
                 * medium : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1656.jpg
                 */

                public String small;
                public String large;
                public String medium;
            }
        }

        public static class DirectorsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1189801/
             * avatars : {"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1369639357.39.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1369639357.39.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1369639357.39.jpg"}
             * name : 陈正道
             * id : 1189801
             */

            public String alt;
            public AvatarsBeanX avatars;
            public String name;
            public String id;

            public static class AvatarsBeanX {
                /**
                 * small : https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1369639357.39.jpg
                 * large : https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1369639357.39.jpg
                 * medium : https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1369639357.39.jpg
                 */

                public String small;
                public String large;
                public String medium;
            }
        }
    }
}
