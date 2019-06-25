/*
 * Decompiled with CFR 0_58.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.CheckBox
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.SeekBar
 *  android.widget.SeekBar$OnSeekBarChangeListener
 *  android.widget.Spinner
 *  java.lang.Object
 */
package com.simplistic.floating_equalizer.ui;


import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.audiofx.PresetReverb;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.simplistic.floating_equalizer.R;
import com.simplistic.floating_equalizer.model.EqualizerApi;
import com.simplistic.floating_equalizer.model.Reverberation;
import com.simplistic.floating_equalizer.ui.lib.EqualizerSettings;

public class EffectsFragment extends EqualizerSettings implements AdapterView.OnItemSelectedListener, PresetReverb.OnParameterChangeListener {
	protected CheckBox mBassBoostCheckbox;
	protected SeekBar mBassBoostSeekBar;
	protected Spinner mReverberationSpinner;
	protected CheckBox mReverbCheckbox;
	protected CheckBox mVirtualizerCheckbox;
	protected SeekBar mVirtualizerSeekBar;
	protected void fetchValues() {
		boolean bl = EqualizerApi.getBassBoostEnabled();
		mBassBoostCheckbox.setChecked(bl);
		mBassBoostSeekBar.setProgress(EqualizerApi.getBassBoostStrength());
		mBassBoostSeekBar.setEnabled(bl);
		boolean bl2 = EqualizerApi.getVirtualizerEnabled();
		mVirtualizerCheckbox.setChecked(bl2);
		mVirtualizerSeekBar.setProgress(EqualizerApi
				.getVirtualizerStrength());
		mVirtualizerSeekBar.setEnabled(bl2);
		boolean bl3 = EqualizerApi.getReverbEnabled();
		mReverbCheckbox.setChecked(bl3);
	}

	protected void initBassBoostUiEvents() {
		mBassBoostCheckbox
		.setOnCheckedChangeListener((new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton compoundButton,
					boolean bl) {
				EqualizerApi.setBassBoostEnabled(bl);
				mBassBoostSeekBar.setEnabled(bl);
			}
		}));
		mBassBoostSeekBar
		.setOnSeekBarChangeListener((new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar2, int n,
					boolean seekBar) {
				EqualizerApi.setBassBoostStrength(n);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		}));
	}

	protected void initReverberationSpinner() {
		mReverberationSpinner = getView().findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
				R.array.reverb_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
		mReverberationSpinner.setAdapter(adapter);
		mReverberationSpinner.setOnItemSelectedListener(this);

		mReverbCheckbox
				.setOnCheckedChangeListener((new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton compoundButton,
												 boolean bl) {
						EqualizerApi.setReverbEnabled(bl);
					}
				}));
	}

	protected void initUi() {
		EqualizerApi.getEqualizerEnabled();
		mBassBoostCheckbox = getView()
				.findViewById(R.id.bass_boost_enabled);
		mBassBoostSeekBar = getView()
				.findViewById(R.id.bass_boost_val);
		mVirtualizerCheckbox = getView()
				.findViewById(R.id.virtualization_enabled);
		mReverbCheckbox = getView()
				.findViewById(R.id.reverb_enabled);
		mVirtualizerSeekBar = getView()
				.findViewById(R.id.virtualization_val);
		initBassBoostUiEvents();
		initVirtualizerUiEvents();
		initReverberationSpinner();
	}

	protected void initVirtualizerUiEvents() {
		mVirtualizerCheckbox
		.setOnCheckedChangeListener((new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton compoundButton,
					boolean bl) {
				EqualizerApi.setVirtualizerEnabled(bl);
				mVirtualizerSeekBar.setEnabled(bl);
			}
		}));
		mVirtualizerSeekBar
		.setOnSeekBarChangeListener((new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar2, int n,
					boolean seekBar) {
				EqualizerApi.setVirtualizerStrength(n);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		}));
	}

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
	}

	@Override
	public View onCreateView(LayoutInflater layoutInflater,
			ViewGroup viewGroup, Bundle bundle) {
		return layoutInflater.inflate(R.layout.effect, viewGroup, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		fetchValues();
	}

	@Override
	public void onViewCreated(View view, Bundle bundle) {
		initUi();
		super.onViewCreated(view, bundle);
	}

	@Override
	public void refetch() {
		fetchValues();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view,
							   int pos, long id) {
		if(pos == 0){
			EqualizerApi.setReverbPreset(PresetReverb.PRESET_NONE);
		}else if(pos == 1){
			EqualizerApi.setReverbPreset(PresetReverb.PRESET_LARGEHALL);
		}else if(pos == 2){
			EqualizerApi.setReverbPreset(PresetReverb.PRESET_LARGEROOM);
		}else if(pos == 3){
			EqualizerApi.setReverbPreset(PresetReverb.PRESET_MEDIUMHALL);
		}else if(pos == 4){
			EqualizerApi.setReverbPreset(PresetReverb.PRESET_MEDIUMROOM);
		}else if(pos == 5){
			EqualizerApi.setReverbPreset(PresetReverb.PRESET_PLATE);
		}else if(pos == 6){
			EqualizerApi.setReverbPreset(PresetReverb.PRESET_SMALLROOM);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}

	@Override
	public void onParameterChange(PresetReverb presetReverb, int i, int i1, short i2) {
		presetReverb.getPreset();
	}
}
