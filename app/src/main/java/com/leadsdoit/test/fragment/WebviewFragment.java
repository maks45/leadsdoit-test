package com.leadsdoit.test.fragment;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leadsdoit.test.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WebviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebviewFragment extends Fragment {
    private static final String BASE_URL = "https://www.google.com/";
    private OnLoadFailed onLoadFailed;

    public WebviewFragment() {
    }

    public static WebviewFragment newInstance(OnLoadFailed onLoadFailed) {
        WebviewFragment fragment = new WebviewFragment();
        fragment.onLoadFailed = onLoadFailed;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
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
                if (!url.equals(BASE_URL)) {
                    //Toast.makeText(WebviewFragment.this.getContext(), url, Toast.LENGTH_LONG).show();
                    onLoadFailed.onFailed();
                } else {
                    webView.setVisibility(View.VISIBLE);
                }
                //TODO it return actual url of page wich was downloaded
               // Toast.makeText(WebviewFragment.this.getContext(), url, Toast.LENGTH_LONG).show();
            }
        });
        webView.loadUrl(BASE_URL);
        return view;
    }

    public interface OnLoadFailed {
        void onFailed();
    }
}
