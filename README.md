# RetrofitEngine
结合Rxjava和Rclifecycle对Retrofit的一次封装，喜欢的朋友可以star一下
> 主要特点

```
* 支持无网络时数据缓存，无需服务器的支持(仅限GET请求)
* 支持过滤恶意频繁网络请求，减轻服务器压力
* 支持cookie头数据的自动加载及持久化
* 网络请求数据的处理过程和Activity、Fragment的生命周期一致
* 支持版本更新（新增）
```
> OkhttpClient构建

```
cookiePersistor = new SharedPrefsCookiePersistor(context);
cookieJar = new PersistentCookieJar(new SetCookieCache(), cookiePersistor);
OkHttpClient.Builder builder = new OkHttpClient.Builder();
builder.connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //支持自动持久化cookie和自动添加cookie
                .cookieJar(cookieJar)
                //错误重连
                .retryOnConnectionFailure(true)
                //没有网络，加载缓存(仅限GET)
                .addInterceptor(new ForceCacheInterceptor(context))
                //添加请求头
                .addInterceptor(new HeadIntercepter())
                //过滤频繁请求，5s为缓存时间，单位秒,5秒之内反复请求，取缓存，超出5秒，取服务器数据
                .addNetworkInterceptor(new FilterFastRequestInterceptor(5))
                //缓存
                .cache(new Cache(new File(context.getCacheDir(), DEFAULT_CACHE_FILE), DEFAULT_CACHE_SIZE));
        //打印请求日志
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        okHttpClient = builder.build();
```
### 用法
下载代码放入项目中
>Gradle配置:看demo，注意添加[Lambda表达式](http://www.jianshu.com/p/5fc2b3362702).

```
android {
	...
    compileOptions {
        sourceCompatibility 1.8
        targetCompatibility 1.8
    }
}
```
>HTTP接口(和retrofit写请求接口一致)

```
public interface GitHubService {
  @GET("yourUrl")
  Observable<UserBean> getUserInfo();
}
```
>请求网络model

```
public class HomeModel {
    private GitHubService gitHubService;

    public HomeModel(Context context) {
        gitHubService = RetrofitEngine.getInstance(context).create(GitHubService.class);
    }

    public Observable<UserBean> getUserInfo() {
        return gitHubService. getUserInfo().compose(RxUtil.io2main());
    }
}
```
>发起网络请求

```
homeModel. getUserInfo().compose(RxUtil.lifecycle(this)).subscribe(new CallBack<UserBean>() {
            @Override
            public void onSuccess(UserBean userBean) {

            }

            @Override
            public void onFail(int code, String message) {

            }
        });
```
>请求后的结果也可以在Logcat看得一清二楚,大大提高了开发效率

![image](https://github.com/sunrise10/RetrofitEngine/blob/master/app/src/main/gif/log.png)

>版本更新，不多说先看图

![image](https://github.com/sunrise10/RetrofitEngine/blob/2aef93beefa61805331a1f40729314595eb54225/app/src/main/gif/update.gif)

在activity里合适的地方
```
//拿到写SD卡权限后调用，即可看到上图效果
updateUtil.showUpdateNotifation();
updateUtil.download();
```
