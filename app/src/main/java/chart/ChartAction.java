package chart;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.webkit.JavascriptInterface;

import com.google.gson.Gson;

//js方法一定要加@JavascriptInterface 否则API17无法运行
public class ChartAction {
    private Activity activity;
    private ChartEntity entity;

    public ChartAction(Activity activity, ChartEntity entity) {
        this.activity = activity;
        this.entity = entity;
    }

    //   java通过loadUrl()调用js方法 把数据传给js
    @JavascriptInterface
    public String initDatas() {
        return new Gson().toJson(entity);
    }

    //   js将数据传给java通过WebView的addJavascriptInterface()方法映射一个对象
//       然后再在js中通过javascript：对象.方法(参数)的方式调用
    @JavascriptInterface
    public void updateDatas(String jsMsg) {
        ChartEntity entity = new Gson().fromJson(jsMsg, ChartEntity.class);

    }
}
