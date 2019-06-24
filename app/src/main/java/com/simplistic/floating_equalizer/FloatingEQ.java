package com.simplistic.floating_equalizer;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.simplistic.floating_equalizer.service.Floating;

public class FloatingEQ extends AppCompatActivity implements OnSeekBarChangeListener,
OnCheckedChangeListener,
OnClickListener, OnItemClickListener
{

static final int MAX_SLIDERS = 10; // Must match the XML layout


SeekBar bass_boost = null;

TextView bass_boost_label = null;
BassBoost bb = null;

CheckBox enabled = null;
Equalizer eq;
Button flat = null;
    short[] r;

int max_level = getMaxBandLevelRange();
int min_level = getMinBandLevelRange();
int num_sliders = 0;

    TextView[] slider_labels = new TextView[MAX_SLIDERS];


    SeekBar[] sliders = new SeekBar[MAX_SLIDERS];
private TextView preset;
private SharedPreferences sp;

private String[] music_styles;

private ListView list;
    private NotificationUtils mNotificationUtils;

public void Exit() {
    AlertDialog.Builder builder = new AlertDialog.Builder((this));
    builder.setTitle("Sure?");
    builder.setMessage("Are you sure you'd like to exit?");
    builder.setCancelable(true);
    builder.setIcon(R.drawable.ic_launcher);
    builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
    @Override
		public void onClick(DialogInterface dialogInterface2, int dialogInterface) {
        }
    });
    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

        @Override
		public void onClick(DialogInterface dialogInterface2, int dialogInterface) {
        	releaseEqualizer();

            mNotificationUtils.getManager().cancelAll();
            finish();
        }
    });
    builder.create().show();
}

/*=============================================================================
formatBandLabel 
=============================================================================*/
public String formatBandLabel (int[] band) 
{
return milliHzToString(band[0]) + "-" + milliHzToString(band[1]);
}



/*=============================================================================
milliHzToString 
=============================================================================*/


public String milliHzToString(int milliHz) 
{
String string = "Hz";
if ((milliHz = (milliHz / 1000)) < 1000) return (milliHz + string);
milliHz/=1000;
string = "Khz";
return (milliHz + string);
}


/*=============================================================================
onCheckedChange
=============================================================================*/
@Override
public void onCheckedChanged (CompoundButton view, boolean isChecked)
{
if (view == enabled)
  {
  eq.setEnabled(isChecked);
  
  }
}

/*=============================================================================
onClick
=============================================================================*/
@Override
public void onClick (View view)
{
switch(view.getId()){
case R.id.flat:
	setFlat();
	break;
case R.id.currentPreset:
	preset.setVisibility(View.GONE);
	for (int i = 0; i < num_sliders && i < MAX_SLIDERS; i++)
    {
    sliders[i].setVisibility(View.VISIBLE);
    slider_labels[i].setVisibility(View.VISIBLE);
    }
	break;
}
}

/*=============================================================================
onCreate 
=============================================================================*/
@Override
public void onCreate(Bundle savedInstanceState)
  {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.eq);

      mNotificationUtils = new NotificationUtils(this);
  
  enabled = findViewById(R.id.enabled);
  enabled.setOnCheckedChangeListener(this);

  flat = findViewById(R.id.flat);
  flat.setOnClickListener(this);

  preset = findViewById(R.id.currentPreset);
  preset.setOnClickListener(this);
  
  Typeface typeface = Typeface.createFromAsset(getAssets(), "RobotoThin.ttf");
  preset.setTypeface(typeface);
  
  bass_boost = findViewById(R.id.bass_boost);
  bass_boost.setOnSeekBarChangeListener(this);
  bass_boost_label = findViewById (R.id.bass_boost_label);

  sliders[0] = findViewById(R.id.slider_1);
  slider_labels[0] = findViewById(R.id.slider_label_1);
  sliders[1] = findViewById(R.id.slider_2);
  slider_labels[1] = findViewById(R.id.slider_label_2);
  sliders[2] = findViewById(R.id.slider_3);
  slider_labels[2] = findViewById(R.id.slider_label_3);
  sliders[3] = findViewById(R.id.slider_4);
  slider_labels[3] = findViewById(R.id.slider_label_4);
  sliders[4] = findViewById(R.id.slider_5);
  slider_labels[4] = findViewById(R.id.slider_label_5);
  sliders[5] = findViewById(R.id.slider_6);
  slider_labels[5] = findViewById(R.id.slider_label_6);
  sliders[6] = findViewById(R.id.slider_7);
  slider_labels[6] = findViewById(R.id.slider_label_7);
  sliders[7] = findViewById(R.id.slider_8);
  slider_labels[7] = findViewById(R.id.slider_label_8);
  sliders[8] = findViewById(R.id.slider_9);
  slider_labels[8] = findViewById(R.id.slider_label_9);
  sliders[9] = findViewById(R.id.slider_10);
  slider_labels[9] = findViewById(R.id.slider_label_10);

  list = findViewById(R.id.spinner);
  list.setOnItemClickListener(this);
  eq = new Equalizer (100, 0);
  setUpEQ(0);

  bb = new BassBoost (0, 0);
  if (bb != null)
    {
    }
  else
    {
    bass_boost.setVisibility(View.GONE);
    bass_boost_label.setVisibility(View.GONE);
    }

updateUI();
getEqualizer();
getBass();

short m = eq.getNumberOfPresets();
music_styles = new String[m];
for(int k=0; k <m ; k++) 
music_styles[k] = eq.getPresetName((short) k);

ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.preset_item, R.id.preset, music_styles);
list.setAdapter(adapter);

//Intent intent = new Intent(getApplicationContext(), Floating.class);
//stopService(intent);
//      NotificationCompat.Builder nb = mNotificationUtils.
//              getAndroidChannelNotification("Floating Equalizer", "");
//
//      mNotificationUtils.getManager().cancelAll();

} 

public void setUpEQ(int setUp){
	 if (eq != null)
	    {
	    eq.setEnabled(true);
	    int num_bands = eq.getNumberOfBands();
	    num_sliders = num_bands;
            short[] r = eq.getBandLevelRange();
	    min_level = r[0];
	    max_level = r[1];
	    for (int i = 0; i < num_sliders && i < MAX_SLIDERS; i++)
	      {
	      int[] freq_range = eq.getBandFreqRange((short)i);
	      sliders[i].setOnSeekBarChangeListener(this);
	      slider_labels[i].setText (formatBandLabel (freq_range));
	      }
	    }else{
	    	eq.getEnabled();
	    	eq.getBandLevel((short) getEqualizer());
	    	getBass();
	    }
	  for (int i = num_sliders ; i < MAX_SLIDERS; i++)
	    {
	    sliders[i].setVisibility(View.GONE);
	    slider_labels[i].setVisibility(View.GONE);
	    }
}

public int getMaxBandLevelRange() {
    if (eq != null) return eq.getBandLevelRange()[1];
    return 1500;
}

public int getMinBandLevelRange() {
    if (eq != null) return eq.getBandLevelRange()[0];
    return -1500;
}


/*=============================================================================
onCreate 
=============================================================================*/
@Override
public boolean onCreateOptionsMenu(Menu menu) 
{
MenuInflater inflater = getMenuInflater();
inflater.inflate(R.menu.floating_menu, menu);
return true;
} 



/*=============================================================================
onOptionsItemSelected 
=============================================================================*/
@Override
public boolean onOptionsItemSelected(MenuItem item) 
 {
 switch (item.getItemId()) 
   {
 case R.id.rate: 
		try {
		    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.simplistic.floating_equalizer")));
		} catch (android.content.ActivityNotFoundException anfe) {
		    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.simplistic.floating_equalizer")));
		}
		return true;
 case R.id.ads:
 		try {
			    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.simplistic.floatingequalizerpro")));
			} catch (android.content.ActivityNotFoundException anfe) {
			    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.simplistic.floatingequalizerpro")));
			}
 		return true;
 	case R.id.stop:
     Exit();
     return true;
 	case R.id.action_settings:
 		Intent i = new Intent(getApplicationContext(), Home.class);
		startActivity(i);
 		return true;
   }
 return super.onOptionsItemSelected(item);
 }

/*=============================================================================
onProgressChanged
=============================================================================*/
@Override
public void onProgressChanged (SeekBar seekBar, int level, 
boolean fromTouch) 
{
  if (seekBar == bass_boost)
    {
    bb.setEnabled (level > 0);
    bb.setStrength ((short)level); // Already in the right range 0-1000
    }
  else if (eq != null)
    {
    int new_level = min_level + (max_level - min_level) * level / 100; 
    for (int i = 0; i < num_sliders; i++)
      {
      if (sliders[i] == seekBar)
        {
        eq.setBandLevel ((short)i, (short)new_level);
        break;
        }
      }
    }
}

/*=============================================================================
onStartTrackingTouch
=============================================================================*/
@Override
public void onStartTrackingTouch(SeekBar seekBar) 
{
}

/*=============================================================================
onStopTrackingTouch
=============================================================================*/
@Override
public void onStopTrackingTouch(SeekBar seekBar) 
{
}

public void releaseEqualizer() {
    if (eq != null) {
       eq.release();

  }
    if (bb == null)
    bb.release();
    
}

public int saveBass(){
	sp = getSharedPreferences("Bass", MODE_PRIVATE);
	SharedPreferences.Editor editor = sp.edit();
	editor.putInt("bass_boost", bb.getRoundedStrength());
	editor.apply();
	return 0;

}

public void getBass(){
	sp = getSharedPreferences("Bass", MODE_PRIVATE);
    bb.setStrength((short) sp.getInt("bass_boost", 0));
    bass_boost.setProgress(sp.getInt("bass_boost", 0));
}

public int saveEqualizer(){
	int n = 0;
	sp = getSharedPreferences("Equalizer", MODE_PRIVATE);
	SharedPreferences.Editor editor = sp.edit();
	for (int i = 0; i < num_sliders && i < MAX_SLIDERS; i++)
    {
	editor.putInt("Band_" + n, eq.getBandLevel((short) n));
	++n;
    }
	editor.commit();
	return n;
} 


@SuppressLint("UseValueOf")
public int getEqualizer(){
	int n = 0;
	sp = getSharedPreferences("Equalizer", MODE_PRIVATE);
	for (int i = 0; i < num_sliders && i < MAX_SLIDERS; i++)
    {
    eq.setBandLevel((short)n, (short) sp.getInt(("Band_" + n), i));
   
    }
	return n;
}




/*=============================================================================
setFlat 
=============================================================================*/
public void setFlat ()
{
if (eq != null)
  {
  for (int i = 0; i < num_sliders; i++)
    {
    eq.setBandLevel((short)i, (short)0);
   
    }
  }

if (bb != null)
  {
  bb.setEnabled(false); 
  bb.setStrength((short)0); 
  }

updateUI();
}

/*=============================================================================
showAbout 
=============================================================================*/


public void Sure() {
    AlertDialog.Builder builder = new AlertDialog.Builder((this));
    builder.setTitle(("Please Choose:"));
    builder.setMessage(("Keep running: This will keep both the EQ and the notifiaction running\n\nClose EQ: This will close both the EQ and the floating button"));
    builder.setCancelable(true);
    builder.setIcon(R.drawable.ic_launcher);
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

        @Override
		public void onClick(DialogInterface dialogInterface2, int dialogInterface) {
        }
    });
    builder.setNeutralButton("Keep running", new DialogInterface.OnClickListener(){

        @Override
		public void onClick(DialogInterface dialogInterface2, int dialogInterface) {
        	 Intent intent = new Intent(getApplicationContext(), Floating.class);
             startService(intent);
             saveEqualizer();
             saveBass();
             finish();
            
        }
    });
    builder.setPositiveButton("Close EQ", new DialogInterface.OnClickListener(){

        @Override
		public void onClick(DialogInterface dialogInterface2, int dialogInterface) {
            Intent intent = new Intent(getApplicationContext(), Floating.class);
            stopService(intent);
            saveEqualizer();
            saveBass();
            releaseEqualizer();
            finish();
          
        }
    });
    builder.create().show();
}

/*=============================================================================
updateBassBoost
=============================================================================*/
public void updateBassBoost ()
{
if (bb != null)
	bass_boost.setProgress (bb.getRoundedStrength());
else
bass_boost.setProgress(0);
}

/*=============================================================================
updateSliders 
=============================================================================*/
public void updateSliders ()
{
  for (int i = 0; i < num_sliders; i++)
    {
    int level;
    if (eq != null)
      level = eq.getBandLevel((short)i);
    else
      level = 0;
    int pos = 100 * level / (max_level - min_level) + 50;
    sliders[i].setProgress(pos);
    }
} 
/*=============================================================================
updateUI
=============================================================================*/
public void updateUI ()
{
updateSliders();
updateBassBoost();
enabled.setChecked(eq.getEnabled());
}

@Override
public void onBackPressed(){
	Sure();
}

@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	eq.usePreset((short)position);
	preset.setText("Current Preset: " + eq.getPresetName((short)position));
	preset.setVisibility(View.VISIBLE);
	for (int i = 0; i < num_sliders && i < MAX_SLIDERS; i++)
    {
	sliders[i].setVisibility(View.GONE);
	slider_labels[i].setVisibility(View.GONE);
    }
	
}
}