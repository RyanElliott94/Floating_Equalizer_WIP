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


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.simplistic.floating_equalizer.R;
import com.simplistic.floating_equalizer.model.EqualizerApi;
import com.simplistic.floating_equalizer.ui.lib.EqualizerSettings;

public class EffectsFragment extends EqualizerSettings {
	protected CheckBox mBassBoostCheckbox;
	protected SeekBar mBassBoostSeekBar;
	protected Spinner mReverberationSpinner;
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
	}

	protected void initUi() {
		mBassBoostCheckbox = getView()
				.findViewById(R.id.bass_boost_enabled);
		mBassBoostSeekBar = getView()
				.findViewById(R.id.bass_boost_val);
		mVirtualizerCheckbox = getView()
				.findViewById(R.id.virtualization_enabled);
		mVirtualizerSeekBar = getView()
				.findViewById(R.id.virtualization_val);
		initBassBoostUiEvents();
		initVirtualizerUiEvents();
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

}
