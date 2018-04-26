package web;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewUtil {
    private WebView webView = null;
    //    是否在web链接中启动新的Activity
    private boolean startNewActivity = false;
    //    是否启动内部监听back按钮
    private boolean enableBack = false;

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebview(WebView webView, String loadUrl) {
        this.webView = webView;
//        控制webView状态
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
//        缩放
        settings.setSupportZoom(true);
        settings.supportMultipleWindows();
        if (!isEnableBack()) {
            webView.setOnKeyListener(new MainWebViewOnKeyListener());
        }
        if (!isStartNewActivity()){
            webView.setWebViewClient(new MainWebViewClient());
        }
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebChromeClient(new MainWebChromeClient());
        webView.loadUrl(loadUrl);
    }

    private class MainWebViewOnKeyListener implements View.OnKeyListener {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
//            按返回键时候的操作
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                    webView.goBack();//后退
                    return true;
                }
            }
            return false;
        }
    }

    private class MainWebChromeClient extends WebChromeClient{
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            result.confirm();
            return true;
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return super.onConsoleMessage(consoleMessage);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }


    //    如果页面中有链接，希望点击链接继续在当前browser中响应，而不是新开一个android的browser
//    需要覆盖webView的webViewClient对象
    public class MainWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private boolean isStartNewActivity() {
        return startNewActivity;
    }

    public void setStartNewActivity(boolean startNewActivity) {
        this.startNewActivity = startNewActivity;
    }

    private boolean isEnableBack() {
        return enableBack;
    }

    public void setEnableBack(boolean enableBack) {
        this.enableBack = enableBack;
    }
}
