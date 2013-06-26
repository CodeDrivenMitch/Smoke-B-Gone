package com.goodhearted.smokebegone;

import android.content.Context;

public class Period {
	public static final long second = 1000;
	public static final long minute = second * 60;
	public static final long hour = minute * 60;
	public static final long day = hour * 24;
	public static final long week = day * 7;
	public static final long year = 365 * day;

	public static final long[] benefits = { 20 * minute, 8 * hour, 12 * hour,
			24 * hour, 48 * hour, 72 * hour, 14 * day, 21 * day, 30 * day };

	private int seconds;
	private int minutes;
	private int hours;
	private int days;
	private int miliseconds;

	private long period;

	public Period(long ms) {
		setPeriod(ms);
		calculate();
	}

	public Period(long ms, long ms2) {
		long p = ms2 - ms;
		if (p < 0)
			p *= -1;
		setPeriod(p);
	}

	public int getSeconds() {
		return this.seconds;
	}

	public int getMinutes() {
		return this.minutes;
	}

	public int getHours() {
		return this.hours;
	}

	public int getDays() {
		return this.days;
	}

	public int getMiliseconds() {
		return this.miliseconds;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
		calculate();
	}

	private void calculate() {
		long rest = period;
		// calculate days
		this.days = (int) ((period - period % day) / day);
		rest = rest - (this.days * day);

		// calculate hours
		this.hours = (int) ((rest - rest % hour) / hour);
		rest = rest - (this.hours * hour);

		// calculate minutes

		this.minutes = (int) ((rest - rest % minute) / minute);
		rest = rest - (this.minutes * minute);

		// calculate seconds
		this.seconds = (int) ((rest - rest % second) / second);
		rest = rest - (this.seconds * second);

		// save miliseconds
		this.miliseconds = (int) rest;
	}

	public String getString() {
		if (this.days == 0 && this.hours == 0) {
			return this.minutes + " minuten en " + this.seconds + " seconden";
		} else if (this.days == 0) {
			return this.hours + " uur en " + this.minutes + " minuten";
		} else {
			if (this.days > 1) {
				return this.days + " dagen en " + this.hours + " uur";
			} else {
				return this.days + " dag en " + this.hours + " uur";
			}
		}
	}

	public float getSave(Context c, int smoked) {
		int cpp = (PreferenceProvider.readInteger(c, PreferenceProvider.keyCPP,
				-1));

		int cpd = (PreferenceProvider.readInteger(c, PreferenceProvider.keyCPD,
				-1));
		float ppp = (PreferenceProvider.readFloat(c, PreferenceProvider.keyPPP,
				-1.0f));

		float p = day / cpd;
		float pricepercig = ppp / cpp;
		float numberofcigssaved = this.period / p - smoked;

		return (pricepercig * numberofcigssaved);
	}

	public int getBenefitProgress(int index) {
		long totalBenefitPeriod = benefits[index];

		double percentOfPeriod = 0.01 * totalBenefitPeriod;

		return (int) (this.period / percentOfPeriod);
	}

}
