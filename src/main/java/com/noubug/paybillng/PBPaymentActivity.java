package com.noubug.paybillng;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PBPaymentActivity extends AppCompatActivity {

    public static final String PAYMENT_CONFIG = "PAYMENT_CONFIG";
    public static final int MAKE_PAYMENT_REQUEST_CODE = 909;
    public static final String PAYMENT_TRANSACTION_REFERENCE = "PAYMENT_TRANSACTION_REFERENCE";
    private WebView webView;
    private ProgressBar progressBar;
    private boolean isLoaded = false;
    private PBPaymentConfig config;
    public static final Long MINIMUM_AMOUNT = 20000L;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pbpayment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().hide();
        config = (PBPaymentConfig) getIntent().getSerializableExtra(PAYMENT_CONFIG);
        setUpViews();
    }

    private boolean validateConfig(){
        boolean valid = true;
        if(config == null){
            valid = false;
            throw new RuntimeException("PBPaymentConfig is null");
        }
        if(config.getOrganizationPublicKey() == null || config.getOrganizationPublicKey().isEmpty()){
            valid = false;
            throw new RuntimeException("organizationPublicKey is null or Empty");
        }
        if(config.getOrganizationUniqueReference() == null || config.getOrganizationUniqueReference().isEmpty()){
            valid = false;
            throw new RuntimeException("organizationUniqueReference is null or Empty");
        }
        if(config.getOrganizationCode() == null || config.getOrganizationCode().isEmpty()){
            valid = false;
            throw new RuntimeException("organizationCode is null or Empty");
        }
        if(config.getAmountInKobo() == null || config.getAmountInKobo() < MINIMUM_AMOUNT){
            valid = false;
            throw new RuntimeException("Amount is null or less than allowed minimum");
        }
        if(config.getCustomerEmail() == null || config.getCustomerEmail().isEmpty()){
            valid = false;
            throw new RuntimeException("customerEmail is null or empty");
        }
        return valid;
    }

    private void setUpViews() {
        webView = (WebView) findViewById(R.id.content_payment);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new PaymentChromeClient());
        webView.addJavascriptInterface(this, "PayBillClient");
        if(validateConfig()){
            webView.loadUrl("file:///android_asset/html/payment-window.html");
        }
    }

    @JavascriptInterface
    public void onClose(String ref) {
        Intent data = new Intent();
        data.putExtra(PAYMENT_TRANSACTION_REFERENCE, config.getOrganizationUniqueReference());
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra(PAYMENT_TRANSACTION_REFERENCE, config.getOrganizationUniqueReference());
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private class PaymentChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

            if (newProgress == 100 && !isLoaded) {
                isLoaded = true;
                progressBar.setVisibility(View.GONE);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    webView.evaluateJavascript(String.format("makePayment('%s')", gson.toJson(config)), null);
                } else {
                    webView.loadUrl(String.format("javascript:makePayment('%s')", gson.toJson(config)));
                }
            }
        }
    }
}
