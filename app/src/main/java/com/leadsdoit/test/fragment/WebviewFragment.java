package com.leadsdoit.test.fragment;

import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;

import com.leadsdoit.test.R;

public class WebviewFragment extends Fragment {
    private OnLoadFailed onLoadFailed;
    private WeakReference<Context> context;
    public WebviewFragment() {
    }

    public static WebviewFragment newInstance(OnLoadFailed onLoadFailed) {
        WebviewFragment fragment = new WebviewFragment();
        fragment.onLoadFailed = onLoadFailed;
        return fragment;
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
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVisibility(View.INVISIBLE);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.equals(context.get().getResources().getString(R.string.white_url))) {
                    onLoadFailed.onFailed();
                } else {
                    webView.setVisibility(View.VISIBLE);
                }
            }
        });
        webView.loadUrl(context.get().getResources().getString(R.string.base_url));
        return view;
    }

    public interface OnLoadFailed {
        void onFailed();
    }
}
