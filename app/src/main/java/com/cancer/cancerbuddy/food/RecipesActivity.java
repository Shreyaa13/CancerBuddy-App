package com.cancer.cancerbuddy.food;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.cancer.cancerbuddy.MainActivity;
import com.cancer.cancerbuddy.R;

public class RecipesActivity extends AppCompatActivity {

    TextView link1,link2,link3,link4,link5,link6,link7,link8,link9,link10;

    ImageView back_to_mainN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        back_to_mainN = findViewById(R.id.back_to_mainN);
        link1 = findViewById(R.id.link1);
        link2 = findViewById(R.id.link2);
        link3 = findViewById(R.id.link3);
        link4 = findViewById(R.id.link4);
        link5 = findViewById(R.id.link5);
        link6 = findViewById(R.id.link6);
        link7 = findViewById(R.id.link7);
        link8 = findViewById(R.id.link8);
        link9 = findViewById(R.id.link9);
        link10 = findViewById(R.id.link10);

        back_to_mainN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //adding link for recipe 1
        link1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(RecipesActivity.this);
                alert.setTitle("Apple Muffins");
                WebView wv = new WebView(RecipesActivity.this);
                wv.getSettings().setJavaScriptEnabled(true);
                wv.loadUrl("https://stanfordhealthcare.org/medical-clinics/cancer-nutrition-services/recipes/apple-muffins.html"); //Your Privacy Policy Url Here
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.getSettings().setJavaScriptEnabled(true);
                        view.loadUrl(url);
                        return true;
                    }
                });
                alert.setView(wv);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });

        link2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(RecipesActivity.this);
                alert.setTitle("Spring Vegetable Frittata");

                WebView wv = new WebView(RecipesActivity.this);
                wv.getSettings().setJavaScriptEnabled(true);
                wv.loadUrl("https://stanfordhealthcare.org/medical-clinics/cancer-nutrition-services/recipes/spring-vegetable-frittata.html");
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.getSettings().setJavaScriptEnabled(true);
                        view.loadUrl(url);

                        return true;
                    }
                });

                alert.setView(wv);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();

            }
        });

        link3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(RecipesActivity.this);
                alert.setTitle("Carrot and Apple soup");

                WebView wv = new WebView(RecipesActivity.this);
                wv.getSettings().setJavaScriptEnabled(true);
                wv.loadUrl("https://stanfordhealthcare.org/medical-clinics/cancer-nutrition-services/recipes/carrot-apple-soup.html"); //Your Privacy Policy Url Here
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.getSettings().setJavaScriptEnabled(true);
                        view.loadUrl(url);

                        return true;
                    }
                });

                alert.setView(wv);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();

            }
        });

        link4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(RecipesActivity.this);
                alert.setTitle("Lemon, Broccoli and Garlic Penne Pasta");

                WebView wv = new WebView(RecipesActivity.this);
                wv.getSettings().setJavaScriptEnabled(true);
                wv.loadUrl("https://breastcancer-news.com/social-clips/2016/10/13/7-recipes-every-cancer-patient-should-try/2/"); //Your Privacy Policy Url Here
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.getSettings().setJavaScriptEnabled(true);
                        view.loadUrl(url);

                        return true;
                    }
                });

                alert.setView(wv);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();

            }
        });
        link5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(RecipesActivity.this);
                alert.setTitle("Prawn Risotto");

                WebView wv = new WebView(RecipesActivity.this);
                wv.getSettings().setJavaScriptEnabled(true);
                wv.loadUrl("https://breastcancer-news.com/social-clips/2016/10/13/7-recipes-every-cancer-patient-should-try/5/"); //Your Privacy Policy Url Here
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.getSettings().setJavaScriptEnabled(true);
                        view.loadUrl(url);

                        return true;
                    }
                });

                alert.setView(wv);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();

            }
        });
        link6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(RecipesActivity.this);
                alert.setTitle("Thai Chicken and Coconut Soup");

                WebView wv = new WebView(RecipesActivity.this);
                wv.getSettings().setJavaScriptEnabled(true);
                wv.loadUrl("https://breastcancer-news.com/social-clips/2016/10/13/7-recipes-every-cancer-patient-should-try/6/"); //Your Privacy Policy Url Here
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.getSettings().setJavaScriptEnabled(true);
                        view.loadUrl(url);

                        return true;
                    }
                });

                alert.setView(wv);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();

            }
        });
        link7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(RecipesActivity.this);
                alert.setTitle("Halibut with Citrus and Garlic");

                WebView wv = new WebView(RecipesActivity.this);
                wv.getSettings().setJavaScriptEnabled(true);
                wv.loadUrl("https://stanfordhealthcare.org/medical-clinics/cancer-nutrition-services/recipes/halibut-citrus-garlic.html"); //Your Privacy Policy Url Here
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.getSettings().setJavaScriptEnabled(true);
                        view.loadUrl(url);

                        return true;
                    }
                });

                alert.setView(wv);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();

            }
        });
        link8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(RecipesActivity.this);
                alert.setTitle("Tofu Fried Rice");

                WebView wv = new WebView(RecipesActivity.this);
                wv.getSettings().setJavaScriptEnabled(true);
                wv.loadUrl("https://stanfordhealthcare.org/medical-clinics/cancer-nutrition-services/recipes/tofu-fried-rice.html"); //Your Privacy Policy Url Here
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.getSettings().setJavaScriptEnabled(true);
                        view.loadUrl(url);

                        return true;
                    }
                });

                alert.setView(wv);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();

            }
        });
        link9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(RecipesActivity.this);
                alert.setTitle("Apple Pumpkin Shake");

                WebView wv = new WebView(RecipesActivity.this);
                wv.getSettings().setJavaScriptEnabled(true);
                wv.loadUrl("https://stanfordhealthcare.org/medical-clinics/cancer-nutrition-services/recipes/apple-pumpkin-shake.html"); //Your Privacy Policy Url Here
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.getSettings().setJavaScriptEnabled(true);
                        view.loadUrl(url);

                        return true;
                    }
                });

                alert.setView(wv);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();

            }
        });

        link10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(RecipesActivity.this);
                alert.setTitle("Herby Chicken Breasts");

                WebView wv = new WebView(RecipesActivity.this);
                wv.getSettings().setJavaScriptEnabled(true);
                wv.loadUrl("https://breastcancer-news.com/social-clips/2016/10/13/7-recipes-every-cancer-patient-should-try/7/ "); //Your Privacy Policy Url Here
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.getSettings().setJavaScriptEnabled(true);
                        view.loadUrl(url);

                        return true;
                    }
                });

                alert.setView(wv);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();


            }
        });
    }
}