package com.leadsdoit.test.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;
import com.facebook.applinks.AppLinkData;
import com.leadsdoit.test.R;

public class WebviewFragment extends Fragment {
    private static final String TAG = "com.leadsdoit.test.fragment";
    private OnLoadFailed onLoadFailed;
    private WeakReference<Context> context;
    private AppLinkData appLinkData;

    public WebviewFragment() {
    }

    public WebviewFragment(OnLoadFailed onLoadFailed, AppLinkData appLinkData) {
        this.onLoadFailed = onLoadFailed;
        this.appLinkData = appLinkData;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = new WeakReference<>(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        final WebView webView = view.findViewById(R.id.webview);
        CookieManager.setAcceptFileSchemeCookies(true);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptThirdPartyCookies(webView, true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.setVisibility(View.INVISIBLE);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.equals(context.get().getResources().getString(R.string.white_url))) {
                    cookieManager.flush();
                    onLoadFailed.onFailed();
                } else {
                    webView.setVisibility(View.VISIBLE);
                }
            }
        });
        String url = context.get().getResources().getString(R.string.base_url);
        Uri targetUri;
        if (appLinkData != null && (targetUri = appLinkData.getTargetUri()) != null) {
            url += targetUri.getQueryParameter("url");
        }
        webView.loadUrl(url);
        return view;
    }

    public interface OnLoadFailed {
        void onFailed();
    }
}
