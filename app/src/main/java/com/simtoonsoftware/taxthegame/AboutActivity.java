package com.simtoonsoftware.taxthegame;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class AboutActivity extends AppCompatActivity {

    /*@Override
    protected void onPause() {
        super.onPause();
        System.out.println("Exiting AboutActivity ..."); //printing to console
        android.os.Process.killProcess(android.os.Process.myPid());
        finish();
    }*/

    private AdView RandomBannerAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        final Button btn_PrivacyPolicy = findViewById(R.id.btn_privacyPolicy);

        MobileAds.initialize(this, "ca-app-pub-9086446979210331~8508547502");

        RandomBannerAd = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        RandomBannerAd.loadAd(adRequest);


        btn_PrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.iubenda.com/privacy-policy/12620960/full-legal")));
            }

                                             });

    }
}
