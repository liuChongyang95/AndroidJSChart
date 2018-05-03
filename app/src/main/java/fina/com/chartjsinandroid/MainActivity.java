package fina.com.chartjsinandroid;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.Gson;

import chart.ChartAction;
import chart.ChartEntity;
import web.WebViewUtil;

public class MainActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container,new PlaceHolderFragment()).commit();
        }
    }

    public static class PlaceHolderFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView =inflater.inflate(R.layout.fragment_main,container,false);
            WebView doughNut=rootView.findViewById(R.id.doughnut);
            initDatas(doughNut);
            //"file:///android_asset/web/Chart.js-master/samples/doughnut.html"
            new WebViewUtil().initWebview(doughNut,"file:////android_asset/www/chartjsmaster/samples/charts/doughnut.html");
            return rootView;
        }

        @JavascriptInterface
        private void initDatas(WebView webView){
            ChartEntity entity=new ChartEntity();
            ChartAction action=new ChartAction(getActivity(),entity);
//            向JS发送配置信息
            action.initDatas();
//            返回值
            webView.addJavascriptInterface(action,"action");
        }
    }
}
