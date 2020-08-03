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

    public WebviewFragment() {
    }

    public static WebviewFragment newInstance() {
        WebviewFragment fragment = new WebviewFragment();
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
        WebView webView = view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //TODO it return actual url of page wich was downloaded
                Toast.makeText(WebviewFragment.this.getContext(), url, Toast.LENGTH_LONG).show();
            }
        });
        webView.loadUrl("https://uk.wikipedia.org/wiki");
        return view;
    }
}
