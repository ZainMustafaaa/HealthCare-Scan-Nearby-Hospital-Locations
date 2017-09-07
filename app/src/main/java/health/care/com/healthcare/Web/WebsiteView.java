/*
 * %W% %E% Zain-Ul-Abedin
 *
 * Copyright (c) 2017-2018. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of ZainMustafaaa.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * for learning purposes.
 *
 */

package health.care.com.healthcare.Web;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import health.care.com.healthcare.Medical.MedicinesController;
import health.care.com.healthcare.R;

public class WebsiteView extends AppCompatActivity {

    /** variable string to show message of loading*/
    String message = "Loading...";
    /** webView variable initialize with null*/
    private WebView webview = null;

    /**
     * onCreate override method call very first when activity start
     * @param savedInstanceState
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_view);

        /** initializing webView variable with webView*/
        webview = (WebView) findViewById(R.id.webView);
        /** calling webView Method*/
        setUpWebView();
    }

    /** setUpWebView method to setUp webView settings*/
    void setUpWebView(){

        /** getting webView setting into webSettings variable */
        WebSettings webSettings = webview.getSettings();
        /** enabling javascript enabled to show javascript content flexibly */
        webSettings.setJavaScriptEnabled(true);
        /** initializing webView client */
        webview.setWebViewClient(new MyWebViewClient());
        /** webView allow to get current location to sites who need this property */
        webSettings.setGeolocationEnabled(true);
        /** enabling open window to java script when content want to open it */
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        /** condition for enabling media playback require when mobile version is greater then jellybean*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setMediaPlaybackRequiresUserGesture(true);
        }

        /** enabling webview to load images automatically without getting permission from user */
        webSettings.setLoadsImagesAutomatically(true);

        /** improve settings for fast browsing  */
        webSettings.setUseWideViewPort(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        try {
            /** loading webpage through URL */
            webview.loadUrl(getResources().getString(MedicinesController.getWebUrl()));
        }catch (Exception e){
            /** for if webpage is not load */
            webview.setWebChromeClient(new WebChromeClient(){

                @Override
                public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                    callback.invoke(origin, true, false);
                }
            });
            message = "Scanning Location...";
            /** load default page to show user that your page is not loading */
            webview.loadUrl("file:///android_asset/myfile.html");
        }

    }

    /**
     * onBackPressed method to perform specific task
     * when user pressed back button
     * */
    @Override
    public void onBackPressed() {
        /** condition for if web can go back then go back */
        if(webview.canGoBack())
            webview.goBack();
        else /** else call to super method to go back from web view */
            super.onBackPressed();
    }

    /**
     * class @extends WebViewClient
     * to perform some actions of loading
     * */
    class MyWebViewClient extends WebViewClient{

        /** progressDialog  variable default initialized with null */
        ProgressDialog progressDialog;

        /**
         * onPageStarted method call when web page start loading
         * @param view
         * @param url
         * @param favicon
         * */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            /** initializing progressDialog */
            progressDialog = new ProgressDialog(WebsiteView.this);
            progressDialog.setTitle("Please wait");
            progressDialog.setMessage(message);
            progressDialog.show();
            /** calling super method to perform generic task to load web page */
            super.onPageStarted(view, url, favicon);
        }

        /**
         * onPageFinished method calls when page finished loading
         * @param view
         * @param url
         * */
        @Override
        public void onPageFinished(WebView view, String url) {
            /** progressDialog dismissed when page loaded */
            progressDialog.dismiss();
            /** call super method to finishUp page loading */
            super.onPageFinished(view, url);
        }
    }
}
