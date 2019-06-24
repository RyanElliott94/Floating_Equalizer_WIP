package com.simplistic.floating_equalizer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.simplistic.floating_equalizer.service.Floating;

public class Home extends Activity {
	private TextView floatinfo;
    private Button floating;
    Button statici;
    private TextView staticinfo;
    EqualizerActivity eq;
    private NotificationUtils mNotificationUtils;
   
    /*
    public boolean version() throws NameNotFoundException{
    	 SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), 0);
         boolean bl = sharedPreferences.getBoolean("hasUpgraded", false);
    	 PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
         if (!bl && pInfo.versionName.equals("2.6")) {
        	 
        	 firstUse();
        	 SharedPreferences.Editor editor = sharedPreferences.edit();
             editor.putBoolean("hasUpgraded", true);
             editor.commit();
        }
         if (!(bl)) return true;
         return false;
    }
    */
   
    /*
    public void firstUse() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("PLEASE READ:");
        builder.setMessage("Unfortunately due to problems, Floating Equalizer has been downgraded, which means you can no longer create, edit or delete presets and the 10 bands have been reduced to 5 bands."
        		+ "\n"
        		+ "\n"
        		+ "If however you prefer the previous version with the 10 bands and custom presets and you wish to use it, just click 'Email Me' and i will send you the app and installations instructions with in 24hours");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setPositiveButton("No Thanks", (new DialogInterface.OnClickListener(){

            @Override
			public void onClick(DialogInterface dialogInterface2, int dialogInterface) {
            }
        }));
        builder.setNeutralButton("Email Me", (new DialogInterface.OnClickListener(){

            @Override
			public void onClick(DialogInterface dialogInterface2, int dialogInterface) {
            	  Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
            	            "mailto","simplisticapps@gmail.com", null));
            	emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Downgraded Floating Equalizer");
            	startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        }));
        builder.create().show();
    }
*/
    public void floating() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(("Floating Information"));
        builder.setMessage("The floating option will place a floating button, which will hover over your phones screen whilst using the phone as normal, it also acts as a shortcut so if you tap and hold the button for a second, it will open the equalizer");
        builder.setCancelable(true);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener(){

            @Override
			public void onClick(DialogInterface dialogInterface2, int dialogInterface) {
            }
        });
        builder.create().show();
    }

    @Override
	public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.home);

        mNotificationUtils = new NotificationUtils(this);
        
        floating = findViewById(R.id.float1);
        statici = findViewById(R.id.static1);
        floatinfo = findViewById(R.id.whatfloat);
        staticinfo = findViewById(R.id.whatstatic);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "RobotoThin.ttf");
        floating.setTypeface(typeface);
        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "RobotoThin.ttf");
        statici.setTypeface(typeface2);
        Typeface typeface3 = Typeface.createFromAsset(getAssets(), "RobotoThin.ttf");
        floatinfo.setTypeface(typeface3);
        Typeface typeface4 = Typeface.createFromAsset(getAssets(), "RobotoThin.ttf");
        staticinfo.setTypeface(typeface4);
        floating.setOnClickListener((new View.OnClickListener(){

            @Override
			public void onClick(View view) {
            	Intent intent2 = new Intent(getApplicationContext(), EqualizerActivity.class);
                startActivity(intent2);
                finish();
            }
        }));
        
        statici.setOnClickListener(new View.OnClickListener(){

            @Override
			public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EqualizerActivity.class);
                startActivity(intent);
                Intent i = new Intent(getApplicationContext(), Floating.class);
        		stopService(i);
                finish();
            }
        });
        
        floatinfo.setOnClickListener((new View.OnClickListener(){

            @Override
			public void onClick(View view) {
                floating();
            }
        }));
        
        staticinfo.setOnClickListener((new View.OnClickListener(){

            @Override
			public void onClick(View view) {
                statici();
            }
        }));
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void statici() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(("Static Information"));
        builder.setMessage("Choosing static will open the equalizer straight away and will place a notification which will run in the forground so even once you exit the equalizer you quickly go back by clicking the notification");
        builder.setCancelable(true);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setPositiveButton("Okay", (new DialogInterface.OnClickListener(){

            @Override
			public void onClick(DialogInterface dialogInterface2, int dialogInterface) {
            }
        }));
        builder.create().show();
    }

  

}
