package com.simplistic.floating_equalizer;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


/* ID's:
 * topHalf
 * TopBanner
 * optionLayout
 * optionList
 */
public class Options extends Activity implements OnClickListener {

	

	private TextView basic;
	private TextView advanced;
	private TextView whatsBasic;
	private TextView whatsAdvanced;
	private AdView mAdView;

	@Override
	public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.options);

		MobileAds.initialize(this, "ca-app-pub-7041676438054720~4748415693");

		mAdView = findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);
		
        basic = findViewById(R.id.basic);
        advanced = findViewById(R.id.advanced);
        whatsBasic = findViewById(R.id.whatBasic);
        whatsAdvanced = findViewById(R.id.whatAdvanced);
        whatsBasic.setOnClickListener(this);
        whatsAdvanced.setOnClickListener(this);
        basic.setOnClickListener(this);
        advanced.setOnClickListener(this);
        

        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "RobotoThin.ttf");
        basic.setTypeface(typeface1);
        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "RobotoThin.ttf");
        advanced.setTypeface(typeface2);
        Typeface typeface3 = Typeface.createFromAsset(getAssets(), "RobotoThin.ttf");
        whatsBasic.setTypeface(typeface3);
        Typeface typeface4 = Typeface.createFromAsset(getAssets(), "RobotoThin.ttf");
        whatsAdvanced.setTypeface(typeface4);

	}

	 public void basic() {
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle(("Basic Info"));
	        builder.setMessage("Basic: Basic consists of between 5-6 bands a long with stock presets.");
	        builder.setCancelable(true);
	        builder.setIcon(R.mipmap.ic_launcher);
	        builder.setPositiveButton("Okay", (new DialogInterface.OnClickListener(){

	            @Override
				public void onClick(DialogInterface dialogInterface2, int dialogInterface) {
	            }
	        }));
	        builder.create().show();
	    }
	 
	 
	 public void advanced() {
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle(("Advanced Info"));
	        builder.setMessage("Advanced: Advanced consists of 10 bands along with the ability to create, edit and delete custom presets, it also saves your settings to save having to re-adjust the sliders");
	        builder.setCancelable(true);
	        builder.setIcon(R.mipmap.ic_launcher);
	        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener(){

	            @Override
				public void onClick(DialogInterface dialogInterface2, int dialogInterface) {
	            }
	        });
	        builder.create().show();
	    }
	   
	 
@Override
	public void onClick(View v) {
	switch(v.getId()){
	case R.id.basic:
		Intent i0 = new Intent(getApplicationContext(), EqualizerActivity.class);
		startActivity(i0);
		break;
	case R.id.advanced:
		Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(i1);
		break;
	case R.id.whatBasic:
		basic();
		break;
	case R.id.whatAdvanced:
		advanced();
		break;
	}
	
	}
}
