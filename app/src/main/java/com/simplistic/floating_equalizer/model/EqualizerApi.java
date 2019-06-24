/*
 * Decompiled with CFR 0_58.
 * 
 * Could not load the following classes:
 *  android.media.audiofx.BassBoost
 *  android.media.audiofx.Equalizer
 *  android.media.audiofx.Virtualizer
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 */
package com.simplistic.floating_equalizer.model;

import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Virtualizer;

public class EqualizerApi {
	public static String PREF_AUTOSTART;
	private static Integer PRIORITY;
	private static Integer SESSION;
	private static Integer[] mBandsValue;
	private static BassBoost mBb;
	public static Equalizer mEq;
	private static Integer mSession;
	private static Virtualizer mVirt;

	static {
		EqualizerApi.PRIORITY = 0;
		EqualizerApi.SESSION = 0;
		EqualizerApi.PREF_AUTOSTART = "autostart";
	}

	public static void destroy() {
		EqualizerApi.mEq.release();
		EqualizerApi.mVirt.release();
		EqualizerApi.mBb.release();
	}

	public static int getBandFreq(int n) {
		short s = (short) (Math.ceil(((n / 2))));
		int n2 = EqualizerApi.mEq.getCenterFreq(s);
		int n3 = (30 * n2 / 100);
		if (((n + 1) % 2) != 0)
			return (n2 - n3);
		return (n2 + n3);
	}

	public static int getBandLevel(int n) {
		if ((EqualizerApi.mBandsValue == null)
				|| (EqualizerApi.mBandsValue[n] == null))
			return 0;
		return EqualizerApi.mBandsValue[n];
	}

	public static boolean getBassBoostEnabled() {
		return EqualizerApi.mBb.getEnabled();
	}

	public static int getBassBoostStrength() {
		return EqualizerApi.mBb.getRoundedStrength();
	}

	public static boolean getEqualizerEnabled() {
		return EqualizerApi.mEq.getEnabled();
	}

	public static int getMaxBandLevelRange() {
		return EqualizerApi.mEq.getBandLevelRange()[1];
	}

	public static int getMinBandLevelRange() {
		return EqualizerApi.mEq.getBandLevelRange()[0];
	}

	public static int getNumberOfBands() {
		return (2 * EqualizerApi.mEq.getNumberOfBands());
	}

	public static boolean getVirtualizerEnabled() {
		return EqualizerApi.mVirt.getEnabled();
	}

	public static int getVirtualizerStrength() {
		return EqualizerApi.mVirt.getRoundedStrength();
	}

	public static void init(Integer integer) {
		if (integer == null) {
			integer = EqualizerApi.SESSION;
		}
		EqualizerApi.mSession = integer;
		EqualizerApi.mEq = new Equalizer(EqualizerApi.PRIORITY.intValue(),
				EqualizerApi.mSession.intValue());
		EqualizerApi.mVirt = new Virtualizer(EqualizerApi.PRIORITY.intValue(),
				EqualizerApi.mSession.intValue());
		EqualizerApi.mBb = new BassBoost(EqualizerApi.PRIORITY.intValue(),
				EqualizerApi.mSession.intValue());
		EqualizerApi.mBandsValue = new Integer[EqualizerApi.getNumberOfBands()];
	}

	/*
	 * Enabled aggressive block sorting Enabled unnecessary exception pruning
	 */
	public static void setBandLevel(int n, int n2) {
		double d;
		double d2;
		EqualizerApi.mBandsValue[n] = n2;
		if (((n + 1) % 2) == 0) {
			d = EqualizerApi.getBandLevel((n - 1));
			d2 = EqualizerApi.getBandLevel(n);
		} else {
			d = EqualizerApi.getBandLevel(n);
			d2 = EqualizerApi.getBandLevel((n + 1));
		}
		double d3 = (((EqualizerApi.getNumberOfBands()) / 2.0 - (n)) / 12.0);
		double d4 = (1.0 - d3);
		double d5 = (1.0 + d3);
		double d6 = (d > 0.0) ? ((d * d5)) : ((d / d4));
		double d7 = (d2 > 0.0) ? ((d2 / d5)) : ((d2 * d4));
		EqualizerApi.mEq.setBandLevel((short) (Math.ceil(((n / 2)))),
				(short) (Math.ceil((((d6 + d7) / 2.0)))));
	}

	public static void setBassBoostEnabled(boolean bl) {
		EqualizerApi.mBb.setEnabled(bl);
	}

	public static void setBassBoostStrength(int n) {
		EqualizerApi.mBb.setStrength((short) (n));
	}

	public static void setEqualizerEnabled(boolean bl) {
		EqualizerApi.mEq.setEnabled(bl);
	}

	public static void setVirtualizerEnabled(boolean bl) {
		EqualizerApi.mVirt.setEnabled(bl);
	}

	public static void setVirtualizerStrength(int n) {
		EqualizerApi.mVirt.setStrength((short) (n));
	}
}
