package com.zakiadev.testakumikro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by sulistyarif on 08/03/18.
 */

public class PetunjukActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.materi_webview_activity);

        webView = (WebView)findViewById(R.id.wvMateri);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/petunjuk_pemakaian.html");

    }
}