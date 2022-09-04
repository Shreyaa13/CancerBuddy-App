package com.cancer.cancerbuddy.Record_Sys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cancer.cancerbuddy.R;

public class TrackWebActivity extends AppCompatActivity {

    WebView webViewPension1;
    Dialog dialog;

    String web1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_web);

        Intent myIntent = getIntent();
        web1 = myIntent.getStringExtra("web");
        webViewPension1 = findViewById(R.id.webViewPension1);



        dialog = new Dialog(TrackWebActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_wait1);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();

            }
        }, 7000);




        webViewPension1.setWebViewClient(new TrackWebActivity.myWebClient());
        webViewPension1.getSettings().setJavaScriptEnabled(true);
        webViewPension1.getSettings().setLoadWithOverviewMode(true);
        webViewPension1.getSettings().setUseWideViewPort(true);
        webViewPension1.getSettings().setBuiltInZoomControls(true);
        //  webView.loadUrl("https://www.vetboss
        //        webViewPension1.loadUrl("https://sspy-up.gov.in/reportnew/OAPReportDistrictVel.in");ise_1920.aspx?s=OldAgePension_2122");

        webViewPension1.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
                try {
                    webView.stopLoading();
                    dialog.dismiss();
                } catch (Exception e) {
                    dialog.dismiss();
                }
                dialog.dismiss();

                if (webView.canGoBack()) {
                    webView.goBack();
                    dialog.dismiss();
                }
                dialog.dismiss();

                webView.loadUrl("about:blank");
                AlertDialog alertDialog = new AlertDialog.Builder(TrackWebActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Check your internet connection and try again.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(getIntent());
                        dialog.dismiss();
                    }
                });

                alertDialog.show();
                super.onReceivedError(webView, errorCode, description, failingUrl);
            }
        });
    }

    public class myWebClient extends WebViewClient
    {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            dialog.dismiss();

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;



        }

        public void onPageFinished(WebView view, String url) {
            //  if (dialog.isShowing()) {
            dialog.dismiss();
            //  }
        }




    }

    @Override
    // This method is used to detect back button
    public void onBackPressed() {

        dialog.dismiss();


        Intent intent = new Intent(TrackWebActivity.this, Track_SystemActivity.class);
        startActivity(intent);
        finish();
    }
}
